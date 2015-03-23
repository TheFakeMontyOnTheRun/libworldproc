/**
 * 
 */
package libworldproc;

import org.junit.Assert;
import org.junit.Test;

import br.odb.libscene.SpaceRegion;
import br.odb.utils.Direction;
import br.odb.worldprocessing.Hyperplane;

/**
 * @author monty
 *
 */
public class HyperPlaneTest {

	/**
	 * Test method for {@link br.odb.worldprocessing.Hyperplane#hashCode()}.
	 */
	@Test
	public void testHashCodeAndEqualsHyperplane() {
		
		SpaceRegion gs = new SpaceRegion( "test" );
		SpaceRegion gs2 = new SpaceRegion( "test2");
		
		gs.localPosition.set( 0.0f, 0.0f, 5.0f );
		gs2.localPosition.set( 0.0f, 0.0f, 5.0f );
		
		Hyperplane plane0 = new Hyperplane( Direction.N, gs );
		Hyperplane plane1 = new Hyperplane( Direction.N, gs );
		
		Assert.assertEquals( plane0.hashCode(), plane1.hashCode() );
		
		Assert.assertEquals( plane1, plane1 );
		Assert.assertEquals( plane0, plane1 );
		Assert.assertEquals( plane1, plane0 );
		
		plane0 = new Hyperplane( Direction.N, 5 );

		Assert.assertFalse( plane0.hashCode() == plane1.hashCode() );
		Assert.assertFalse( plane0.equals( plane1 ) );

		plane0 = new Hyperplane( Direction.N, gs2 );
		
		Assert.assertFalse( plane0.equals( plane1 ) );

		plane0 = new Hyperplane( Direction.N, 5 );
		plane1 = new Hyperplane( Direction.N, 5 );

		Assert.assertEquals( plane0.hashCode(), plane1.hashCode() );
		Assert.assertEquals( plane0, plane1 );
		Assert.assertEquals( plane1, plane0 );

		plane1 = new Hyperplane( Direction.N, gs2 );
		plane0 = new Hyperplane( Direction.N, gs2 );
		
		Assert.assertEquals( plane0, plane1 );
		Assert.assertEquals( plane1, plane0 );
		
		plane0 = new Hyperplane( Direction.FLOOR, gs2 ); 
		
		Assert.assertFalse( plane0.equals( plane1 ) );
		
		gs2.localPosition.set( 0.0f, 0.0f, 7.0f );
		
		plane0 = new Hyperplane( Direction.N, gs ); 
		
		Assert.assertFalse( plane0.equals( plane1 ) );
		
		Assert.assertFalse( plane0.equals( null ) );
		Assert.assertFalse( plane0.equals( "Not a hyperplane" ) );
	}
	
	
	@Test
	public void anotherTest() {

		SpaceRegion gs = new SpaceRegion( "test" );
		SpaceRegion gs2 = new SpaceRegion( "test2");
		
		gs.localPosition.set( 0.0f, 0.0f, 5.0f );
		gs2.localPosition.set( 0.0f, 0.0f, 5.2f );
		
		Hyperplane plane0 = new Hyperplane( Direction.N, gs );
		Hyperplane plane1 = new Hyperplane( Direction.N, gs );
		
		plane1.v.z += 0.2f;
		
		Assert.assertFalse( plane0.equals( plane1 ) );

	}

	/**
	 * Test method for {@link br.odb.worldprocessing.Hyperplane#toString()}.
	 */
	@Test
	public void testToString() {
		SpaceRegion gs = new SpaceRegion( "test" );
		gs.localPosition.set( 1.0f, 2.0f, 3.0f );
		Hyperplane plane0 = new Hyperplane( Direction.N, gs );
		Assert.assertTrue( plane0.toString().contains( "test" ) );
		Assert.assertFalse( plane0.toString().contains( "1" ) );
		Assert.assertFalse( plane0.toString().contains( "2" ) );
		Assert.assertTrue( plane0.toString().contains( "3" ) );
		Assert.assertTrue( plane0.toString().contains( "N" ) );
	}

	/**
	 * Test method for {@link br.odb.worldprocessing.Hyperplane#Hyperplane(br.odb.utils.Direction, float, br.odb.libscene.SpaceRegion)}.
	 */
	@Test
	public void testHyperplane() {
		SpaceRegion sr1;
		Hyperplane plane0;
		
		sr1 = new SpaceRegion( "sr1" );
		
		sr1.localPosition.set( 1, 2, 3 );
		sr1.size.set( 4, 5, 6 );
		
		plane0 = new Hyperplane( Direction.N, sr1 );
		Assert.assertEquals( 3.0f, plane0.v.z, 0.001f );
		
		plane0 = new Hyperplane( Direction.E, sr1 );
		Assert.assertEquals( 5.0f, plane0.v.x, 0.001f );
		
		plane0 = new Hyperplane( Direction.S, sr1 );
		Assert.assertEquals( 9.0f, plane0.v.z, 0.001f );
		
		plane0 = new Hyperplane( Direction.W, sr1 );
		Assert.assertEquals( 1.0f, plane0.v.x, 0.001f );
		
		plane0 = new Hyperplane( Direction.FLOOR, sr1 );
		Assert.assertEquals( 2.0f, plane0.v.y, 0.001f );
		
		plane0 = new Hyperplane( Direction.CEILING, sr1 );
		Assert.assertEquals( 7.0f, plane0.v.y, 0.001f );
		
		
		plane0 = new Hyperplane( Direction.N, 4 );
		Assert.assertEquals( 4.0f, plane0.v.z, 0.001f );
		
		plane0 = new Hyperplane( Direction.E, 5 );
		Assert.assertEquals( 5.0f, plane0.v.x, 0.001f );
		
		plane0 = new Hyperplane( Direction.S, 6 );
		Assert.assertEquals( 6.0f, plane0.v.z, 0.001f );
		
		plane0 = new Hyperplane( Direction.W, 7 );
		Assert.assertEquals( 7.0f, plane0.v.x, 0.001f );
		
		plane0 = new Hyperplane( Direction.FLOOR, 8 );
		Assert.assertEquals( 8.0f, plane0.v.y, 0.001f );
		
		plane0 = new Hyperplane( Direction.CEILING, 9 );
		Assert.assertEquals( 9.0f, plane0.v.y, 0.001f );
		
	}

