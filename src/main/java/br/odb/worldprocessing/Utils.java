package br.odb.worldprocessing;

import br.odb.libscene.GroupSector;
import br.odb.libscene.SceneNode;
import br.odb.libscene.World;

/**
 * 
 * @author monty
 */
public class Utils {
	
	public static void removeSectorFromSons( GroupSector gs, SceneNode son ) {
		for ( SceneNode sr : gs.getSons() ) {
			if ( sr == son ) {
				gs.getSons().remove( son );
				return;
			} else if ( sr instanceof GroupSector ) {
				removeSectorFromSons( (GroupSector) sr, son );
			}
		}
	}
	
	public static void removeSector(World world, SceneNode s) {
		removeSectorFromSons( world.masterSector, s );		
	}
}
