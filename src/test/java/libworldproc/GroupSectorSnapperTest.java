///**
// *
// */
//package libworldproc;
//
//import org.junit.Assert;
//import org.junit.Test;
//
//import br.odb.gameapp.ApplicationClient;
//
//import br.odb.libscene.GroupSector;
//import br.odb.libscene.SceneNode;
//import br.odb.libscene.World;
//import br.odb.gameutils.math.Vec3;
//import br.odb.worldprocessing.GroupSectorSnapper;
//
//import static org.mockito.Mockito.mock;
//
///**
// * @author monty
// *
// */
//public class GroupSectorSnapperTest {
//
//	@Test
//	public void testSectorSnapper() {
//		GroupSectorSnapper snapper;
//		World world = null;
//		ApplicationClient client = mock( br.odb.gameapp.ApplicationClient.class );
//
//		snapper = new GroupSectorSnapper( client, world );
//
//		Assert.assertNull( snapper.client );
//		Assert.assertNull( snapper.world );
//
//		snapper = new GroupSectorSnapper( client, world );
//
//		Assert.assertNotNull( snapper.client );
//		Assert.assertNull( snapper.world );
//
//
//		world = new World();
//		snapper = new GroupSectorSnapper( client, world );
//
//		Assert.assertNotNull( snapper.client );
//		Assert.assertNotNull( snapper.world );
//	}
//
//	/**
//	 * Test method for {@link br.odb.worldprocessing.GroupSectorSnapper#run()}.
//	 */
//	@Test
//	public void testRun() {
//		GroupSector master = new GroupSector( "master" );
//		GroupSector slave = new GroupSector( "subject" );
//		SceneNode node = new SceneNode( "non-snap" );
//		slave.localPosition.set( 0.3141592f, 0.51f, 0.49f );
//
//		master.size.set( 0.9f, 2.1f, 1.45f );
//		slave.size.set( 0.85f, 1.15f, 1.00001f );
//
//		master.addChild( slave );
//		master.addChild( node );
//
//		World world = new World( master );
//		ApplicationClient client = mock( br.odb.gameapp.ApplicationClient.class );
//		GroupSectorSnapper snapper = new GroupSectorSnapper( client, world );
//		snapper.run();
//
//		Assert.assertEquals( new Vec3( 0.3f, 0.5f, 0.5f ), slave.localPosition );
//		Assert.assertEquals( new Vec3( 0.9f, 1.2f, 1.0f ), slave.size );
//	}
//
//	/**
//	 * Test method for {@link br.odb.worldprocessing.GroupSectorSnapper#toString()}.
//	 */
//	@Test
//	public void testToString() {
//		GroupSectorSnapper snapper = new GroupSectorSnapper( null, null );
//		Assert.assertNotNull( snapper.toString() );
//	}
//}
