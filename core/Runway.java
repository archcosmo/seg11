	package core;

	import java.util.ArrayList;
import java.util.List;

	public class Runway {
		String name;
		List<LogicalRunway> logicalRunways;
		int RESA, blastAllowance, stripEnd;

		//TODO: where should runway RESA and blast allowance be stored?
		public Runway(String name, int RESA, int blastAllowance, int stripEnd) {
			this.name = name;
			this.RESA = RESA;
			this.blastAllowance = blastAllowance;
			this.stripEnd = stripEnd;
			logicalRunways = new ArrayList<LogicalRunway>();
		}

		//TODO: method to add many Logical Runways
		public void addLogicalRunway(LogicalRunway logicalRunway) {
			logicalRunways.add(logicalRunway);
		}
	}