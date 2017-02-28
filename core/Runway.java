	package core;

	import java.util.ArrayList;

	public class Runway {
		String name;
		ArrayList<LogicalRunway> logicalRunways; //TODO: store runways in pairs?

		public Runway(String name) {
			this.name = name;
		}

		public void addLogicalRunway(LogicalRunway logicalRunway) {
			//TODO: add many LogRuns
			logicalRunways.add(logicalRunway);
		}
	}