/**
 * 
 */
package br.odb.worldprocessing;

import br.odb.gameapp.ApplicationClient;
import br.odb.libscene.GroupSector;
import br.odb.libscene.Sector;
import br.odb.libscene.SpaceRegion;
import br.odb.libscene.World;
import br.odb.utils.Direction;

/**
 * @author monty
 * 
 */
public class WorldLocalPartitioner implements WorldProcessor {

	private ApplicationClient client;
	private World world;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

		for (SpaceRegion sr : Utils.getAllRegionsAsList(world.masterSector)) {
			if (sr instanceof GroupSector) {
				((GroupSector) sr).getSons().add(new Sector(sr));
			}
		}

		for (SpaceRegion sr : Utils.getAllRegionsAsList(world.masterSector)) {
			if (sr instanceof GroupSector) {
				splitSectorsWithPlanesFrom((GroupSector) sr, world);
			}
		}

		int total = 0;

		for (SpaceRegion sr : Utils.getAllRegionsAsList(world.masterSector)) {

			if (sr instanceof Sector) {
				++total;
			}
		}

		client.printVerbose(" partitioning finished!");
		client.printVerbose(" generated " + total + " leaf sector(s)");
	}

	public static Hyperplane generateHyperplane(final GroupSector sector,
			final Direction kind) {
		float n = 0.0f;

		switch (kind) {
		case N:
			n = sector.position.z;
			break;
		case S:
			n = sector.position.z + sector.size.z;
			break;
		case W:
			n = sector.position.x;
			break;
		case E:
			n = sector.position.x + sector.size.x;
			break;
		case FLOOR:
			n = sector.position.y;
			break;
		case CEILING:
			n = sector.position.y + sector.size.y;
			break;
		}

		return new Hyperplane(kind, n, sector);
	}

	private void splitSectorsWithPlanesFrom(GroupSector current, World workArea) {

		Sector generated;

		Hyperplane[] localPlanes = new Hyperplane[6];

		for (Direction d : Direction.values()) {
			localPlanes[d.ordinal()] = generateHyperplane(current, d);
		}

		for (SpaceRegion sr : Utils.getAllRegionsAsList(world.masterSector)) {
			if (sr instanceof Sector) {
				for (Hyperplane h : localPlanes) {

					generated = split((Sector) sr, h);

					if (generated != null && !generated.isDegenerate()) {
						current.getSons().add(generated);
					}
				}
			}
		}
	}

	public static Sector split(final Sector sector, final Hyperplane hyperplane) {

		Sector toReturn;
		toReturn = null;

		if (hyperplane.v.x != Integer.MAX_VALUE) {
			// plane in YZ

			if (sector.position.x < hyperplane.v.x
					&& hyperplane.v.x < (sector.position.x + sector.size.x)) {
				toReturn = new Sector(sector);
				toReturn.position.x = (hyperplane.v.x);
				sector.size.x = (hyperplane.v.x) - sector.position.x;
			}

		} else if (hyperplane.v.y != Integer.MAX_VALUE) {
			// plane in XZ
			if (sector.position.y < hyperplane.v.y
					&& hyperplane.v.y < (sector.position.y + sector.size.y)) {
				toReturn = new Sector(sector);
				toReturn.position.y = (hyperplane.v.y);
				sector.size.y = (hyperplane.v.y) - sector.position.y;
			}

		} else if (hyperplane.v.z != Integer.MAX_VALUE) {
			// plane in XY
			if (sector.position.z < hyperplane.v.z
					&& hyperplane.v.z < (sector.position.z + sector.size.z)) {
				toReturn = new Sector(sector);
				toReturn.position.z = (hyperplane.v.z);
				sector.size.z = (hyperplane.v.z) - sector.position.z;
			}
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
