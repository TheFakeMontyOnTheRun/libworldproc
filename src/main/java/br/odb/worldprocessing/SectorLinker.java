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

			for (SpaceRegion s2 : sectors) {

				if (s2 == s1)
					continue;

				if (s2 instanceof GroupSector) {
					continue;
				}

//				if (s1.parent == s2.parent) {

					checkLinksForSectors((Sector) s1, (Sector) s2);
	//			}
			}
		}
	}

	/**
	 * @param s1
	 * @param s2
	 */
	private void checkLinksForSectors(Sector s1, Sector s2) {

		Vec3 pos2 = s2.getAbsolutePosition();

		float s2_x0 = pos2.x;
		float s2_x1 = pos2.x + s2.size.x;
		float s2_y0 = pos2.y;
		float s2_y1 = pos2.y + s2.size.y;
		float s2_z0 = pos2.z;
		float s2_z1 = pos2.z + s2.size.y;

		Vec3 pos1 = s1.getAbsolutePosition();

		float s1_x0 = pos1.x;
		float s1_x1 = pos1.x + s1.size.x;
		float s1_y0 = pos1.y;
		float s1_y1 = pos1.y + s1.size.y;
		float s1_z0 = pos1.z;
		float s1_z1 = pos1.z + s1.size.y;

		if (br.odb.utils.Utils.eqFloat(s1_x0, s2_x1)
				&& br.odb.utils.Utils.eqFloat(s1_z0, s2_z0)
				&& br.odb.utils.Utils.eqFloat(s1_z1, s2_z1)
				&& br.odb.utils.Utils.eqFloat(s1_y0, s2_y0)
				&& br.odb.utils.Utils.eqFloat(s1_y1, s2_y1)) {

			s1.connection.put(Direction.W, s2);
			s2.connection.put(Direction.E, s1);

			if (client != null) {
				client.printVerbose("linking " + s1.id + " with " + s2.id);
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
				client.printVerbose("linking " + s1.id + " with " + s2.id);
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
				client.printVerbose("linking " + s1.id + " with " + s2.id);
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
				client.printVerbose("linking " + s1.id + " with " + s2.id);
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
				client.printVerbose("linking " + s1.id + " with " + s2.id);
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
				client.printVerbose("linking " + s1.id + " with " + s2.id);
				;
			}
		}
	}

	@Override
	public void setClient(ApplicationClient client) {
		// this.client = client;
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
