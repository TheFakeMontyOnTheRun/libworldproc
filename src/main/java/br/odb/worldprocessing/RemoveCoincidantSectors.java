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
public class RemoveCoincidantSectors implements WorldProcessor {

	private ApplicationClient client;
	private World world;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

		ArrayList<Sector> toRemove = new ArrayList<Sector>();
		toRemove.clear();

		for (Sector s1 : world) {

			if (toRemove.contains(s1))
				continue;

			if (s1.isMaster())
				continue;

			for (Sector s2 : world) {

				if (s2 == s1)
					continue;

				if (s2.isMaster())
					continue;

				if (toRemove.contains(s2))
					continue;

				if (s1.contains(s2)) {

					if (client != null
							&& LogUtils.getLevel() == LogUtils.LEVEL_VERBOSE)
						client.printWarning("sector " + s1.getId()
								+ " contains with sector " + s2.getId());

					toRemove.add(s2);

				} else if (s1.coincidant(s2)) {

					if (client != null
							&& LogUtils.getLevel() == LogUtils.LEVEL_VERBOSE)
						client.printWarning("sector " + s1.getId()
								+ " coincides sector " + s2.getId());

					toRemove.add(s2);
				}
			}
		}

		for (int c = 0; c < toRemove.size(); ++c) {
			world.removeSector(toRemove.get(c), false);
		}
	}

	@Override
	public void setClient(ApplicationClient client) {
		this.client = client;
	}

	@Override
	public void prepareFor(World worldToProcess) {
		world = worldToProcess;
	}

	@Override
	public String toString() {

		return "Removing coincidant and internal sectors";
	}
}
