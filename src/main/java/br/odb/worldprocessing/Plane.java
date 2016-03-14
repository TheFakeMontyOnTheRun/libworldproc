/**
 * 
 */
package br.odb.worldprocessing;

import br.odb.libscene.SpaceRegion;
import br.odb.gameutils.Direction;
import br.odb.gameutils.math.Vec3;

/**
 * @author monty
 * 
 */
public class Plane {

	/**
	 * 
	 */
	public final Direction kind;
	public final Vec3 v = new Vec3();
	public final SpaceRegion generator;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((generator == null) ? 0 : generator.hashCode());
		result = prime * result + ( kind.hashCode());
		result = prime * result + ( v.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		
		if ( !( obj instanceof Plane ) ) {
			return false;
		}
			
		Plane other = (Plane) obj;
		if (generator == null) {
			if (other.generator != null)
				return false;
		} else if (!generator.equals(other.generator))
			return false;
		if (kind != other.kind)
			return false;
		if (!v.equals(other.v))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "hyperplane: " + v + " kind: " + kind + " generator id: "
				+ generator.id;
	}

	public Plane(Direction kind, SpaceRegion generator) {

		this.kind = kind;
		this.v.set( Float.NaN, Float.NaN, Float.NaN );
		this.generator = generator;
		
		Vec3 pos = generator.getAbsolutePosition();
		
		switch (kind) {
		case N:
			v.z = pos.z;
			break;
		case S:
			v.z = pos.z + generator.size.z;
			break;
		case W:
			v.x = pos.x;
			break;
		case E:
			v.x = pos.x + generator.size.x;
			break;
		case FLOOR:
			v.y = pos.y;
			break;
		case CEILING:
			v.y = pos.y + generator.size.y;
			break;
		}
	}
	
	public Plane(Direction kind, float n ) {

		this.kind = kind;
		this.v.set( Float.NaN, Float.NaN, Float.NaN );
		this.generator = null;

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

	public boolean stabXY(SpaceRegion sector) {

		Vec3 position = sector.getAbsolutePosition();
		
		if (!Float.isNaN( v.z)) {
			return position.z < v.z && v.z < ( position.z + sector.size.z );
		}
		return false;
	}
	
	public boolean stabXZ(SpaceRegion sector) {

		Vec3 position = sector.getAbsolutePosition();
		
		if (!Float.isNaN( v.y)) {
			return position.y < v.y && v.y < (position.y + sector.size.y);
		} 	

		return false;
	}


	public boolean stabYZ(SpaceRegion sector) {

		Vec3 position = sector.getAbsolutePosition();
		
		if (!Float.isNaN( v.x ) ) {
			return position.x < v.x	&& v.x < (position.x + sector.size.x );
		}		

		return false;
	}
}