package libworldproc;


import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import br.odb.gameapp.ApplicationClient;
import br.odb.gameapp.DummyClient;
import br.odb.libscene.GroupSector;
import br.odb.libscene.SceneNode;
import br.odb.libscene.Sector;
import br.odb.libscene.SpaceRegion;
import br.odb.libscene.World;
import br.odb.gameutils.Direction;
import br.odb.gameutils.math.Vec3;
import br.odb.worldprocessing.Plane;
import br.odb.worldprocessing.WorldLocalPartitioner;

public class WorldLocalPartitionerTest {

	@Test
	public final void testRun() {
		GroupSector sr1 = new GroupSector( "test1" );
		GroupSector sr2 = new GroupSector( "test2" );
		
		sr1.localPosition.set( 11.0f, 21.0f, -4.0f );
		sr1.size.set( 12, 12, 12 );
		
		sr2.localPosition.set( 17.0f, 21.0f, 3.0f );
		sr2.size.set( 12, 12, 12 );
		
		Sector s1 = new Sector( "s1" );
		s1.localPosition.set( sr1.localPosition );
		s1.size.set( sr1.size );

		Sector s2 = new Sector( "s2" );
		s2.localPosition.set( sr2.localPosition );
		s2.size.set( sr2.size );
		
		sr1.addChild( s1 );
		sr2.addChild( s2 );
		
		Assert.assertTrue( sr1.intersects( sr2 ) );
		Assert.assertTrue( sr2.intersects( sr1 ) );

		GroupSector master = new GroupSector( "master" );
		
		master.addChild( sr1 );
		master.addChild( sr2 );
		World world = new World( master );
		
		
		WorldLocalPartitioner partitioner = new WorldLocalPartitioner( new DummyClient(), world );
		partitioner.run();
		
//		Assert.assertEquals( 10, world.getAllRegionsAsList().size() );
				

	}
	
