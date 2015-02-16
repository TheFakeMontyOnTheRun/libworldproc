/**
 * 
 */
package br.odb.worldprocessing;

import java.util.ArrayList;
import java.util.List;

import br.odb.gameapp.ApplicationClient;
import br.odb.libscene.SceneNode;
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

		List<SceneNode> toRemove = new ArrayList<>();
		List<SceneNode> sectors = world.getAllRegionsAsList();

		for (SceneNode s1 : sectors) {

			if ( !( s1 instanceof Sector ) ) {
				continue;
			}

			for (SceneNode s2 : sectors) {

				if (s2 == s1)
					continue;

				if ( !( s2 instanceof Sector ) ) {
					continue;
				}

				if ( ( (Sector) s1 ).coincidant( (Sector)s2) && s1.parent != s2.parent ) {

					if (client != null
							&& Utils.getLevel() == Utils.VerbosityLevels.LEVEL_VERBOSE)
						client.printWarning("sector " + s1.id
								+ " contains with sector " + s2.id);

					toRemove.add(s2);

				}
			}
		}

		client.printWarning("removing " + toRemove.size() + " sectors");

		for (SceneNode s : toRemove) {
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
