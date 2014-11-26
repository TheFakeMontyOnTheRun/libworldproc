/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.odb.worldprocessing;

import java.util.ArrayList;
import java.util.List;

import br.odb.libscene.GroupSector;
import br.odb.libscene.SpaceRegion;
import br.odb.libscene.World;

/**
 * 
 * @author monty
 */
class Utils {
	enum VerbosityLevels { 
		LEVEL_NORMAL,
		LEVEL_VERBOSE
	}

	static VerbosityLevels level = VerbosityLevels.LEVEL_NORMAL;

	static VerbosityLevels getLevel() {
		return level;
	}
	
	static void removeSectorFromSons( GroupSector gs, SpaceRegion son ) {
		for ( SpaceRegion sr : gs.getSons() ) {
			if ( sr == son ) {
				gs.getSons().remove( son );
				return;
			} else if ( sr instanceof GroupSector ) {
				removeSectorFromSons( (GroupSector) sr, son );
			}
		}
	}
	
	static void removeSector(World world, SpaceRegion s) {
		removeSectorFromSons( world.masterSector, s );		
	}

	static List< SpaceRegion > getAllRegionsAsList( GroupSector group ) {
		
		ArrayList< SpaceRegion > toReturn = new ArrayList< SpaceRegion >();
		
		toReturn.addAll( group.getSons() );
		
		for ( SpaceRegion sr : group.getSons() ) {
			if ( sr instanceof GroupSector ) {
				toReturn.addAll( getAllRegionsAsList( ( GroupSector )sr ) );
			}
		}
		
		
		
		return toReturn;
	}
}