	@Test
	public final void testConstructor() {
		WorldLocalPartitioner partitioner;
		World world = null;
		ApplicationClient client = null;
		
		partitioner = new WorldLocalPartitioner( client, world );
		
		Assert.assertNull( partitioner.client );
		Assert.assertNull( partitioner.world );
		
		client = new DummyClient();
		partitioner = new WorldLocalPartitioner( client, world );
		
		Assert.assertNotNull( partitioner.client );
		Assert.assertNull( partitioner.world );
		
		
		world = new World();
		partitioner = new WorldLocalPartitioner( client, world );
		
		Assert.assertNotNull( partitioner.client );
		Assert.assertNotNull( partitioner.world );
	}
	
	
	@Test
	public final void testSplit() {

		GroupSector gs;
		Sector s1;
		Plane plane;
		Sector s2;

		gs = new GroupSector( "gs" );
		s1 = new Sector( "test1" );
		gs.localPosition.set( 0.0f, 0.0f, 5.0f );
		
		plane = new Plane( Direction.N, gs );

		gs.localPosition.set( 0, 0, 2 );
		s1.localPosition.set( 0, 0, 2 );
		gs.addChild( s1 );
		s1.size.set( 10.0f, 10.0f, 10.0f );
		
		s2 = WorldLocalPartitioner.split( s1, plane );
		
		Assert.assertEquals( new Vec3( 0, 0, 0 ), s1.localPosition );
		Assert.assertEquals( new Vec3( 10, 10, 3 ), s1.size );
		Assert.assertEquals( new Vec3( 0, 0, 5 ), s2.localPosition );
		Assert.assertEquals( new Vec3( 10, 10, 7 ), s2.size );
		
		gs.addChild( s2 );
		
		Assert.assertEquals( new Vec3( 0, 0, 3 ), s2.localPosition );
		Assert.assertEquals( new Vec3( 10, 10, 7 ), s2.size );

		
		
		
		
		gs = new GroupSector( "gs" );
		s1 = new Sector( "test1" );
		gs.localPosition.set( 11.0f, 21.0f, -4.0f );
		plane = new Plane( Direction.N, 3 );

		gs.localPosition.set( 11, 21, -4 );
		gs.size.set( 12.0f, 12.0f, 12.0f );
		gs.addChild( s1 );
		s1.localPosition.set( 0, 0, 0 );
		s1.size.set( 12.0f, 12.0f, 12.0f );
		
		s2 = WorldLocalPartitioner.split( s1, plane );
		
		Assert.assertEquals( new Vec3( 0, 0, 0 ), s1.localPosition );
		Assert.assertEquals( new Vec3( 12, 12, 7 ), s1.size );
		Assert.assertEquals( new Vec3( 11, 21, 3 ), s2.localPosition );
		Assert.assertEquals( new Vec3( 12, 12, 5 ), s2.size );
		
		
		
		gs = new GroupSector( "gs" );
		s1 = new Sector( "test1" );
		gs.localPosition.set( 5.0f, 0.0f, 0.0f );
		plane = new Plane( Direction.W, gs );

		gs.localPosition.set( 2, 0, 0 );
		s1.localPosition.set( 2, 0, 0 );
		gs.addChild( s1 );
		s1.size.set( 10.0f, 10.0f, 10.0f );
		
		s2 = WorldLocalPartitioner.split( s1, plane );
		
		Assert.assertEquals( new Vec3( 0, 0, 0 ), s1.localPosition );
		Assert.assertEquals( new Vec3( 3, 10, 10 ), s1.size );
		Assert.assertEquals( new Vec3( 5, 0, 0 ), s2.localPosition );
		Assert.assertEquals( new Vec3( 7, 10, 10 ), s2.size );
		
		gs.addChild( s2 );
		
		Assert.assertEquals( new Vec3( 3, 0, 0 ), s2.localPosition );
		Assert.assertEquals( new Vec3( 7, 10, 10 ), s2.size );		
		
		
		gs = new GroupSector( "gs" );
		s1 = new Sector( "test1" );
		gs.localPosition.set( 0.0f, 5.0f, 0.0f );
		plane = new Plane( Direction.FLOOR, gs );

		gs.localPosition.set( 0, 2, 0 );
		s1.localPosition.set( 0, 2, 0 );
		gs.addChild( s1 );
		s1.size.set( 10.0f, 10.0f, 10.0f );
		
		s2 = WorldLocalPartitioner.split( s1, plane );
		
		Assert.assertEquals( new Vec3( 0, 0, 0 ), s1.localPosition );
		Assert.assertEquals( new Vec3( 10, 3, 10 ), s1.size );
		Assert.assertEquals( new Vec3( 0, 5, 0 ), s2.localPosition );
		Assert.assertEquals( new Vec3( 10, 7, 10 ), s2.size );
		
		gs.addChild( s2 );
		
		Assert.assertEquals( new Vec3( 0, 3, 0 ), s2.localPosition );
		Assert.assertEquals( new Vec3( 10, 7, 10 ), s2.size );
	
		GroupSector sr1 = new GroupSector( "test1" );
		GroupSector sr2 = new GroupSector( "test2" );
		
		sr1.localPosition.set( 11.0f, 21.0f, -4.0f );
		sr1.size.set( 12, 12, 12 );
		
		sr2.localPosition.set( 17.0f, 21.0f, 3.0f );
		sr2.size.set( 12, 12, 12 );
		
		s1 = new Sector( "s1" );
		s1.localPosition.set( sr1.localPosition );
		s1.size.set( sr1.size );

		s2 = new Sector( "s2" );
		s2.localPosition.set( sr2.localPosition );
		s2.size.set( sr2.size );
		
		sr1.addChild( s1 );
		sr2.addChild( s2 );

		plane = new Plane( Direction.N, sr2 );
		
		Sector newSector = WorldLocalPartitioner.split( s1, plane );

		Assert.assertEquals( new Vec3( 11.0f, 21.0f, -4.0f ), s1.getAbsolutePosition() );
		Assert.assertEquals( new Vec3( 12.0f, 12.0f, 7.0f ), s1.size );

		
		Assert.assertEquals( new Vec3( 11.0f, 21.0f, 3.0f ), newSector.getAbsolutePosition() );
		Assert.assertEquals( new Vec3( 12.0f, 12.0f, 5.0f ), newSector.size );
	}
	
