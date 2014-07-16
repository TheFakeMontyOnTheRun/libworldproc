/**
 * 
 */
package br.odb.worldprocessing;

import br.odb.gameapp.ApplicationClient;
import br.odb.gameworld.Actor;
import br.odb.libscene.Actor3D;
import br.odb.libscene.Sector;
import br.odb.libscene.World;

/**
 * @author monty
 * 
 */
public class StartSectorLocator implements WorldProcessor {

	private ApplicationClient client;
	private World world;
	private int sectorToStart;

	public StartSectorLocator(int desiredMasterSector) {
		sectorToStart = desiredMasterSector;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

		if (sectorToStart > 0 && sectorToStart < world.getTotalSectors()) {
			for (Sector current : world) {

				if (current.isMaster())
					continue;

				if (current.getParent() == sectorToStart) {

					Actor3D cameraActor = new Actor3D();
					cameraActor.setCurrentSector(current.getId());
					world.addActor(cameraActor);
					client.printVerbose("starting at sector " + current.getId());
					return;
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.odb.libscene.WorldProcessor#setClient(br.odb.ApplicationClient)
	 */
	@Override
	public void setClient(ApplicationClient client) {
		this.client = client;
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

		return "Finding placement for camera actor";
	}
}
