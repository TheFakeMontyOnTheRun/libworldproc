/**
 * 
 */
package br.odb.worldprocessing;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.odb.gameapp.ApplicationClient;
import br.odb.libscene.GroupSector;
import br.odb.libscene.SceneNode;
import br.odb.libscene.Sector;
import br.odb.libscene.SpaceRegion;
import br.odb.libscene.World;
import br.odb.utils.Direction;
import br.odb.utils.math.Vec3;

/**
 * @author monty
 * 
 */
public class WorldGlobalPartitioner extends WorldPartitioner {

	private ApplicationClient client;
	private World world;
	static long counter = 0;
	
	@Override
	public void run() {
		
		client.printVerbose("cleaning leaf nodes for replacement");
		
		counter = 0;
		Set<Hyperplane> planes = new HashSet<Hyperplane>();

		
		Sector sector;
		
		for (SceneNode sr : world.getAllRegionsAsList()) {
			if (sr instanceof GroupSector) {
				sector = new Sector((SpaceRegion)sr );
//				sector.parent = sr;
				((GroupSector) sr).getSons().add( sector ); ///This is sooooo wrong...

				planes.addAll(getAllHyperplanesForSector((GroupSector)sr));
			}
		}
		client.printVerbose("entering the compiling phase");
		List<SceneNode> regions = world
				.getAllRegionsAsList();

		int generated;

		int pass = 1;

		do {

			client.printVerbose("->pass " + (pass++));

			generated = 0;

			for (SceneNode sr : regions) {
				if (sr instanceof GroupSector) {
					generated += splitSectorsWithPlanesFrom((GroupSector) sr,
							planes);
				}
			}
		} while (generated != 0);

		int total = 0;

		for (SceneNode sr : world.getAllRegionsAsList()) {

			if (sr instanceof Sector) {
				++total;
			}
		}

		client.printVerbose(" partitioning finished!");
		client.printVerbose(" generated " + total + " leaf sector(s)");
	}

	public Set<Hyperplane> getAllHyperplanesForSector(SpaceRegion sr) {
		HashSet<Hyperplane> planes = new HashSet<Hyperplane>();

		for (Direction d : Direction.values()) {
			planes.add(generateHyperplane((GroupSector) sr, d));
		}

		return planes;
	}

	public Set<Hyperplane> getAllHyperplanes() {
		HashSet<Hyperplane> planes = new HashSet<Hyperplane>();

		for (SceneNode sr : world.getAllRegionsAsList()) {
			if (sr instanceof GroupSector) {
				for (Direction d : Direction.values()) {
					planes.add(generateHyperplane((GroupSector) sr, d));
				}
			}
		}

		return planes;
	}

	public int splitSectorsWithPlanesFrom(GroupSector current,
			Set<Hyperplane> planes) {

		Sector generated;
		List<Sector> toAdd = new ArrayList<Sector>();
		int generatedSectors = 0;

		for (Hyperplane plane : planes) {
			for (SceneNode sr : current.getSons()) {
				if (sr instanceof Sector) {
					generated = split((Sector) sr, plane);

					if (generated != null && !generated.isDegenerate()) {
						toAdd.add(generated);
					}
				}
			}
		}

		generatedSectors += toAdd.size();
		current.getSons().addAll(toAdd);

		return generatedSectors; //current.getSons().size();
	}

	public static Sector split(final Sector sector, final Hyperplane hyperplane) {

		Sector toReturn;
		toReturn = null;
		Vec3 position = sector.getAbsolutePosition();

		if (!Float.isNaN(hyperplane.v.x)) {
			// plane in YZ

			if (position.x < hyperplane.v.x
					&& hyperplane.v.x < (position.x + sector.size.x)) {
				toReturn = new Sector(sector);

				toReturn.size.x = ((position.x + sector.size.x) - hyperplane.v.x);

				toReturn.localPosition.x = (hyperplane.v.x);

				sector.size.x = (hyperplane.v.x) - position.x;
			}

		} else if (!Float.isNaN(hyperplane.v.y)) {
			// plane in XZ
			if (position.y < hyperplane.v.y
					&& hyperplane.v.y < (position.y + sector.size.y)) {
				toReturn = new Sector(sector);

				toReturn.size.y = ((position.y + sector.size.y) - hyperplane.v.y);

				toReturn.localPosition.y = (hyperplane.v.y);
				sector.size.y = (hyperplane.v.y) - position.y;
			}

		} else if (!Float.isNaN(hyperplane.v.z)) {
			// plane in XY
			if (position.z < hyperplane.v.z
					&& hyperplane.v.z < (position.z + sector.size.z)) {
				toReturn = new Sector(sector);

				toReturn.size.z = ((position.z + sector.size.z) - hyperplane.v.z);

				toReturn.localPosition.z = (hyperplane.v.z);
				
				sector.size.z = (hyperplane.v.z) - position.z;
			}
		}
		
		if ( toReturn != null ) {
			
			String id = "_";
			
			if ( sector.parent != null ) {
				id = sector.parent.id;
			}
			toReturn.id = id + "_" + ( ++counter );
		}

		return toReturn;
	}

	@Override
	public String toString() {
		return "World-wide global partitioning";
	}
}
