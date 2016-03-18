package libworldproc;

import org.junit.Assert;
import org.junit.Test;

import br.odb.gameapp.ApplicationClient;

import br.odb.libscene.World;
import br.odb.worldprocessing.SectorLinker;
import br.odb.worldprocessing.WorldProcessor;

import static org.mockito.Mockito.mock;

public class SectorLinkerTest {

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
