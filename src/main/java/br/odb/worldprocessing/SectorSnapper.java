/**
 *
 */
package br.odb.worldprocessing;

import br.odb.gameapp.ApplicationClient;
import br.odb.gameworld.exceptions.InvalidSlotException;
import br.odb.libscene.Constants;
import br.odb.libscene.Sector;
import br.odb.libscene.World;

/**
 * @author monty
 * 
 */
public class SectorSnapper implements WorldProcessor {

	private World world;
	private ApplicationClient client;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		for (Sector current : world) {

			snapSectorConnections(world, current);
		}
	}

	private void snapSectorConnections(World world, Sector current) {

		Sector sector;
		int link;

		for (int d = 0; d < 6; ++d) {

			try {
				link = current.getLink(d);

				if (link != Constants.NO_LINK) {

					if (link < 0 || link > world.getTotalSectors()) {

						client.printError("Sector " + current
								+ " has a wrong link.");

					}

					sector = world.getSector(link);

					synchronized (sector) {

						switch (d) {

						case Constants.FACE_N:
							current.setZ0(sector.getZ1());
							break;
						case Constants.FACE_S:
							current.setZ1(sector.getZ0());
							break;
						case Constants.FACE_W:
							current.setX0(sector.getX1());
							break;
						case Constants.FACE_E:
							current.setX1(sector.getX0());
							break;
						case Constants.FACE_FLOOR:
							current.setY0(sector.getY1());
							break;
						case Constants.FACE_CEILING:
							current.setY1(sector.getY0());
							break;
						}
					}
				}
			} catch (InvalidSlotException e) {

				if (client != null
						&& LogUtils.getLevel() == LogUtils.LEVEL_VERBOSE) {

					client.printError("Error - Invalid Slot. Sector: "
							+ current + " slot: " + d + " exception: " + e);
				}
				e.printStackTrace();
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.odb.libscene.WorldProcessor#prepareFor(br.odb.libscene.World)
	 */
	@Override
	public void prepareFor(World worldToProcess) {

		world = worldToProcess;
	}

	@Override
	public void setClient(ApplicationClient client) {
		this.client = client;
	}

	@Override
	public String toString() {

		return "Snapping sectors to grid";
	}
}
