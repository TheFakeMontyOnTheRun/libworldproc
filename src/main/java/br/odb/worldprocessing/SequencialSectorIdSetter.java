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
public class SequencialSectorIdSetter implements WorldProcessor {

	World world;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

		for (int c = 0; c < world.getTotalSectors(); ++c) {
			world.reassignConnectionsWith(world.getSector(c).getId(), c);
			world.getSector(c).setId(c);
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

	}

	@Override
	public String toString() {

		return "Reordering sector ID's";
	}
}
