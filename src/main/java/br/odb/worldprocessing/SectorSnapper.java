/**
 *
 */
package br.odb.worldprocessing;

import br.odb.gameapp.ApplicationClient;
import br.odb.libscene.GroupSector;
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
			if (sr instanceof GroupSector) {

				snapSectorConnections(world, (GroupSector) sr);
			}
		}
	}

	private void snapSectorConnections(World world, GroupSector current) {

		for (Direction d : Direction.values()) {

			switch (d) {

			case N:
				current.localPosition.z = Math.round(current.localPosition.z);
				break;
			case S:
				current.size.z = Math.round(current.size.z);
				break;
			case W:
				current.localPosition.x = Math.round(current.localPosition.x);
				break;
			case E:
				current.size.x = Math.round(current.size.x);
				break;
			case FLOOR:
				current.localPosition.y = Math.round(current.localPosition.y);
				break;
			case CEILING:
				current.size.y = Math.round(current.size.y);
				break;
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
