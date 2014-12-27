//package libworldproc;
//
//
//import java.util.Set;
//
//import org.junit.Assert;
//import org.junit.Test;
//
//import br.odb.libscene.GroupSector;
//import br.odb.libscene.Sector;
//import br.odb.worldprocessing.Hyperplane;
//import br.odb.worldprocessing.WorldLocalPartitioner;
//
//public class SplitTest {
//
//	@Test
//	public final void SplitTestIntersect() {
//		WorldLocalPartitioner local = new WorldLocalPartitioner();
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
//		
//		sr1.addChild( new Sector( sr1 ) );
//		sr2.addChild( new Sector( sr2 ) );
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
//}
