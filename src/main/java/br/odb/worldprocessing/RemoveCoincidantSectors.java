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
public class RemoveCoincidantSectors extends WorldProcessor {


	public RemoveCoincidantSectors(ApplicationClient client,
			World worldToProcess) {
		super(client, worldToProcess);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

		Utils utils = new Utils();
		
		List<SceneNode> toRemove = new ArrayList<>();
		List<SceneNode> sectors = world.getAllRegionsAsList();
		Sector s1;
		Sector s2;
		
		for ( int c = 0; c < sectors.size(); ++c ) {
			
			if ( !( sectors.get( c ) instanceof Sector ) ) {
				continue;
			}
			
			s1 = (Sector) sectors.get( c );

			for ( int d = c + 1; d < sectors.size(); ++d ) {
				
				if ( !( sectors.get( d ) instanceof Sector ) ) {
					continue;
				}
				
				s2 = (Sector) sectors.get( d );

				if ( ( (Sector) s1 ).coincidant( (Sector)s2) ) {
					toRemove.add(s2);
				}
			}
		}

		for (SceneNode s : toRemove) {
			utils.removeSector(world, s);
		}
	}

	@Override
	public String toString() {
		return "Removing coincidant and internal sectors";
	}
}
