/**
 * 
 */
package br.odb.worldprocessing;

import br.odb.gameapp.ApplicationClient;
import br.odb.libscene.Sector;
import br.odb.libscene.World;

/**
 * @author monty
 * 
 */
public class AddFirstMasterSector implements WorldProcessor {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	private World world;

	@Override
	public void run() {
		Sector master = new Sector();
		master.setIsMaster(true);
		world.addSectorAtIndex(master, 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.odb.libscene.WorldProcessor#setClient(br.odb.ApplicationClient)
	 */
	@Override
	public void setClient(ApplicationClient client) {
		// TODO This should inherit the client from its master.

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

		return "Adding first sector";
	}

}
