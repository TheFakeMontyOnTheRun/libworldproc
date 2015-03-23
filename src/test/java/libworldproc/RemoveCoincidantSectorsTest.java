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
import br.odb.worldprocessing.RemoveCoincidantSectors;
import br.odb.worldprocessing.WorldProcessor;

/**
 * @author monty
 *
 */
public class RemoveCoincidantSectorsTest {

	/**
	 * Test method for
	 * {@link br.odb.worldprocessing.RemoveCoincidantSectors#RemoveCoincidantSectors(br.odb.gameapp.ApplicationClient, br.odb.libscene.World)}
	 * .
	 */
	@Test
	public void testRemoveCoincidantSectors() {
		WorldProcessor processor;
		World world = null;
		ApplicationClient client = null;

		processor = new RemoveCoincidantSectors(client, world);

		Assert.assertNull(processor.client);
		Assert.assertNull(processor.world);

		client = new DummyClient();
		processor = new RemoveCoincidantSectors(client, world);

		Assert.assertNotNull(processor.client);
		Assert.assertNull(processor.world);

		world = new World();
		processor = new RemoveCoincidantSectors(client, world);

		Assert.assertNotNull(processor.client);
		Assert.assertNotNull(processor.world);
	}

	/**
	 * Test method for
	 * {@link br.odb.worldprocessing.RemoveCoincidantSectors#run()}.
	 */
	@Test
	public void testRun() {
		GroupSector master = new GroupSector( "master" );
		
		GroupSector slave1 = new GroupSector( "subject1" );
		GroupSector slave2 = new GroupSector( "subject2" );
		Sector sector1 = new Sector( "sector1" );
		Sector sector1_dup = new Sector( "sector2" );
		Sector sector2 = new Sector( "sector2" );
		Sector sector2_dup = new Sector( "sector1" );
		SceneNode node = new SceneNode( "untouchable" );
		
		master.addChild( slave1 );
		master.addChild( slave2 );
		master.addChild( node );
		
		slave1.addChild( sector1 );
		sector1.localPosition.set( 5, 5, 5 );
		sector1.size.set( 1.0f, 1.0f, 1.0f );
		
		sector1_dup.size.set( 2.0f, 2.0f, 2.0f );
		slave1.addChild( sector1_dup );

		
		
		slave2.addChild( sector2 );
		sector2.size.set( 2.0f, 2.0f, 2.0f );
		
		sector2_dup.localPosition.set( 5, 5, 5 );
		sector2_dup.size.set( 1.0f, 1.0f, 1.0f );
		slave2.addChild( sector2_dup );
		
		
		slave2.addChild( node );
		
		World world = new World( master );
		WorldProcessor processor = new RemoveCoincidantSectors( new DummyClient(), world );
		
		Assert.assertEquals( 2, slave1.getSons().size() );
		Assert.assertEquals( 3, slave2.getSons().size() );

		Assert.assertTrue( slave1.getSons().contains( sector1 ) );
		Assert.assertTrue( slave1.getSons().contains( sector1_dup ) );

		Assert.assertTrue( slave2.getSons().contains( sector2 ) );
		Assert.assertTrue( slave2.getSons().contains( sector2_dup ) );
		Assert.assertTrue( slave2.getSons().contains( node ) );

		Assert.assertEquals( 2, slave1.getSons().size() );
		Assert.assertEquals( 3, slave2.getSons().size() );

		processor.run();
		
		Assert.assertTrue( slave1.getSons().contains( sector1 ) );
		Assert.assertTrue( slave1.getSons().contains( sector1_dup ) );

		Assert.assertFalse( slave2.getSons().contains( sector2 ) );
		Assert.assertFalse( slave2.getSons().contains( sector2_dup ) );
		Assert.assertTrue( slave2.getSons().contains( node ) );

		
		Assert.assertEquals( 2, slave1.getSons().size() );
		Assert.assertEquals( 1, slave2.getSons().size() );
	}

	/**
	 * Test method for
	 * {@link br.odb.worldprocessing.RemoveCoincidantSectors#toString()}.
	 */
	@Test
	public void testToString() {
		Assert.assertNotNull(new RemoveCoincidantSectors(null, null).toString());
	}
}
