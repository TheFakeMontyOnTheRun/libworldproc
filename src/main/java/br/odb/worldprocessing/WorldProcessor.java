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
public interface WorldProcessor extends Runnable {
	void setClient(ApplicationClient client);

	void prepareFor(World worldToProcess);
}
