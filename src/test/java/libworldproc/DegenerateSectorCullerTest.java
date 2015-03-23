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
import br.odb.libscene.World;
import br.odb.worldprocessing.DegenerateSectorCuller;
import br.odb.worldprocessing.WorldProcessor;

/**
 * @author monty
 *
 */
public class DegenerateSectorCullerTest {

	/**
	 * Test method for {@link br.odb.worldprocessing.DegenerateSectorCuller#DegenerateSectorCuller(br.odb.gameapp.ApplicationClient, br.odb.libscene.World)}.
	 */
	@Test
	public void testDegenerateSectorCuller() {
		WorldProcessor processor;
		World world = null;
		ApplicationClient client = null;
		
		processor = new DegenerateSectorCuller( client, world );
		
		Assert.assertNull( processor.client );
		Assert.assertNull( processor.world );
		
		client = new DummyClient();
		processor = new DegenerateSectorCuller( client, world );
		
		Assert.assertNotNull( processor.client );
		Assert.assertNull( processor.world );
		
		
		world = new World();
		processor = new DegenerateSectorCuller( client, world );
		
		Assert.assertNotNull( processor.client );
		Assert.assertNotNull( processor.world );
	}

	/**
	 * Test method for {@link br.odb.worldprocessing.DegenerateSectorCuller#run()}.
	 */
	@Test
	public void testRun() {
		GroupSector master = new GroupSector( "master" );
		
		GroupSector slave1 = new GroupSector( "subject1" );
		GroupSector slave2 = new GroupSector( "subject2" );
		slave2.size.set( 0.0f, 1.0f, 1.0f );
		GroupSector slave3 = new GroupSector( "subject3" );
		GroupSector slave4 = new GroupSector( "subject4" );
		slave4.size.set( 1.0f, 0.0f, 1.0f);
		SceneNode node = new SceneNode( "untouchable" );
		
		master.addChild( slave1 );
		master.addChild( slave2 );
		master.addChild( slave3 );
		master.addChild( slave4 );
		master.addChild( node );
		
		World world = new World( master );
		WorldProcessor processor = new DegenerateSectorCuller( new DummyClient(), world );
		processor.run();
		
		Assert.assertEquals( 3, master.getSons().size() );
	}

	/**
	 * Test method for {@link br.odb.worldprocessing.DegenerateSectorCuller#toString()}.
	 */
	@Test
	public void testToString() {
		Assert.assertNotNull( new DegenerateSectorCuller( null, null ).toString() );
	}
}
