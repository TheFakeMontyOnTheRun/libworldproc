/**
 * 
 */
package br.odb.worldprocessing;

import java.util.ArrayList;

import br.odb.gameapp.ApplicationClient;
import br.odb.libscene.Constants;
import br.odb.libscene.Hyperplane;
import br.odb.libscene.Sector;
import br.odb.libscene.World;
import br.odb.utils.math.Vec3;

/**
 * @author monty
 * 
 */
public class WorldLocalPartitioner implements WorldProcessor {

	private ApplicationClient client;
	private World world;
	final ArrayList<Sector> generated = new ArrayList<Sector>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

		World subs = new World();
		Sector s;

		for (Sector master : world) {

			s = new Sector(master);
			s.removeAllDoors();
			s.cleanUpMeshes();
			s.cleanUpLinks();
			s.setParent(master.getId());
			s.setIsMaster(false);
			subs.addSector(s);
		}

		for (int c = 0; c < subs.getTotalSectors(); ++c) {
			s = subs.getSector(c);
			splitSectorsWithPlanesFrom(s, subs);
		}

		for (Sector sub : subs) {
			world.addSector(sub);
		}

		client.printVerbose(" partitioning finished!");
		client.printVerbose(" generated " + subs.getTotalSectors()
				+ " leaf sector(s)");
	}

	public static Hyperplane generateHyperplane(final Sector sector,
			final byte kind) {
		float n = 0.0f;

		switch (kind) {
		case Constants.FACE_N:
			n = sector.getZ0();
			break;
		case Constants.FACE_S:
			n = sector.getZ1();
			break;
		case Constants.FACE_W:
			n = sector.getX0();
			break;
		case Constants.FACE_E:
			n = sector.getX1();
			break;
		case Constants.FACE_FLOOR:
			n = sector.getY0();
			break;
		case Constants.FACE_CEILING:
			n = sector.getY1();
			break;
		}

		return new Hyperplane(kind, n, sector);
	}

	private void splitSectorsWithPlanesFrom(Sector current, World workArea) {

		Sector sector;
		Sector splitting;
		Sector generatee;
		Hyperplane hyperplane;

		sector = null;

		Hyperplane[] localPlanes = new Hyperplane[6];

		for (byte d = 0; d < 6; ++d) {

			localPlanes[d] = generateHyperplane(current, d);
		}

		for (int e = 0; e < workArea.getTotalSectors(); ++e) {

			splitting = workArea.getSector(e);

			if (current == splitting)
				continue;

			if (!current.touches(splitting))
				continue;

			generated.clear();
			generated.add(splitting);

			for (int d = 0; d < 6; ++d) {

				hyperplane = localPlanes[d];

				for (int c = 0; c < generated.size(); ++c) {

					generatee = generated.get(c);
					sector = split(generatee, hyperplane);

					if (sector != null && !sector.isDegenerate()
							&& !generated.contains(sector)) {
						sector.setParent(splitting.getParent());
						generated.add(sector);
					}
				}
			}

			int generatedSize = generated.size();

			for (int c = 0; c < generatedSize; ++c) {

				if (!workArea.contains(generated.get(c)))
					workArea.addSector(generated.get(c));
			}
		}
	}

	public static Sector split(final Sector sector, final Hyperplane hyperplane) {

		Sector toReturn;
		toReturn = null;
		Vec3 hyperplaneVector = hyperplane.getVector();

		if (hyperplaneVector.x != Integer.MAX_VALUE) {
			// plane in YZ

			if (sector.getX0() < hyperplaneVector.x
					&& hyperplaneVector.x < sector.getX1()) {
				toReturn = new Sector(sector);
				toReturn.setX0(hyperplaneVector.x);
				sector.setX1(hyperplaneVector.x);
			}

		} else if (hyperplaneVector.y != Integer.MAX_VALUE) {
			// plane in XZ
			if (sector.getY0() < hyperplaneVector.y
					&& hyperplaneVector.y < sector.getY1()) {
				toReturn = new Sector(sector);
				toReturn.setY0(hyperplaneVector.y);
				sector.setY1(hyperplaneVector.y);
			}

		} else if (hyperplaneVector.z != Integer.MAX_VALUE) {
			// plane in XY
			if (sector.getZ0() < hyperplaneVector.z
					&& hyperplaneVector.z < sector.getZ1()) {
				toReturn = new Sector(sector);
				toReturn.setZ0(hyperplaneVector.z);
				sector.setZ1(hyperplaneVector.z);

			}

		}

		if (toReturn != null) {
			toReturn.removeAllDoors();
			toReturn.cleanUpLinks();
			toReturn.cleanUpMeshes();
			sector.setIsMaster(false);
			toReturn.setIsMaster(false);
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
