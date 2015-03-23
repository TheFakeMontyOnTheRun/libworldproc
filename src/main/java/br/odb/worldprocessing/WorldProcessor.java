/**
 * 
 */
package br.odb.worldprocessing;

import br.odb.gameapp.ApplicationClient;
import br.odb.libscene.World;

/**
 * @author monty
 * 
 */
public abstract class WorldProcessor implements Runnable {
	
	public World world;
	public ApplicationClient client;
	
	public WorldProcessor( ApplicationClient client, World worldToProcess) {
		this.client = client;
		this.world = worldToProcess;
	}
}
