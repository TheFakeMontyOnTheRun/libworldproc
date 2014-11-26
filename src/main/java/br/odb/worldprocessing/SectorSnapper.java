/**
 *
 */
package br.odb.worldprocessing;

import br.odb.gameapp.ApplicationClient;
import br.odb.libscene.Sector;
import br.odb.libscene.SpaceRegion;
import br.odb.libscene.World;
import br.odb.utils.Direction;

/**
 * @author monty
 * 
 */
public class SectorSnapper implements WorldProcessor {

	private World world;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

		for (SpaceRegion sr : Utils.getAllRegionsAsList(world.masterSector)) {
			if (sr instanceof Sector) {

				snapSectorConnections(world, (Sector) sr);
			}
		}
	}

	private void snapSectorConnections(World world, Sector current) {

		Sector sector;
		for (Direction d : Direction.values()) {

			if (!current.connection.containsKey(d)) {
				continue;
			}

			sector = current.connection.get(d);

			synchronized (sector) {

				switch (d) {

				case N:
					current.position.z = (sector.position.z + sector.size.z);
					break;
				case S:
					current.size.z = (sector.position.z) - current.position.z;
					break;
				case W:
					current.position.x = (sector.position.x + sector.size.x);
					break;
				case E:
					current.size.x = (sector.position.x) - current.position.x;
					break;
				case FLOOR:
					current.position.y = (sector.position.y + sector.size.y);
					break;
				case CEILING:
					current.size.y = (sector.position.y) - current.position.y;
					break;
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
	public String toString() {

		return "Snapping sectors to grid";
	}

	@Override
	public void setClient(ApplicationClient client) {
		
	}
}
