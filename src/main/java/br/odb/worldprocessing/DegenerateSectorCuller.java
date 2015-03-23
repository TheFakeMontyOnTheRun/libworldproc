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
public class DegenerateSectorCuller extends WorldProcessor {

	public DegenerateSectorCuller(ApplicationClient client, World worldToProcess) {
		super(client, worldToProcess);
	}

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
				toRemove.add( ((SpaceRegion)sr) );
			}
		}

		for (SpaceRegion s : toRemove) {
			Utils.removeSector( world, s );
		}
	}

	@Override
	public String toString() {

		return "Removing degenerate sectors";
	}
}