	/**
	 * Test method for {@link br.odb.worldprocessing.Hyperplane#stabXY(br.odb.libscene.Sector)}.
	 */
	@Test
	public void testStabXY() {
		SpaceRegion sr1 = new SpaceRegion( "sr1" );
		SpaceRegion sr2 = new SpaceRegion( "sr2" );
		SpaceRegion sr3 = new SpaceRegion( "sr2" );
		SpaceRegion sr4 = new SpaceRegion( "sr2" );
		
		sr1.localPosition.set( 0, 0, 0 );
		sr1.size.set( 1, 1, 1 );
		
		sr3.localPosition.set( -10, -10, -10 );
		sr3.size.set( 1, 1, 1 );
		
		sr4.localPosition.set( 10, 10, 10 );
		sr4.size.set( 1, 1, 1 );


		sr2.localPosition.set( 0.5f, 0.5f, 0.5f );
		
		Hyperplane plane0 = new Hyperplane( Direction.S, sr1 );
		
		Assert.assertTrue( plane0.stabXY( sr2 ) );
				
		Assert.assertFalse( plane0.stabXY( sr3 ) );
		Assert.assertFalse( plane0.stabXY( sr4 ) );

		plane0 = new Hyperplane( Direction.FLOOR, sr1 );
		
		Assert.assertFalse( plane0.stabXY( sr1 ) );
		Assert.assertFalse( plane0.stabXY( sr2 ) );
				
		Assert.assertFalse( plane0.stabXY( sr3 ) );
		Assert.assertFalse( plane0.stabXY( sr4 ) );		
	}

	/**
	 * Test method for {@link br.odb.worldprocessing.Hyperplane#stabXZ(br.odb.libscene.Sector)}.
	 */
	@Test
	public void testStabXZ() {
		SpaceRegion sr1 = new SpaceRegion( "sr1" );
		SpaceRegion sr2 = new SpaceRegion( "sr2" );
		SpaceRegion sr3 = new SpaceRegion( "sr2" );
		SpaceRegion sr4 = new SpaceRegion( "sr2" );
		
		sr1.localPosition.set( 0, 0, 0 );
		sr1.size.set( 1, 1, 1 );
		
		sr3.localPosition.set( -10, -10, -10 );
		sr3.size.set( 1, 1, 1 );
		
		sr4.localPosition.set( 10, 10, 10 );
		sr4.size.set( 1, 1, 1 );


		sr2.localPosition.set( -0.5f, -0.5f, -0.5f );
		sr2.size.set( 2.0f, 2.0f, 2.0f );
		
		Hyperplane plane0 = new Hyperplane( Direction.FLOOR, sr1 );
		
		Assert.assertTrue( plane0.stabXZ( sr2 ) );
				
		Assert.assertFalse( plane0.stabXZ( sr3 ) );
		Assert.assertFalse( plane0.stabXZ( sr4 ) );

		plane0 = new Hyperplane( Direction.W, sr1 );
		
		Assert.assertFalse( plane0.stabXZ( sr1 ) );
		Assert.assertFalse( plane0.stabXZ( sr2 ) );
				
		Assert.assertFalse( plane0.stabXZ( sr3 ) );
		Assert.assertFalse( plane0.stabXZ( sr4 ) );
	}

	/**
	 * Test method for {@link br.odb.worldprocessing.Hyperplane#stabYZ(br.odb.libscene.Sector)}.
	 */
	@Test
	public void testStabYZ() {
		SpaceRegion sr1 = new SpaceRegion( "sr1" );
		SpaceRegion sr2 = new SpaceRegion( "sr2" );
		SpaceRegion sr3 = new SpaceRegion( "sr2" );
		SpaceRegion sr4 = new SpaceRegion( "sr2" );
		
		sr1.localPosition.set( 0, 0, 0 );
		sr1.size.set( 1, 1, 1 );
		
		sr3.localPosition.set( -10, -10, -10 );
		sr3.size.set( 1, 1, 1 );
		
		sr4.localPosition.set( 10, 10, 10 );
		sr4.size.set( 1, 1, 1 );


		sr2.localPosition.set( -0.5f, -0.5f, -0.5f );
		sr2.size.set( 2.0f, 2.0f, 2.0f );
		
		Hyperplane plane0 = new Hyperplane( Direction.W, sr1 );
		
		Assert.assertTrue( plane0.stabYZ( sr2 ) );
				
		Assert.assertFalse( plane0.stabYZ( sr3 ) );
		Assert.assertFalse( plane0.stabYZ( sr4 ) );

		plane0 = new Hyperplane( Direction.N, sr1 );
		
		Assert.assertFalse( plane0.stabYZ( sr1 ) );
		Assert.assertFalse( plane0.stabYZ( sr2 ) );
				
		Assert.assertFalse( plane0.stabYZ( sr3 ) );
		Assert.assertFalse( plane0.stabYZ( sr4 ) );

	}

}
