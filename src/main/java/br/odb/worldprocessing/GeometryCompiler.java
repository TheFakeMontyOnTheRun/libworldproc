/**
 * 
 */
package br.odb.worldprocessing;

import java.util.ArrayList;
import java.util.Calendar;

import br.odb.gameapp.ApplicationClient;
import br.odb.gameapp.ConsoleApplication;
import br.odb.libscene.World;

/**
 * @author monty
 * 
 */
public abstract class GeometryCompiler extends ConsoleApplication implements
		WorldProcessor {

	protected World world;
	protected ArrayList<WorldProcessor> processingPipeline;
	protected ApplicationClient client;

	public GeometryCompiler() {
		world = new World();
		processingPipeline = new ArrayList<WorldProcessor>();
	}

	@Override
	public void run() {

		for (WorldProcessor p : processingPipeline) {

			if (client != null) {

				p.setClient(client);
				client.printVerbose("Executing step:" + p + " at "
						+ Calendar.getInstance().getTime().toString());
			}

			p.prepareFor(world);
			p.run();
		}

		if (client != null) {

			client.printVerbose("finished running " + this + " at " + Calendar.getInstance().getTime().toString() );
		}
	}

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

		return "Geometry Compilation";
	}
}
