/**
 * 
 */
package br.odb.worldprocessing;

import br.odb.libscene.Sector;
import br.odb.libscene.SpaceRegion;
import br.odb.utils.Direction;
import br.odb.utils.math.Vec3;

/**
 * @author monty
 * 
 */
public class Hyperplane {

	/**
	 * 
	 */
	public final Direction kind;
	public final Vec3 v;
	public final SpaceRegion generator;

	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}
	

	public boolean equals(Hyperplane another) {

		if (another == null)
			return false;

		if (!v.equals(another.v))
			return false;

		if (kind != another.kind)
			return false;

		// the generator sector doesnt matter.

		return true;
	}

	@Override
	public boolean equals(Object obj) {

		return equals((Hyperplane) obj);
	}

	@Override
	public String toString() {
		return "hyperplane: " + v + " kind: " + kind + " generator id: "
				+ generator.id;
	}

	public Hyperplane(Direction kind, float n, SpaceRegion generator) {

		this.kind = kind;
		this.v = new Vec3( Float.NaN, Float.NaN, Float.NaN );
		this.generator = generator;

		switch (kind) {
		case N:
		case S:
			v.z = (n);
			break;
		case W:
		case E:
			v.x = (n);
			break;
		case FLOOR:
		case CEILING:
			v.y = (n);
			break;
		}

	}

	public boolean stabXY(Sector sector) {

		Vec3 position = sector.getAbsolutePosition();
		
		if (!Float.isNaN( v.z)) {
			return position.z < v.z && v.z < ( position.z + sector.size.z );
		}
		return false;
	}
	
	public boolean stabXZ(Sector sector) {

		Vec3 position = sector.getAbsolutePosition();
		
		if (!Float.isNaN( v.y)) {
			return position.y < v.y && v.y < (position.y + sector.size.y);
		} 	

		return false;
	}


	public boolean stabYZ(Sector sector) {

		Vec3 position = sector.getAbsolutePosition();
		
		if (!Float.isNaN( v.x ) ) {
			return position.x < v.x	&& v.x < (position.x + sector.size.x );
		}		

		return false;
	}
}