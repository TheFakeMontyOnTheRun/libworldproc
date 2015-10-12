/**
 * 
 */
package br.odb.worldprocessing;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.odb.gameapp.ApplicationClient;
import br.odb.libscene.GroupSector;
import br.odb.libscene.SceneNode;
import br.odb.libscene.World;

/**
 * @author monty
 * 
 */
public class WorldGlobalPartitioner extends WorldPartitioner {

	public WorldGlobalPartitioner(ApplicationClient client, World worldToProcess) {
		super(client, worldToProcess);
	}


	@Override
	public void run() {
		
		client.printVerbose("cleaning leaf nodes for replacement");
		
		counter = 0;
		Set<Plane> planes = new HashSet<Plane>();

		
		generateLeafsForWorld( world );
		
		planes.addAll( generateHyperplanesForGroupSectorsInWorld( world ) );
		
		client.printVerbose("entering the compiling phase");
		
		List<SceneNode> regions = world.getAllRegionsAsList();
		
		for (SceneNode sr : regions) {
			if (sr instanceof GroupSector) {
				splitSectorsWithPlanesFrom((GroupSector) sr,planes);
			}
		}

		client.printVerbose(" partitioning finished!");
		client.printVerbose(" generated " + getTotalSectors( world ) + " leaf sector(s)");
	}

	@Override
	public String toString() {
		return "World-wide global partitioning";
	}
}
