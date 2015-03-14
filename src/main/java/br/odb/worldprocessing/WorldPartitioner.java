package br.odb.worldprocessing;

import br.odb.gameapp.ApplicationClient;
import br.odb.libscene.SpaceRegion;
import br.odb.libscene.World;
import br.odb.utils.Direction;
import br.odb.utils.math.Vec3;

public abstract class WorldPartitioner implements WorldProcessor {

	World world;
	ApplicationClient client;

	public static Hyperplane generateHyperplane(final SpaceRegion sr,
			final Direction kind) {
		float n = 0.0f;
		Vec3 position = sr.getAbsolutePosition();

		switch (kind) {
		case N:
			n = position.z;
			break;
		case S:
			n = position.z + sr.size.z;
			break;
		case W:
			n = position.x;
			break;
		case E:
			n = position.x + sr.size.x;
			break;
		case FLOOR:
			n = position.y;
			break;
		case CEILING:
			n = position.y + sr.size.y;
			break;
		}

		return new Hyperplane(kind, n, sr);
	}

	@Override
	public void setClient(ApplicationClient client) {
		this.client = client;
	}

	@Override
	public void prepareFor(World worldToProcess) {
		world = worldToProcess;
	}
}
