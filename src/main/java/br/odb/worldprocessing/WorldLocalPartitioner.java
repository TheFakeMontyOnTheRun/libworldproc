/**
 * 
 */
package br.odb.worldprocessing;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.odb.gameapp.ApplicationClient;
import br.odb.libscene.GroupSector;
import br.odb.libscene.SceneNode;
import br.odb.libscene.Sector;
import br.odb.libscene.SpaceRegion;
import br.odb.libscene.World;

/**
 * @author monty
 * 
 */
public class WorldLocalPartitioner extends WorldPartitioner {

	public WorldLocalPartitioner(ApplicationClient client, World worldToProcess) {
		super(client, worldToProcess);
	}

	@Override
	public void run() {

		List<SceneNode> regions;

		generateLeafsForWorld(world);
		int previous = -1;

		regions = world.getAllRegionsAsList();

		Set<Hyperplane> planes = new HashSet<>();

		List<GroupSector> groups = new ArrayList<GroupSector>();

		for (SceneNode sr1 : regions) {
			if (sr1 instanceof GroupSector) {
				groups.add((GroupSector) sr1);
			}
		}

		while (previous != this.getTotalSectors(world)) {

			previous = this.getTotalSectors(world);

			for (GroupSector sr1 : groups) {
				for (GroupSector sr2 : groups) {
					if (sr1 != sr2) {

						planes.clear();

						for (SceneNode sn : sr2.getSons()) {
							if (sn instanceof Sector
									&& sr1.intersects((SpaceRegion) sn)) {
								planes.addAll(getAllHyperplanesForSector((SpaceRegion) sn));
							}
						}
						splitSectorsWithPlanesFrom(sr1, planes);

					}
				}
			}
		}

		client.printVerbose(" partitioning finished!");
		client.printVerbose(" generated " + getTotalSectors(world)
				+ " leaf sector(s)");
	}

	@Override
	public String toString() {

		return "World-wide local partitioning";
	}
}
