/**
 * 
 */
package libworldproc;

import org.junit.Assert;
import org.junit.Test;

import br.odb.gameapp.ApplicationClient;

import br.odb.libscene.GroupSector;
import br.odb.libscene.SceneNode;
import br.odb.libscene.World;
import br.odb.worldprocessing.DegenerateSectorCuller;
import br.odb.worldprocessing.WorldProcessor;

import static org.mockito.Mockito.mock;

/**
 * @author monty
 *
 */
public class DegenerateSectorCullerTest {

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
		ApplicationClient client = mock( br.odb.gameapp.ApplicationClient.class );
		WorldProcessor processor = new DegenerateSectorCuller( client, world );
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
