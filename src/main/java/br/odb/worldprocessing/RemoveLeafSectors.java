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
public class RemoveLeafSectors implements WorldProcessor {

	World world;

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
			Utils.removeSector( world, s );
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

		return "Removing leaf sectors";
	}
}