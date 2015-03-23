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
public class RemoveLeafSectors extends WorldProcessor {

	public RemoveLeafSectors(ApplicationClient client, World worldToProcess) {
		super(client, worldToProcess);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		List< SceneNode > toRemove = new ArrayList<>();

		for ( SceneNode sr : world.getAllRegionsAsList() ) {

			if ( sr instanceof Sector ) {

				toRemove.add( sr );
			}
		}

		for (SceneNode s : toRemove) {
			client.printWarning("removing leaf " + toRemove.size() + " sectors");
			Utils.removeSector( world, s );
		}
	}

	@Override
	public String toString() {

		return "Removing leaf sectors";
	}
}