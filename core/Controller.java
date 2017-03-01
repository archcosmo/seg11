package core;

import java.util.List;

public class Controller 
{
	Console view;
	Model model;
	
	static public void main(String[] args) { new Controller(); } /* Initialise program */
	
	public Controller() 
	{
		/* Initialise state */
		view = new Console(this);
		model = new Model(view);
		
		//Check for XML data and load if exists
		//Prompt user to configure if not
		init();
		
		//Ask model to get airport, returns NULL if none
		//Ask model for run-ways
		loop();
	}
	
	/* Main program loop
	 * loops user input
	 * - exits on program 'quit' command
	 * terminates program by calling quit method
	 */
	private void loop()
	{
		while ( !view.handle_input() );
		quit();
	}
	
	private void init() {
		if(!model.loadAirportInfoFromFile())
			initialAirportConfiguration();

		model.loadObstacleInfoFromFile();
		
		view.selectAirports();
	}
	
	public boolean createAirport() {
		Airport airport = view.configureAirport();
		
		if(airport == null)
			return false;
		
		Runway runway = null;
		do {
			runway = view.configureRunway(false);
			if(runway != null)
				airport.addRunway(runway);
		} while(runway != null);
		
		model.addAirport(airport);
		model.saveAirportInfoToFile();
		return true;
	}
	
	public boolean createRunway(int airportId) {
		Runway runway = view.configureRunway(true);
		
		if(runway == null)
			return false;
		
		return addRunway(airportId, runway);
	}
	
	public boolean createObstacle() {
		Obstacle obstacle = view.configureObstacle();
		
		if(obstacle == null)
			return false;
		
		model.addObstacle(obstacle);
		model.saveObstacleInfoToFile();
		
		return true;
	}
	
	//TODO: Replace the false return with exceptions so more detailed error messages can be passed back.
	private boolean addRunway(Integer airportId, Runway runway) {
		try {
			Airport airport = model.getAirports().get(airportId);
			if(airport == null)
				return false;
			
			airport.addRunway(runway);
			model.saveAirportInfoToFile();
			return true;
		} catch(IndexOutOfBoundsException e) {
			return false;
		}
	}
	
//	private boolean addLogicalRunway(Integer airportId, Integer runwayId, LogicalRunway logicalRunway) {
//		try {
//			Airport airport = model.getAirports().get(airportId);
//			if(airport == null)
//				return false;
//			
//			Runway runway = airport.runways.get(runwayId);
//			if(runway == null)
//				return false;
//
//			runway.addLogicalRunway(logicalRunway);
//			model.saveAirportInfoToFile();
//			return true;
//		} catch(IndexOutOfBoundsException e) {
//			return false;
//		}
//	}
	
	private void initialAirportConfiguration() {
		view.initialAirportConfiguration();
		model.saveAirportInfoToFile();
	}
	
//	private void initialAirportConfiguration() {
//		String airportName = "";
//		Airport airport = null;
//		while(true) {
//			airportName = view.prompt("Airport Information needs to be configured.\nEnter Airport Name:");
//			if(airportName.replaceAll("\\s+", "").isEmpty()) {
//				view.displayMessage("An airport name cannot be empty.");
//				continue;
//			}
//			
//			airport = new Airport(airportName);
//			break;
//		}
//		boolean finishedRunways = false;
//		while(!finishedRunways) {
//			String runwayName = view.prompt("Enter runway name for " + airportName + ",\n"
//					+ "or enter to '!' to finish entering runways:");
//			if(runwayName.equals("!")) {
//				finishedRunways = true;
//				break;
//			}
//
//			if(runwayName.replaceAll("\\s+", "").isEmpty()) {
//				view.displayMessage("An runway name cannot be empty.");
//				continue;
//			}
//			
//			Runway runway = new Runway(runwayName);
//
//			boolean finishedThresholds = false;
//			while(!finishedThresholds) {
//				String thresholdDesignator = view.prompt("Enter threshold designator for " + runwayName + ",\n"
//						+ "or enter '!' to finish entering thresholds:");
//				if(thresholdDesignator.equals("!")) {
//					finishedThresholds = true;
//					break;
//				}
//
//				boolean enteringThresholdInfo = true;
//				while(enteringThresholdInfo) {
//					String data[] = view.prompt("Enter TORA,TODA,ASDA,LDA information in order separated by a comma,\ne.g. 3902,3902,3902,3592:").split(",");
//					if(data.length < 4) {
//						view.displayMessage("Not enough information supplied, expected 4 integer values separated by a comma.");
//						continue;
//					}
//
//					Integer iData[] = new Integer[data.length];
//					
//					int i = 0;
//					try {
//						for(i = 0; i < 4; i++) {
//							iData[i] = Integer.parseInt(data[i]);
//						}
//					} catch(NumberFormatException e) {
//						view.displayMessage("Information in incorrect format, expected number but " + data[i] + " was given.");
//						continue;
//					}
//
//					while(true) {
//						String confirm = view.prompt("Is this correct?\nTORA: " + data[0] + "\nTODA: " + data[1] + "\nASDA: " + data[2] + "\nLDA: " + data[3] + "\n(y/n)");
//						if(confirm.equalsIgnoreCase("y")) {
//							runway.addThreshold(new Threshold(thresholdDesignator, iData[0], iData[1], iData[2], iData[3]));
//							break;
//						}
//						else if(confirm.equalsIgnoreCase("n")) {
//							break;
//						}
//						else {
//							view.displayMessage("Expected 'y' or 'n'");
//							continue;
//						}
//					}
//					
//					enteringThresholdInfo = false;
//				}
//				
//			}
//			airport.addRunway(runway);
//		}
//		model.addAirport(airport);
//		model.saveAirportInfoToFile();
//	}
	
	public List<Airport> getAirports() { return model.getAirports(); }
	public List<Runway> getRunways() { return model.getRunways(); }
	public List<Obstacle> getObstacles() { return model.getObjects(); }

	/* Safely closes application */
	private void quit() 
	{
		view.quit();
		view = null;
		model.quit();
		model = null;
	}

	
	/* Many of these types will have to be changed */
	public void calculate(boolean b) {}
	public void getStatus() {}

	/* Return false if ID does not exist */
	public boolean selectObject(int ID) { return false; }
	public boolean selectAirport(int ID) { return model.selectAirport(ID);}
	public boolean selectRunway(int ID) { return false; }

	public void deleteAirport(int iD) {}
	public void deleteRunway(int iD) {}
	public void deleteObject(int iD) {}
}
