package br.odb.worldprocessing;

import br.odb.libscene.SpaceRegion;

public class GroupSectorSightLine {
	public final SpaceRegion sr1;
	public final SpaceRegion sr2;
	
	public GroupSectorSightLine( SpaceRegion sr1, SpaceRegion sr2 ) {
		this.sr2 = sr2;
		this.sr1 = sr1;
	}
}