	@Test
	public void testGetAllHyperplanesFromSpaceRegion() {
		SpaceRegion sr = new SpaceRegion( "test" );
		sr.localPosition.set( 1, 2, 3 );
		sr.size.set( 4, 5, 6 );
		Set< Plane > planes = WorldLocalPartitioner.getAllHyperplanesForSector( sr );

		for ( Direction d : Direction.values() ) {
			Assert.assertTrue( planes.contains(
					new Plane( d, sr )));

		}		
	}
	

	
	@Test
	public final void testSplitTestIntersect() {
		
		GroupSector sr1 = new GroupSector( "test1" );
		GroupSector sr2 = new GroupSector( "test2" );
		
		sr1.localPosition.set( 11.0f, 21.0f, -4.0f );
		sr1.size.set( 12, 12, 12 );
		
		sr2.localPosition.set( 17.0f, 21.0f, 3.0f );
		sr2.size.set( 12, 12, 12 );
		
		Sector s1 = new Sector( "s1" );
		s1.localPosition.set( sr1.localPosition );
		s1.size.set( sr1.size );

		Sector s2 = new Sector( "s2" );
		s2.localPosition.set( sr2.localPosition );
		s2.size.set( sr2.size );
		
		sr1.addChild( s1 );
		sr2.addChild( s2 );
		
		Set<Plane> planes = WorldLocalPartitioner.getAllHyperplanesForSector(sr2);
		
		Assert.assertTrue( sr1.intersects( sr2 ) );
		Assert.assertTrue( sr2.intersects( sr1 ) );
		
		WorldLocalPartitioner.splitSectorsWithPlanesFrom((GroupSector) sr1, planes);
		
		Sector s;
				
		s = new Sector( "son" );
		
// 11,-4
//      ___________	
//      |	|     |  SR1
//      | 1 |  2  |
//17, 3 |___|_____|_____ 29, 3
//      | 3 |  4  |  5 |
//11, 8 |___|_____|____| 23, 8
//		    |     |    | SR2
//		    |  6  |  7 |
//	17, 15  |_____|____| 29, 15
		
		Assert.assertEquals( 4, sr1.getSons().size() );
		
		boolean found;
		
		found = false;
		//1
		s.localPosition.set( 0.0f, 0.0f, 0.0f );
		s.size.set( 6, 12, 7 );
		
		for ( SceneNode sn : sr1.getSons() ) {
			if ( s.localPosition.equals( sn.localPosition) && (( Sector ) sn ).size.equals( s.size ) ) {
				found = true;
			}
		}
		Assert.assertTrue( found );
		
		//2
		s.localPosition.set( 6.0f, 0.0f, 0.0f );
		s.size.set( 6, 12, 7 );
		found = false;
		for ( SceneNode sn : sr1.getSons() ) {
			if ( s.localPosition.equals( sn.localPosition) && (( Sector ) sn ).size.equals( s.size ) ) {
				found = true;
			}
		}
		Assert.assertTrue( found );

		
		//3
		s.localPosition.set( 0.0f, 0.0f, 7.0f );
		s.size.set( 6, 12, 5 );		
		found = false;
		for ( SceneNode sn : sr1.getSons() ) {
			if ( s.localPosition.equals( sn.localPosition) && (( Sector ) sn ).size.equals( s.size ) ) {
				found = true;
			}
		}
		Assert.assertTrue( found );

		
		//4
		s.localPosition.set( 6.0f, 0.0f, 7.0f );
		s.size.set( 6, 12, 5 );		
		found = false;
		for ( SceneNode sn : sr1.getSons() ) {
			if ( s.localPosition.equals( sn.localPosition) && (( Sector ) sn ).size.equals( s.size ) ) {
				found = true;
			}
		}
		Assert.assertTrue( found );
	}

	
	@Test
	public void testGenerateLeafsForGroupsInWorld() {
		World world;
		GroupSector master;
		GroupSector gs1;
		GroupSector gs2;
		
		master = new GroupSector( "master" );
		master.localPosition.set( 1.0f, 1.0f, 1.0f );
		master.size.set( 255.0f, 255.0f, 255.0f );
		
		gs1 = new GroupSector( "gs1" );
		gs1.localPosition.set( 20.0f, 20.0f, 20.0f );
		gs1.size.set( 5.0f, 5.0f, 5.0f );
		
		gs2 = new GroupSector( "gs2" );
		
		master.addChild( gs1 );
		master.addChild( gs2 );
		world = new World( master );

		Assert.assertEquals( 0, gs1.getSons().size() );
		Assert.assertEquals( 0, gs2.getSons().size() );
		
		WorldLocalPartitioner.generateLeafsForWorld( world );
		
		Assert.assertEquals( 1, gs1.getSons().size() );
		Assert.assertEquals( 1, gs2.getSons().size() );
		
		Assert.assertEquals( new Vec3(), gs1.sons.get( 0 ).localPosition );
		Assert.assertEquals( gs1.size, ( (Sector)gs1.sons.get( 0 ) ).size );

		Assert.assertEquals( new Vec3(), gs2.sons.get( 0 ).localPosition );
		Assert.assertEquals( gs2.size, ( (Sector)gs2.sons.get( 0 ) ).size );
	}	
	
	@Test
	public void testHyperplanesFromGroupsInWorld() {
		World world;
		GroupSector master;
		GroupSector gs1;
		GroupSector gs2;
		Sector s1;
		
		master = new GroupSector( "master" );
		master.localPosition.set( 1.0f, 1.0f, 1.0f );
		master.size.set( 255.0f, 255.0f, 255.0f );
		
		gs1 = new GroupSector( "gs1" );
		gs1.localPosition.set( 20.0f, 20.0f, 20.0f );
		gs1.size.set( 5.0f, 5.0f, 5.0f );
		
		gs2 = new GroupSector( "gs2" );
		
		s1 = new Sector( gs1, "s1" );
		
		gs1.addChild( s1 );
		master.addChild( gs1 );
		master.addChild( gs2 );
		world = new World( master );
		
		Set< Plane > planes = WorldLocalPartitioner.generateHyperplanesForGroupSectorsInWorld(world);
		//only from gs1 and gs2
		Assert.assertEquals( 12, planes.size() );
		
		for ( Direction d : Direction.values() ) {
			Assert.assertTrue( planes.contains( new Plane( d, gs1 ) ) );
		}

		for ( Direction d : Direction.values() ) {
			Assert.assertTrue( planes.contains( new Plane( d, gs2 ) ) );
		}
	}
	
	@Test
	public void testToString() {
		WorldLocalPartitioner snapper = new WorldLocalPartitioner( null, null );
		Assert.assertNotNull( snapper.toString() );
	}
}
