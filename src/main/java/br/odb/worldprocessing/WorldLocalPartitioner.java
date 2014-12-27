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
		Set<Hyperplane> planes = new HashSet<Hyperplane>();

		for (SpaceRegion sr : Utils.getAllRegionsAsList(world.masterSector)) {
			if (sr instanceof GroupSector) {
				((GroupSector) sr).getSons().add(new Sector(sr));

				planes.addAll(getAllHyperplanesForSector(sr));
			}
		}

		List<SpaceRegion> regions = Utils
				.getAllRegionsAsList(world.masterSector);

		int generated;

		int pass = 1;

		do {

			client.printVerbose("->pass " + (pass++));

			generated = 0;

			for (SpaceRegion sr : regions) {
				if (sr instanceof GroupSector) {
					generated += splitSectorsWithPlanesFrom((GroupSector) sr,
							planes);
				}
			}
		} while (generated != 0);

		int total = 0;

		for (SpaceRegion sr : Utils.getAllRegionsAsList(world.masterSector)) {

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

		for (SpaceRegion sr : Utils.getAllRegionsAsList(world.masterSector)) {
			if (sr instanceof GroupSector) {
				for (Direction d : Direction.values()) {
					planes.add(generateHyperplane((GroupSector) sr, d));
				}
			}
		}

		return planes;
	}

	public static Hyperplane generateHyperplane(final GroupSector sector,
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
