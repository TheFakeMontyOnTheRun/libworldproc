package br.odb.worldprocessing;

import java.util.HashSet;
import java.util.Set;

import br.odb.gameapp.ApplicationClient;
import br.odb.libscene.GroupSector;
import br.odb.libscene.SceneNode;
import br.odb.libscene.Sector;
import br.odb.libscene.SpaceRegion;
import br.odb.libscene.World;
import br.odb.gameutils.Direction;
import br.odb.gameutils.math.Vec3;

public abstract class WorldPartitioner extends WorldProcessor {

	public WorldPartitioner(ApplicationClient client, World worldToProcess) {
		super(client, worldToProcess);

	}

	static long counter = 0;

	public static Sector split(final Sector sector, final Plane hyperplane) {

		Sector toReturn;
		toReturn = null;
		Vec3 position = sector.getAbsolutePosition();
		
		if (!Float.isNaN(hyperplane.v.x)) {
			// plane in YZ

			if (position.x < hyperplane.v.x
					&& hyperplane.v.x < (position.x + sector.size.x)) {
				
				if ( Math.abs( position.x - hyperplane.v.x ) < 0.001f ||
						Math.abs( ( position.x + sector.size.x) - hyperplane.v.x ) < 0.001f
						) {
					return null;
				}
				
				toReturn = new Sector( sector.id );
				
				toReturn.localPosition.set( sector.getAbsolutePosition() );
				toReturn.size.set( sector.size );
				
				toReturn.localPosition.x = (hyperplane.v.x);
				toReturn.size.x = sector.size.x - ( hyperplane.v.x - position.x);

				sector.size.x = (hyperplane.v.x) - position.x;
			}

		} else if (!Float.isNaN(hyperplane.v.y)) {
			// plane in XZ
			if (position.y < hyperplane.v.y
					&& hyperplane.v.y < (position.y + sector.size.y)) {
				
				if ( Math.abs( position.y - hyperplane.v.y ) < 0.001f ||
						Math.abs( ( position.y + sector.size.y) - hyperplane.v.y ) < 0.001f
						) {
					return null;
				}
				
				toReturn = new Sector( sector.id );
				
				toReturn.localPosition.set( sector.getAbsolutePosition() );
				toReturn.size.set( sector.size );

				toReturn.size.y = sector.size.y - (hyperplane.v.y - position.y );
				toReturn.localPosition.y = (hyperplane.v.y);
				
				sector.size.y = (hyperplane.v.y) - position.y;
			}

		} else if (!Float.isNaN(hyperplane.v.z)) {
			
			if ( Math.abs( position.z - hyperplane.v.z ) < 0.001f ||
					Math.abs( ( position.z + sector.size.z) - hyperplane.v.z ) < 0.001f
					) {
				return null;
			}
			
			// plane in XY
			if (position.z < hyperplane.v.z
					&& hyperplane.v.z < (position.z + sector.size.z)) {

				toReturn = new Sector( sector.id );
				
				toReturn.localPosition.set( sector.getAbsolutePosition() );
				toReturn.size.set( sector.size );

				toReturn.size.z = sector.size.z - (hyperplane.v.z - position.z);
				toReturn.localPosition.z = (hyperplane.v.z);
				
				sector.size.z = (hyperplane.v.z) - position.z;
			}
		}
		
		if ( toReturn != null ) {
			
			String id = "_";
			
			if ( sector.parent != null ) {
				id = sector.parent.id;
			}
			toReturn.id = id + "_" + ( ++counter );
		}

		return toReturn;
	}

	public static void generateLeafsForWorld(World world) {
		Sector sector;
		
		for (SceneNode sr : world.getAllRegionsAsList()) {
			if (sr instanceof GroupSector) {
				sector = new Sector( sr.id );
				sector.localPosition.set( sr.getAbsolutePosition() );
				sector.size.set( ((GroupSector) sr).size );
				((GroupSector) sr).addChild( sector );
			}
		}
	}

	public static Set<Plane> getAllHyperplanesForSector(SpaceRegion sr) {
		Set<Plane> planes = new HashSet<Plane>();

		for (Direction d : Direction.values()) {
			planes.add( new Plane( d, sr ) );
		}

		return planes;
	}

	public static int splitSectorsWithPlanesFrom(GroupSector current,
			Set<Plane> planes) {

		Sector generated;
		Set<Sector> toAdd = new HashSet<Sector>();
		Set<Sector> toRemove = new HashSet<Sector>();
		int changedNodes = 0;
		
		do {
			changedNodes = 0;
		
			for (Plane plane : planes) {
				
				toAdd.clear();
				
				for (SceneNode sr : current.getSons()) {
					if (sr instanceof Sector) {
						generated = split((Sector) sr, plane);
	
						if (generated != null && !generated.isDegenerate()) {
							toAdd.add(generated);
						}
					}
				}
				
				changedNodes = toAdd.size();
				
				for (SceneNode sr : toAdd ) {
					current.addChild( sr );
				}
			}
		} while( changedNodes != 0 );

		
		for (SceneNode sr : current.getSons()) {
			if (sr instanceof Sector ) {
				if ( ( ( Sector ) sr ).isDegenerate() ) {
					toRemove.add( ( ( Sector ) sr ) );
				}
			}
		}

		current.getSons().removeAll( toRemove );

		return current.getSons().size();
	}
	
	public static Set<Plane> generateHyperplanesForGroupSectorsInWorld(World world) {
		
		final Set<Plane> toReturn = new HashSet< Plane >();
		
		for (SceneNode sr : world.getAllRegionsAsList()) {
			if (sr instanceof GroupSector) {
				toReturn.addAll(getAllHyperplanesForSector((GroupSector)sr));
			}
		}
		
		return toReturn;
	}
	
	int getTotalSectors( World world ) {
		
		int total = 0;
		
		for (SceneNode sr : world.getAllRegionsAsList()) {

			if (sr instanceof Sector) {
				++total;
			}
		}

		return total;
	}
}
