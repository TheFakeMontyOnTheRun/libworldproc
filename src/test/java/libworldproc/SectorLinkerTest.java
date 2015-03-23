package libworldproc;

import org.junit.Assert;
import org.junit.Test;

import br.odb.gameapp.ApplicationClient;
import br.odb.gameapp.DummyClient;
import br.odb.libscene.World;
import br.odb.worldprocessing.SectorLinker;
import br.odb.worldprocessing.WorldProcessor;

public class SectorLinkerTest {

	@Test
	public void testSectorLinker() {
		WorldProcessor processor;
		World world = null;
		ApplicationClient client = null;

		processor = new SectorLinker(client, world);

		Assert.assertNull(processor.client);
		Assert.assertNull(processor.world);

		client = new DummyClient();
		processor = new SectorLinker(client, world);

		Assert.assertNotNull(processor.client);
		Assert.assertNull(processor.world);

		world = new World();
		processor = new SectorLinker(client, world);

		Assert.assertNotNull(processor.client);
		Assert.assertNotNull(processor.world);
	}

	@Test
	public void testRun() {
		World world = new World();
		WorldProcessor processor = new SectorLinker( null, world);
		processor.run();
	}

	@Test
	public void testToString() {
		Assert.assertNotNull(new SectorLinker(null, null).toString());
	}

}
