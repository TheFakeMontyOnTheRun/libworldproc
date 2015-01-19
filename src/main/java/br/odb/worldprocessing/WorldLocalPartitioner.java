/**
 * 
 */
package br.odb.worldprocessing;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.odb.gameapp.ApplicationClient;
import br.odb.libscene.GroupSector;
import br.odb.libscene.Sector;
import br.odb.libscene.SpaceRegion;
import br.odb.libscene.World;
import br.odb.utils.Direction;
import br.odb.utils.math.Vec3;

/**
 * @author monty
 * 
 */
public class WorldLocalPartitioner implements WorldProcessor {

	private ApplicationClient client;
	private World world;

	@Override
	public void run() {

		int generated;
		int pass = 1;
		Set<Hyperplane> planes = new HashSet<Hyperplane>();
		List<SpaceRegion> regions;

		for (SpaceRegion sr : world.getAllRegionsAsList()) {
			if (sr instanceof GroupSector) {
				((GroupSector) sr).getSons().add(new Sector(sr));
			}
		}

		do {
			planes.clear();
			generated = 0;

			regions = world.getAllRegionsAsList();

			for (SpaceRegion sr : regions) {
				if (sr instanceof Sector) {
					planes.addAll(getAllHyperplanesForSector(sr));
				}
			}

			for (SpaceRegion sr : regions) {
				if (sr instanceof GroupSector) {
					generated += splitSectorsWithPlanesFrom((GroupSector) sr,
							planes);
				}
			}

			client.printVerbose("->pass " + (pass++) + " generated: "
					+ generated);
		} while (generated != 0);

		int total = 0;

		for (SpaceRegion sr : world.getAllRegionsAsList()) {

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
			planes.add(generateHyperplane(sr, d));
		}

		return planes;
	}

	public Set<Hyperplane> getAllHyperplanes() {
		HashSet<Hyperplane> planes = new HashSet<Hyperplane>();

		for (SpaceRegion sr : world.getAllRegionsAsList()) {
			if (sr instanceof GroupSector) {
				for (Direction d : Direction.values()) {
					planes.add(generateHyperplane((GroupSector) sr, d));
				}
			}
		}

		return planes;
	}

	public static Hyperplane generateHyperplane(final SpaceRegion sector,
			final Direction kind) {
		float n = 0.0f;
		Vec3 position = sector.getAbsolutePosition();

		switch (kind) {
		case N:
			n = position.z;
			break;
		case S:
			n = position.z + sector.size.z;
			break;
		case W:
			n = position.x;
			break;
		case E:
			n = position.x + sector.size.x;
			break;
		case FLOOR:
			n = position.y;
			break;
		case CEILING:
			n = position.y + sector.size.y;
			break;
		}

		return new Hyperplane(kind, n, sector);
	}

	public int splitSectorsWithPlanesFrom(GroupSector current,
			Set<Hyperplane> planes) {

		Sector generated;
		HashSet<Sector> toAdd = new HashSet<Sector>();
		int generatedSectors = 0;

		for (Hyperplane plane : planes) {

			// ////--------
			if (!plane.generator.intersects(current)
					&& !current.intersects(plane.generator)) {
				continue;
			}
			// ////--------
			toAdd.clear();

			for (SpaceRegion sr : current.getSons()) {
				if (sr instanceof Sector) {
					generated = split((Sector) sr, plane);

					if (generated != null && !generated.isDegenerate()) {
						toAdd.add(generated);
					}
				}
			}
			generatedSectors += toAdd.size();
			current.getSons().addAll(toAdd);
		}

		return generatedSectors;
	}

	public static Sector split(final Sector sector, final Hyperplane hyperplane) {

		Sector toReturn;
		toReturn = null;
		Vec3 position = sector.getAbsolutePosition();

		if (hyperplane.stabYZ(sector)) {
			toReturn = new Sector(sector);

			toReturn.size.x = ((position.x + sector.size.x) - hyperplane.v.x);

			toReturn.localPosition.x = (hyperplane.v.x);

			sector.connection.put( Direction.E, toReturn );
			toReturn.connection.put( Direction.W, sector );
			
			sector.size.x = (hyperplane.v.x) - position.x;

		} else if (hyperplane.stabXZ(sector)) {
			toReturn = new Sector(sector);

			toReturn.size.y = ((position.y + sector.size.y) - hyperplane.v.y);

			toReturn.localPosition.y = (hyperplane.v.y);
			sector.size.y = (hyperplane.v.y) - position.y;

			sector.connection.put( Direction.CEILING, toReturn );
			toReturn.connection.put( Direction.FLOOR, sector );
			
			
		} else if (hyperplane.stabXY(sector)) {

			toReturn = new Sector(sector);

			toReturn.size.z = ((position.z + sector.size.z) - hyperplane.v.z);

			toReturn.localPosition.z = (hyperplane.v.z);
			sector.size.z = (hyperplane.v.z) - position.z;

			sector.connection.put( Direction.S, toReturn );
			toReturn.connection.put( Direction.N, sector );
		}

		return toReturn;
	}

	@Override
	public void setClient(ApplicationClient client) {
		this.client = client;
	}

	@Override
	public void prepareFor(World worldToProcess) {
		world = worldToProcess;
	}

	@Override
	public String toString() {

		return "World-wide local partitioning";
	}
}
