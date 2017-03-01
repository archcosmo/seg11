	package core;

	import java.util.ArrayList;
import java.util.List;

	public class Runway {
		String name;
		List<LogicalRunway> logicalRunways;
		int RESA;
		int blastAllowance;

		//TODO: where should runway RESA and blast allowance be stored?
		public Runway(String name, int RESA, int blastAllowance) {
			this.name = name;
			logicalRunways = new ArrayList<LogicalRunway>();
		}

		//TODO: method to add many Logical Runways
		public void addLogicalRunway(LogicalRunway logicalRunway) {
			logicalRunways.add(logicalRunway);
		}
	}