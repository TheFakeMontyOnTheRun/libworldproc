/**
 *
 */
package br.odb.worldprocessing;

import br.odb.gameapp.ApplicationClient;
import br.odb.libscene.GroupSector;
import br.odb.libscene.SceneNode;
import br.odb.libscene.World;
import br.odb.utils.Direction;

/**
 * @author monty
 * 
 */
public class GroupSectorSnapper extends WorldProcessor {

	public GroupSectorSnapper(ApplicationClient client, World worldToProcess) {
		super(client, worldToProcess);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

		for (SceneNode sr : world.getAllRegionsAsList()) {
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
			case CEILING:
				current.size.y = Math.round(current.size.y);
				break;
			
			case FLOOR:
			default:
				current.localPosition.y = Math.round(current.localPosition.y);
				break;
			}
		}
	}
	
	@Override
	public String toString() {

		return "Snapping sectors to grid";
	}
}
