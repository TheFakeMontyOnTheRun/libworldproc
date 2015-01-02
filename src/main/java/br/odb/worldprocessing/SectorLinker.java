/**
 * 
 */
package br.odb.worldprocessing;

import java.util.List;

import br.odb.gameapp.ApplicationClient;
import br.odb.libscene.GroupSector;
import br.odb.libscene.Sector;
import br.odb.libscene.SpaceRegion;
import br.odb.libscene.World;
import br.odb.utils.Direction;
import br.odb.utils.math.Vec3;

/**
 * @author monty
 * 
 */
public class SectorLinker implements WorldProcessor {

	private World world;
	private ApplicationClient client;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

		List<SpaceRegion> sectors = Utils
				.getAllRegionsAsList(world.masterSector);

		for (SpaceRegion s1 : sectors) {

			if (s1 instanceof GroupSector) {
				continue;
			}

			client.printVerbose( "checking links for " + s1.id  );
			
			for (SpaceRegion s2 : sectors) {

				if (s2 == s1)
					continue;

				if (s2 instanceof GroupSector) {
					continue;
				}

				// if (s1.parent == s2.parent) {

				checkLinksForSectors((Sector) s1, (Sector) s2);
				// }
			}
		}
	}

	/**
	 * @param s1
	 * @param s2
	 */
	private void checkLinksForSectors(Sector s1, Sector s2) {

		Vec3 pos2 = s2.getAbsolutePosition();

		int s2_x0 = Math.round( pos2.x );
		int s2_x1 = Math.round(pos2.x + s2.size.x);
		int s2_y0 = Math.round( pos2.y );
		int s2_y1 = Math.round(pos2.y + s2.size.y);
		int s2_z0 = Math.round( pos2.z );
		int s2_z1 = Math.round(pos2.z + s2.size.z);

		Vec3 pos1 = s1.getAbsolutePosition();

		int s1_x0 = Math.round( pos1.x );
		int s1_x1 = Math.round(pos1.x + s1.size.x);
		int s1_y0 = Math.round( pos1.y );
		int s1_y1 = Math.round(pos1.y + s1.size.y);
		int s1_z0 = Math.round( pos1.z );
		int s1_z1 = Math.round(pos1.z + s1.size.z);

		if (br.odb.utils.Utils.eqFloat(s1_x0, s2_x1)
				&& br.odb.utils.Utils.eqFloat(s1_z0, s2_z0)
				&& br.odb.utils.Utils.eqFloat(s1_z1, s2_z1)
				&& br.odb.utils.Utils.eqFloat(s1_y0, s2_y0)
				&& br.odb.utils.Utils.eqFloat(s1_y1, s2_y1)) {

			s1.connection.put(Direction.W, s2);
			s2.connection.put(Direction.E, s1);

			if (client != null) {
				client.printVerbose("linking " + s1.id + " with " + s2.id + " direction: " + Direction.W );
				;
			}
		}

		if (br.odb.utils.Utils.eqFloat(s1_x1, s2_x0)
				&& br.odb.utils.Utils.eqFloat(s1_z0, s2_z0)
				&& br.odb.utils.Utils.eqFloat(s1_z1, s2_z1)
				&& br.odb.utils.Utils.eqFloat(s1_y0, s2_y0)
				&& br.odb.utils.Utils.eqFloat(s1_y1, s2_y1)) {
			s1.connection.put(Direction.E, s2);
			s2.connection.put(Direction.W, s1);

			if (client != null) {
				client.printVerbose("linking " + s1.id + " with " + s2.id + " direction: " + Direction.E );
				;
			}
		}

		if (br.odb.utils.Utils.eqFloat(s1_y0, s2_y1)
				&& br.odb.utils.Utils.eqFloat(s1_z0, s2_z0)
				&& br.odb.utils.Utils.eqFloat(s1_z1, s2_z1)
				&& br.odb.utils.Utils.eqFloat(s1_x0, s2_x0)
				&& br.odb.utils.Utils.eqFloat(s1_x1, s2_x1)) {
			s1.connection.put(Direction.FLOOR, s2);
			s2.connection.put(Direction.CEILING, s1);

			if (client != null) {
				client.printVerbose("linking " + s1.id + " with " + s2.id + " direction: " + Direction.FLOOR );
				;
			}
		}

		if (br.odb.utils.Utils.eqFloat(s1_y1, s2_y0)
				&& br.odb.utils.Utils.eqFloat(s1_z0, s2_z0)
				&& br.odb.utils.Utils.eqFloat(s1_z1, s2_z1)
				&& br.odb.utils.Utils.eqFloat(s1_x0, s2_x0)
				&& br.odb.utils.Utils.eqFloat(s1_x1, s2_x1)) {
			s1.connection.put(Direction.CEILING, s2);
			s2.connection.put(Direction.FLOOR, s1);

			if (client != null) {
				client.printVerbose("linking " + s1.id + " with " + s2.id + " direction: " + Direction.CEILING );
				;
			}
		}

		if (br.odb.utils.Utils.eqFloat(s1_z0, s2_z1)
				&& br.odb.utils.Utils.eqFloat(s1_x0, s2_x0)
				&& br.odb.utils.Utils.eqFloat(s1_x1, s2_x1)
				&& br.odb.utils.Utils.eqFloat(s1_y0, s2_y0)
				&& br.odb.utils.Utils.eqFloat(s1_y1, s2_y1)) {
			s1.connection.put(Direction.N, s2);
			s2.connection.put(Direction.S, s1);

			if (client != null) {
				client.printVerbose("linking " + s1.id + " with " + s2.id + " direction: " + Direction.N );
				;
			}
		}

		if (br.odb.utils.Utils.eqFloat(s1_z1, s2_z0)
				&& br.odb.utils.Utils.eqFloat(s1_x0, s2_x0)
				&& br.odb.utils.Utils.eqFloat(s1_x1, s2_x1)
				&& br.odb.utils.Utils.eqFloat(s1_y0, s2_y0)
				&& br.odb.utils.Utils.eqFloat(s1_y1, s2_y1)) {
			s1.connection.put(Direction.S, s2);
			s2.connection.put(Direction.N, s1);

			if (client != null) {
				client.printVerbose("linking " + s1.id + " with " + s2.id + " direction: " + Direction.S );
				;
			}
		}
	}

	@Override
	public void setClient(ApplicationClient client) {
		this.client = client;
	}

	@Override
	public void prepareFor(World worldToProcess) {
		world = worldToProcess;
	}

	@Override
	public String toString() {
		return "Settings links between neighbour sectors";
	}
}
