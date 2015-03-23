/**
 * 
 */
package br.odb.worldprocessing;

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
public class WorldGlobalPartitioner extends WorldProcessor {

	public WorldGlobalPartitioner(ApplicationClient client, World worldToProcess) {
		super(client, worldToProcess);
	}

	static long counter = 0;

	@Override
	public void run() {
		
		client.printVerbose("cleaning leaf nodes for replacement");
		
		counter = 0;
		Set<Hyperplane> planes = new HashSet<Hyperplane>();

		
		generateLeafsForWorld( world );
		planes.addAll( generateHyperplanesForGroupSectorsInWorld( world ) );
		
		client.printVerbose("entering the compiling phase");
		List<SceneNode> regions = world.getAllRegionsAsList();

		

		
			for (SceneNode sr : regions) {
				if (sr instanceof GroupSector) {
					splitSectorsWithPlanesFrom((GroupSector) sr,planes);
				}
			}


		client.printVerbose(" partitioning finished!");
		client.printVerbose(" generated " + getTotalSectors( world ) + " leaf sector(s)");
	}

	int getTotalSectors( World world ) {
		
		int total = 0;
		
		for (SceneNode sr : world.getAllRegionsAsList()) {

			if (sr instanceof Sector) {
				++total;
			}
		}

		return total;
	}

	public static Set<Hyperplane> generateHyperplanesForGroupSectorsInWorld(World world) {
		
		final Set<Hyperplane> toReturn = new HashSet< Hyperplane >();
		
		for (SceneNode sr : world.getAllRegionsAsList()) {
			if (sr instanceof GroupSector) {
				toReturn.addAll(getAllHyperplanesForSector((GroupSector)sr));
			}
		}
		
		return toReturn;
	}
	
	public static void generateLeafsForWorld(World world) {
		Sector sector;
		
		for (SceneNode sr : world.getAllRegionsAsList()) {
			if (sr instanceof GroupSector) {
				sector = new Sector( sr.id );
				sector.localPosition.set( sr.getAbsolutePosition() );
				sector.size.set( ((GroupSector) sr).size );
				((GroupSector) sr).addChild( sector );
			}
		}
	}

	public static Set<Hyperplane> getAllHyperplanesForSector(SpaceRegion sr) {
		Set<Hyperplane> planes = new HashSet<Hyperplane>();

		for (Direction d : Direction.values()) {
			planes.add( new Hyperplane( d, sr ) );
		}

		return planes;
	}

	public static int splitSectorsWithPlanesFrom(GroupSector current,
			Set<Hyperplane> planes) {

		Sector generated;
		Set<Sector> toAdd = new HashSet<Sector>();
		Set<Sector> toRemove = new HashSet<Sector>();
		int changedNodes = 0;
		
		do {
			changedNodes = 0;
			for (Hyperplane plane : planes) {
				
				toAdd.clear();
				
				for (SceneNode sr : current.getSons()) {
					if (sr instanceof Sector) {
						generated = split((Sector) sr, plane);
	
						if (generated != null && !generated.isDegenerate()) {
							toAdd.add(generated);
						}
					}
				}
				
				changedNodes = toAdd.size();
				
				for (SceneNode sr : toAdd ) {
					current.addChild( sr );
				}
			}
		} while( changedNodes != 0 );

		
		for (SceneNode sr : current.getSons()) {
			if (sr instanceof Sector ) {
				if ( ( ( Sector ) sr ).isDegenerate() ) {
					toRemove.add( ( ( Sector ) sr ) );
				}
			}
		}

		current.getSons().removeAll( toRemove );

		return current.getSons().size();
	}

	public static Sector split(final Sector sector, final Hyperplane hyperplane) {

		Sector toReturn;
		toReturn = null;
		Vec3 position = sector.getAbsolutePosition();
		
		if (!Float.isNaN(hyperplane.v.x)) {
			// plane in YZ

			if (position.x < hyperplane.v.x
					&& hyperplane.v.x < (position.x + sector.size.x)) {
				
				toReturn = new Sector( sector.id );
				
				toReturn.localPosition.set( sector.getAbsolutePosition() );
				toReturn.size.set( sector.size );
				
				toReturn.localPosition.x = (hyperplane.v.x);
				toReturn.size.x = sector.size.x - ( hyperplane.v.x - position.x);

				sector.size.x = (hyperplane.v.x) - position.x;
			}

		} else if (!Float.isNaN(hyperplane.v.y)) {
			// plane in XZ
			if (position.y < hyperplane.v.y
					&& hyperplane.v.y < (position.y + sector.size.y)) {
				
				toReturn = new Sector( sector.id );
				
				toReturn.localPosition.set( sector.getAbsolutePosition() );
				toReturn.size.set( sector.size );

				toReturn.size.y = sector.size.y - (hyperplane.v.y - position.y );
				toReturn.localPosition.y = (hyperplane.v.y);
				
				sector.size.y = (hyperplane.v.y) - position.y;
			}

		} else if (!Float.isNaN(hyperplane.v.z)) {
			// plane in XY
			if (position.z < hyperplane.v.z
					&& hyperplane.v.z < (position.z + sector.size.z)) {

				toReturn = new Sector( sector.id );
				
				toReturn.localPosition.set( sector.getAbsolutePosition() );
				toReturn.size.set( sector.size );

				toReturn.size.z = sector.size.z - (hyperplane.v.z - position.z);
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
