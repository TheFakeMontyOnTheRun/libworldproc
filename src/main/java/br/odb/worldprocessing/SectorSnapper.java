/**
 *
 */
package br.odb.worldprocessing;

import br.odb.gameapp.ApplicationClient;
import br.odb.gameworld.Direction;
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
		for (Direction d : Direction.values()) {

			link = current.getLink(d);

			if (link != Constants.NO_LINK) {

				if (link < 0 || link > world.getTotalSectors()) {

					client.printError("Sector " + current
							+ " has a wrong link.");

				}

				sector = world.getSector(link);

				synchronized (sector) {

					switch (d) {

					case N:
						current.setZ0(sector.getZ1());
						break;
					case S:
						current.setZ1(sector.getZ0());
						break;
					case W:
						current.setX0(sector.getX1());
						break;
					case E:
						current.setX1(sector.getX0());
						break;
					case FLOOR:
						current.setY0(sector.getY1());
						break;
					case CEILING:
						current.setY1(sector.getY0());
						break;
					}
				}
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
