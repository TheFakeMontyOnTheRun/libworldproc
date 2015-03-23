///**
// * 
// */
//package libworldproc;
//
//import org.junit.Assert;
//import org.junit.Test;
//
//import br.odb.gameapp.ApplicationClient;
//import br.odb.gameapp.DummyClient;
//import br.odb.libscene.GroupSector;
//import br.odb.libscene.SceneNode;
//import br.odb.libscene.Sector;
//import br.odb.libscene.World;
//import br.odb.worldprocessing.RemoveLeafSectors;
//import br.odb.worldprocessing.WorldProcessor;
//
///**
// * @author monty
// *
// */
//public abstract class GenericWorldProcessorTest < T extends WorldProcessor > {
//
//	/**
//	 * Test method for {@link br.odb.worldprocessing.RemoveLeafSectors#RemoveLeafSectors(br.odb.gameapp.ApplicationClient, br.odb.libscene.World)}.
//	 */
//	@Test
//	public  void testRemoveLeafSectors() {
//		WorldProcessor processor;
//		World world = null;
//		ApplicationClient client = null;
//		
//		processor = new T( client, world );
//		
//		Assert.assertNull( processor.client );
//		Assert.assertNull( processor.world );
//		
//		client = new DummyClient();
//		processor = new T( client, world );
//		
//		Assert.assertNotNull( processor.client );
//		Assert.assertNull( processor.world );
//		
//		
//		world = new World();
//		processor = new T( client, world );
//		
//		Assert.assertNotNull( processor.client );
//		Assert.assertNotNull( processor.world );
//	}
//
//	/**
//	 * Test method for {@link br.odb.worldprocessing.RemoveLeafSectors#toString()}.
//	 */
//	@Test
//	public void testToString() {
//		Assert.assertNotNull( new T( null, null ).toString() );
//	}
//}
