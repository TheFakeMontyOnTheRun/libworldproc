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
public class SectorLinker extends WorldProcessor {

	public SectorLinker(ApplicationClient client, World worldToProcess) {
		super(client, worldToProcess);
	}

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
	public String toString() {
		return "Settings links between neighbour sectors";
	}
}
