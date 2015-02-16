/**
 * 
 */
package br.odb.worldprocessing;

import java.util.ArrayList;

import br.odb.gameapp.ApplicationClient;
import br.odb.libscene.SceneNode;
import br.odb.libscene.SpaceRegion;
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

		
		ArrayList< SpaceRegion > toRemove = new ArrayList<SpaceRegion>();

		for ( SceneNode sr : world.getAllRegionsAsList() ) {

			if ( !( sr instanceof SpaceRegion ) ) {
				continue;
			}
			
			if ( ((SpaceRegion)sr).isDegenerate()) {

				if (client != null
						&& Utils.getLevel() == Utils.VerbosityLevels.LEVEL_VERBOSE)
					client.printWarning("Sector " + sr.id
							+ " is degenerated. Removing.");

				toRemove.add( ((SpaceRegion)sr) );
			}
		}

		for (SpaceRegion s : toRemove) {
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
		this.client = client;
	}

	@Override
	public String toString() {

		return "Removing degenerate sectors";
	}
}
