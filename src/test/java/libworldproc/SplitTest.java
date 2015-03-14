package libworldproc;


import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import br.odb.libscene.GroupSector;
import br.odb.libscene.Sector;
import br.odb.utils.Direction;
import br.odb.utils.math.Vec3;
import br.odb.worldprocessing.Hyperplane;
import br.odb.worldprocessing.WorldGlobalPartitioner;
import br.odb.worldprocessing.WorldLocalPartitioner;

public class SplitTest {

	@Test
	public final void testSplit() {

		GroupSector gs;
		Sector s1;
		Hyperplane plane;
		Sector s2;

		gs = new GroupSector( "gs" );
		s1 = new Sector( "test1" );
		plane = new Hyperplane( Direction.N, 5, gs );

		gs.localPosition.set( 0, 0, 2 );
		s1.localPosition.set( 0, 0, 2 );
		gs.addChild( s1 );
		s1.size.set( 10.0f, 10.0f, 10.0f );
		
		s2 = WorldGlobalPartitioner.split( s1, plane );
		
		Assert.assertEquals( new Vec3( 0, 0, 0 ), s1.localPosition );
		Assert.assertEquals( new Vec3( 10, 10, 3 ), s1.size );
		Assert.assertEquals( new Vec3( 0, 0, 5 ), s2.localPosition );
		Assert.assertEquals( new Vec3( 10, 10, 7 ), s2.size );
		
		gs.addChild( s2 );
		
		Assert.assertEquals( new Vec3( 0, 0, 3 ), s2.localPosition );
		Assert.assertEquals( new Vec3( 10, 10, 7 ), s2.size );
		
		
		gs = new GroupSector( "gs" );
		s1 = new Sector( "test1" );
		plane = new Hyperplane( Direction.W, 5, gs );

		gs.localPosition.set( 2, 0, 0 );
		s1.localPosition.set( 2, 0, 0 );
		gs.addChild( s1 );
		s1.size.set( 10.0f, 10.0f, 10.0f );
		
		s2 = WorldGlobalPartitioner.split( s1, plane );
		
		Assert.assertEquals( new Vec3( 0, 0, 0 ), s1.localPosition );
		Assert.assertEquals( new Vec3( 3, 10, 10 ), s1.size );
		Assert.assertEquals( new Vec3( 5, 0, 0 ), s2.localPosition );
		Assert.assertEquals( new Vec3( 7, 10, 10 ), s2.size );
		
		gs.addChild( s2 );
		
		Assert.assertEquals( new Vec3( 3, 0, 0 ), s2.localPosition );
		Assert.assertEquals( new Vec3( 7, 10, 10 ), s2.size );		
		
		
		gs = new GroupSector( "gs" );
		s1 = new Sector( "test1" );
		plane = new Hyperplane( Direction.FLOOR, 5, gs );

		gs.localPosition.set( 0, 2, 0 );
		s1.localPosition.set( 0, 2, 0 );
		gs.addChild( s1 );
		s1.size.set( 10.0f, 10.0f, 10.0f );
		
		s2 = WorldGlobalPartitioner.split( s1, plane );
		
		Assert.assertEquals( new Vec3( 0, 0, 0 ), s1.localPosition );
		Assert.assertEquals( new Vec3( 10, 3, 10 ), s1.size );
		Assert.assertEquals( new Vec3( 0, 5, 0 ), s2.localPosition );
		Assert.assertEquals( new Vec3( 10, 7, 10 ), s2.size );
		
		gs.addChild( s2 );
		
		Assert.assertEquals( new Vec3( 0, 3, 0 ), s2.localPosition );
		Assert.assertEquals( new Vec3( 10, 7, 10 ), s2.size );
	}
	
//	@Test
//	public final void testSplitTestIntersect() {
//		
//		WorldGlobalPartitioner local = new WorldGlobalPartitioner();
//		
//		GroupSector sr1 = new GroupSector( "test1" );
//		GroupSector sr2 = new GroupSector( "test2" );
//		
//		sr1.localPosition.set( 11.0f, 21.0f, -4.0f );
//		sr1.size.set( 12, 12, 12 );
//		
//		sr2.localPosition.set( 17.0f, 21.0f, 3.0f );
//		sr2.size.set( 12, 12, 12 );
//		
//		Sector s1 = new Sector( "s1" );
//		s1.localPosition.set( sr1.localPosition );
//		s1.size.set( sr1.size );
//
//		Sector s2 = new Sector( "s2" );
//		s2.localPosition.set( sr2.localPosition );
//		s2.size.set( sr2.size );
//		
//		sr1.addChild( s1 );
//		sr2.addChild( s2 );
//		
//		Set<Hyperplane> planes = local.getAllHyperplanesForSector(sr2);
//		
//		Assert.assertTrue( sr1.intersects( sr2 ) );
//		Assert.assertTrue( sr2.intersects( sr1 ) );
//		
//		local.splitSectorsWithPlanesFrom((GroupSector) sr1, planes);
//		
//		Sector s;
//				
//		s = new Sector( "son" );
//		
//// 11,-4
////      ___________	
////      |	|     |  SR1
////      | 1 |  2  |
////17, 3 |___|_____|_____ 29, 3
////      | 3 |  4  |  5 |
////11, 8 |___|_____|____| 23, 8
////		    |     |    | SR2
////		    |  6  |  7 |
////	17, 15  |_____|____| 29, 15
//		
//		Assert.assertEquals( 4, sr1.getSons().size() );
//		
//		//1
//		s.localPosition.set( 11.0f, 21.0f, -4.0f );
//		s.size.set( 6, 12, 7 );		
//		Assert.assertTrue( sr1.getSons().contains( s ) );
//		
//		//2
//		s.localPosition.set( -11.0f, 21.0f, -3.0f );
//		s.size.set( 6, 12, 7 );		
//		Assert.assertTrue( sr1.getSons().contains( s ) );
//		
//		//3
//		s.localPosition.set( -17.0f, 21.0f, 4.0f );
//		s.size.set( 6, 12, 5 );		
//		Assert.assertTrue( sr1.getSons().contains( s ) );
//		
//		//4
//		s.localPosition.set( -11.0f, 21.0f, 4.0f );
//		s.size.set( 6, 12, 5 );		
//		Assert.assertTrue( sr1.getSons().contains( s ) );
//		
//		
////		
////		s.localPosition.set( -17.0f, 21.0f, -3.0f );
////		s.size.set( 6, 12, 7 );		
////		Assert.assertTrue( sr1.getSons().contains( s ) );
////		
////		s.localPosition.set( -17.0f, 21.0f, -3.0f );
////		s.size.set( 6, 12, 7 );		
////		Assert.assertTrue( sr1.getSons().contains( s ) );
////		
////		s.localPosition.set( -17.0f, 21.0f, -3.0f );
////		s.size.set( 6, 12, 7 );		
////		Assert.assertTrue( sr1.getSons().contains( s ) );
////		
////		s.localPosition.set( -17.0f, 21.0f, -3.0f );
////		s.size.set( 6, 12, 7 );		
////		Assert.assertTrue( sr1.getSons().contains( s ) );
////		
//
//		
//		s.localPosition.set( -5.0f, 21.0f, 9.0f );
//		s.size.set( 6, 12, 7 );		
//		Assert.assertTrue( sr1.getSons().contains( s ) );
//		
//		
//		
//		
//		s.localPosition.set( -11.0f, 21.0f, -3.0f );
//		s.size.set( 12, 12, 7 );		
//		Assert.assertFalse( sr1.getSons().contains( s ) );
//	}
}
