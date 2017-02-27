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
		if(model.airportXMLInfoExists()) {
			model.loadAirportInfoFromFile();
		}
		else {
			initialAirportConfiguration();
		}
	}
	
	private void initialAirportConfiguration() {
		String airportName = view.prompt("Airport Information needs to be configured.\nEnter Airport Name:");
		Airport airport = new Airport(airportName);
		
		boolean finishedRunways = false;
		while(!finishedRunways) {
			String runwayName = view.prompt("Enter runway name for " + airportName + ",\n"
					+ "or enter to '!' to finish entering runways:");
			if(runwayName.equals("!")) {
				finishedRunways = true;
				break;
			}

			Runway runway = new Runway(runwayName);

			boolean finishedThresholds = false;
			while(!finishedThresholds) {
				String thresholdDesignator = view.prompt("Enter threshold designator for " + runwayName + ",\n"
						+ "or enter '!' to finish entering thresholds:");
				if(thresholdDesignator.equals("!")) {
					finishedThresholds = true;
					break;
				}

				boolean enteringThresholdInfo = true;
				while(enteringThresholdInfo) {
					String data[] = view.prompt("Enter TORA,TODA,ASDA,LDA information in order separated by a comma,\ne.g. 3902,3902,3902,3592:").split(",");
					if(data.length < 4) {
						view.displayMessage("Not enough information supplied, expected 4 integer values separated by a comma.");
						continue;
					}

					Integer iData[] = new Integer[data.length];
					
					int i = 0;
					try {
						for(i = 0; i < 4; i++) {
							iData[i] = Integer.parseInt(data[i]);
						}
					} catch(NumberFormatException e) {
						view.displayMessage("Information in incorrect format, expected number but " + data[i] + " was given.");
						continue;
					}

					while(true) {
						String confirm = view.prompt("Is this correct?\nTORA: " + data[0] + "\nTODA: " + data[1] + "\nASDA: " + data[2] + "\nLDA: " + data[3] + "\n(y/n)");
						if(confirm.equalsIgnoreCase("y")) {
							runway.addThreshold(new Threshold(thresholdDesignator, iData[0], iData[1], iData[2], iData[3]));
							break;
						}
						else if(confirm.equalsIgnoreCase("n")) {
							break;
						}
						else {
							view.displayMessage("Expected 'y' or 'n'");
							continue;
						}
					}
					
					enteringThresholdInfo = false;
				}
				
			}
			airport.addRunway(runway);
		}
		model.addAirport(airport);
		model.selectAirport(model.getAirports().size() - 1);
		model.saveAirportInfoToFile();
	}
	
	public List<Airport> getAirports() { return model.getAirports(); }
	public List<Runway> getRunways() { return model.getRunways(); }
	public void getObjects() { model.getObjects(); }

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
	public boolean selectAirport(int ID) { return false; }
	public boolean selectRunway(int ID) { return false; }

	public void deleteAirport(int iD) {}
	public void deleteRunway(int iD) {}
	public void deleteObject(int iD) {}
}
