/**
 * 
 */
package br.odb.worldprocessing;

import br.odb.gameapp.ApplicationClient;
import br.odb.libscene.World;

/**
 * @author monty
 * 
 */
public class RemoveFirstMasterSector implements WorldProcessor {

	private World world;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

		world.removeSector(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.odb.libscene.WorldProcessor#setClient(br.odb.ApplicationClient)
	 */
	@Override
	public void setClient(ApplicationClient client) {
		// TODO should be the same as its father

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

		return "Removing first sector";
	}
}
