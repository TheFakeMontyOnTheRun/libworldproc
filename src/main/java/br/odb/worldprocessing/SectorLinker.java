/**
 * 
 */
package br.odb.worldprocessing;

import br.odb.gameapp.ApplicationClient;
import br.odb.libscene.Constants;
import br.odb.libscene.Sector;
import br.odb.libscene.World;
import br.odb.utils.Utils;

/**
 * @author monty
 * 
 */
public class SectorLinker implements WorldProcessor {

	private World world;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

		for (Sector s1 : world) {

			if (s1.isMaster())
				continue;

			for (Sector s2 : world) {

				if (s1 == s2)
					continue;

				if (s2.isMaster())
					continue;

				checkLinksForSectors(s1, s2);
			}
		}
	}

	/**
	 * @param s1
	 * @param s2
	 */
	private void checkLinksForSectors(Sector s1, Sector s2) {
		if (Utils.eqFloat(s1.getX0(), s2.getX1())
				&& Utils.eqFloat(s1.getZ0(), s2.getZ0())
				&& Utils.eqFloat(s1.getZ1(), s2.getZ1())
				&& Utils.eqFloat(s1.getY0(), s2.getY0())
				&& Utils.eqFloat(s1.getY1(), s2.getY1())) {
			s1.setLink(Constants.FACE_W, s2.getId());
			s2.setLink(Constants.FACE_E, s1.getId());
		}

		if (Utils.eqFloat(s1.getX1(), s2.getX0())
				&& Utils.eqFloat(s1.getZ0(), s2.getZ0())
				&& Utils.eqFloat(s1.getZ1(), s2.getZ1())
				&& Utils.eqFloat(s1.getY0(), s2.getY0())
				&& Utils.eqFloat(s1.getY1(), s2.getY1())) {
			s1.setLink(Constants.FACE_E, s2.getId());
			s2.setLink(Constants.FACE_W, s1.getId());
		}

		if (Utils.eqFloat(s1.getY0(), s2.getY1())
				&& Utils.eqFloat(s1.getZ0(), s2.getZ0())
				&& Utils.eqFloat(s1.getZ1(), s2.getZ1())
				&& Utils.eqFloat(s1.getX0(), s2.getX0())
				&& Utils.eqFloat(s1.getX1(), s2.getX1())) {
			s1.setLink(Constants.FACE_FLOOR, s2.getId());
			s2.setLink(Constants.FACE_CEILING, s1.getId());
		}

		if (Utils.eqFloat(s1.getY1(), s2.getY0())
				&& Utils.eqFloat(s1.getZ0(), s2.getZ0())
				&& Utils.eqFloat(s1.getZ1(), s2.getZ1())
				&& Utils.eqFloat(s1.getX0(), s2.getX0())
				&& Utils.eqFloat(s1.getX1(), s2.getX1())) {
			s1.setLink(Constants.FACE_CEILING, s2.getId());
			s2.setLink(Constants.FACE_FLOOR, s1.getId());
		}

		if (Utils.eqFloat(s1.getZ0(), s2.getZ1())
				&& Utils.eqFloat(s1.getX0(), s2.getX0())
				&& Utils.eqFloat(s1.getX1(), s2.getX1())
				&& Utils.eqFloat(s1.getY0(), s2.getY0())
				&& Utils.eqFloat(s1.getY1(), s2.getY1())) {
			s1.setLink(Constants.FACE_N, s2.getId());
			s2.setLink(Constants.FACE_S, s1.getId());
		}

		if (Utils.eqFloat(s1.getZ1(), s2.getZ0())
				&& Utils.eqFloat(s1.getX0(), s2.getX0())
				&& Utils.eqFloat(s1.getX1(), s2.getX1())
				&& Utils.eqFloat(s1.getY0(), s2.getY0())
				&& Utils.eqFloat(s1.getY1(), s2.getY1())) {
			s1.setLink(Constants.FACE_S, s2.getId());
			s2.setLink(Constants.FACE_N, s1.getId());
		}
	}

	@Override
	public void setClient(ApplicationClient client) {
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
