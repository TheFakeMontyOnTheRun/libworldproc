/**
 * 
 */
package br.odb.worldprocessing;

import java.util.ArrayList;
import java.util.List;

import br.odb.gameapp.ApplicationClient;
import br.odb.libscene.GroupSector;
import br.odb.libscene.SpaceRegion;
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

		ArrayList<SpaceRegion> toRemove = new ArrayList<SpaceRegion>();
		List<SpaceRegion> sectors = Utils
				.getAllRegionsAsList(world.masterSector);

		for (SpaceRegion s1 : sectors) {

			if (s1 instanceof GroupSector) {
				continue;
			}

			for (SpaceRegion s2 : sectors) {

				if (s2 == s1)
					continue;

				if (s2 instanceof GroupSector) {
					continue;
				}

				if (s1.equals(s2)) {

					if (client != null
							&& Utils.getLevel() == Utils.VerbosityLevels.LEVEL_VERBOSE)
						client.printWarning("sector " + s1.id
								+ " contains with sector " + s2.id);

					toRemove.add(s2);

				} else if (s1.equals(s2)) {

					if (client != null
							&& Utils.getLevel() == Utils.VerbosityLevels.LEVEL_VERBOSE)
						client.printWarning("sector " + s1.id
								+ " coincides sector " + s2.id);

					toRemove.add(s2);
				}
			}
		}

		client.printWarning("removing " + toRemove.size() + " sectors");

		for (SpaceRegion s : toRemove) {
			Utils.removeSector(world, s);
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
