/**
 * 
 */
package br.odb.worldprocessing;

import java.util.ArrayList;

import br.odb.gameapp.ApplicationClient;
import br.odb.libscene.Sector;
import br.odb.libscene.World;

/**
 * @author monty
 * 
 */
public class DegenerateSectorCuller implements WorldProcessor {

	World world;
	private ApplicationClient client;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

		Sector sector;
		ArrayList<Sector> toRemove = new ArrayList<Sector>();

		for (int c = 0; c < world.getTotalSectors(); ++c) {

			sector = world.getSector(c);

			if (sector.isDegenerate()) {

				if (client != null
						&& LogUtils.getLevel() == LogUtils.LEVEL_VERBOSE)
					client.printWarning("Sector " + c
							+ " is degenerated. Removing.");

				toRemove.add(sector);
			}
		}

		for (Sector s : toRemove) {
			world.removeSector(s, true);
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
		this.client = client;
	}

	@Override
	public String toString() {

		return "Removing degenerate sectors";
	}
}
