/**
 * 
 */
package libworldproc;

import org.junit.Assert;
import org.junit.Test;

import br.odb.gameapp.ApplicationClient;
import br.odb.gameapp.DummyClient;
import br.odb.libscene.GroupSector;
import br.odb.libscene.SceneNode;
import br.odb.libscene.Sector;
import br.odb.libscene.World;
import br.odb.worldprocessing.RemoveLeafSectors;
import br.odb.worldprocessing.WorldProcessor;

/**
 * @author monty
 *
 */
public class RemoveLeafSectorsTest {

	/**
	 * Test method for {@link br.odb.worldprocessing.RemoveLeafSectors#RemoveLeafSectors(br.odb.gameapp.ApplicationClient, br.odb.libscene.World)}.
	 */
	@Test
	public void testRemoveLeafSectors() {
		WorldProcessor processor;
		World world = null;
		ApplicationClient client = null;
		
		processor = new RemoveLeafSectors( client, world );
		
		Assert.assertNull( processor.client );
		Assert.assertNull( processor.world );
		
		client = new DummyClient();
		processor = new RemoveLeafSectors( client, world );
		
		Assert.assertNotNull( processor.client );
		Assert.assertNull( processor.world );
		
		
		world = new World();
		processor = new RemoveLeafSectors( client, world );
		
		Assert.assertNotNull( processor.client );
		Assert.assertNotNull( processor.world );
	}

	/**
	 * Test method for {@link br.odb.worldprocessing.RemoveLeafSectors#run()}.
	 */
	@Test
	public void testRun() {
		GroupSector master = new GroupSector( "master" );
		
		GroupSector slave1 = new GroupSector( "subject1" );
		GroupSector slave2 = new GroupSector( "subject4" );
		Sector sector1 = new Sector( "sector1" );
		Sector sector2 = new Sector( "sector2" );
		Sector sector3 = new Sector( "sector3" );
		Sector sector4 = new Sector( "sector4" );
		Sector sector5 = new Sector( "sector5" );
		Sector sector6 = new Sector( "sector6" );
		Sector sector7 = new Sector( "sector7" );
		Sector sector8 = new Sector( "sector8" );
		SceneNode node = new SceneNode( "untouchable" );
		
		master.addChild( slave1 );
		master.addChild( slave2 );
		master.addChild( node );
		
		slave1.addChild( sector1 );
		slave1.addChild( sector2 );
		slave1.addChild( sector3 );
		slave1.addChild( sector4 );
		
		slave2.addChild( sector5 );
		slave2.addChild( sector6 );
		slave2.addChild( sector7 );
		slave2.addChild( sector8 );
		
		World world = new World( master );
		WorldProcessor processor = new RemoveLeafSectors( new DummyClient(), world );
		processor.run();
		
		Assert.assertEquals( 0, slave1.getSons().size() );
		Assert.assertEquals( 0, slave2.getSons().size() );
	}

	/**
	 * Test method for {@link br.odb.worldprocessing.RemoveLeafSectors#toString()}.
	 */
	@Test
	public void testToString() {
		Assert.assertNotNull( new RemoveLeafSectors( null, null ).toString() );
	}
}
