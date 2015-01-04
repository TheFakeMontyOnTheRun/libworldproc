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

		for (SpaceRegion sr : world.getAllRegionsAsList()) {
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
					current.localPosition.z = (float) Math.floor(sector.localPosition.z + sector.size.z);
					break;
				case S:
					current.size.z = (float) Math.floor( (sector.localPosition.z) - current.localPosition.z );
					break;
				case W:
					current.localPosition.x = (float) Math.floor(sector.localPosition.x + sector.size.x);
					break;
				case E:
					current.size.x = (float) Math.floor( (sector.localPosition.x) - current.localPosition.x );
					break;
				case FLOOR:
					current.localPosition.y = (float) Math.floor(sector.localPosition.y + sector.size.y);
					break;
				case CEILING:
					current.size.y = (float) Math.floor( (sector.localPosition.y) - current.localPosition.y );
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
