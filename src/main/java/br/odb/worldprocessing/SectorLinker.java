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
public class SectorLinker implements WorldProcessor {

	private World world;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		world.checkForHardLinks();
	}

	@Override
	public void setClient(ApplicationClient client) {
	}

	@Override
	public void prepareFor(World worldToProcess) {
		world = worldToProcess;
	}

	@Override
	public String toString() {
		return "Settings links between neighbour sectors";
	}
}
