	package core;

	import java.util.ArrayList;

	public class Runway {
		String name;
		ArrayList<LogicalRunway> logicalRunways;
		int RESA;
		int blastAllowance;

		//TODO: where should runway RESA and blast allowance be stored?
		public Runway(String name, int RESA, int blastAllowance) {
			this.name = name;
		}

		//TODO: method to add many Logical Runways
		public void addLogicalRunway(LogicalRunway logicalRunway) {
			logicalRunways.add(logicalRunway);
		}
	}