package core;

		import java.awt.Point;

/* TODO:
		 * list_objects
		 * printCalculations
		 * printAnswers
		 * printStatus
		 */


import java.util.List;
import java.util.Scanner;


public class Console 
{
	Scanner s;
	Controller controller;
	
	public Console(Controller controller) 
	{
		this.controller = controller;
		printBar("Runway Re-Declaration Tool");
		
		//System.out.println("Use 'help' to get started.");
	}
	
	/* Print formatted bar
	 * prints a centralised string with bars on top and bottom
	 */
	private void printBar(String str)
	{
		System.out.println("######################################################################");
		for (int i = 0; i < (70 - str.length())/2; i++) { System.out.print(" "); }
		System.out.println(str + "\n######################################################################");
	}
	
	public void printCalculations()
	{
		/* Takes a structure of calculations, formats and outputs */
	}
	
	public void printAnswers()
	{
		
	}
	
	public void printStatus()
	{
		
	}
	
	private Integer readInt(String msg, int min, int max, int defaultResponse) {
		while(true) {
			String inputStr = prompt(msg + "(" + min + "-" + max + ")(Default: " + defaultResponse + "):");
			
			if(inputStr.isEmpty())
				return defaultResponse;
			
			try{
				Integer intgr = Integer.parseInt(inputStr);
				
				if(intgr < min || intgr > max)
					throw new NumberFormatException();
				
				return intgr;
				
			} catch(NumberFormatException e) {
				displayMessage("Expected integer between " + min + " and " + max + ", but " + inputStr + " was given.");
				continue;
			}
		}
	}
	
	private Integer readInt(String msg, int min, int max) {
		while(true) {
			String inputStr = prompt(msg + "(" + min + "-" + max + "):");
			
			try{
				Integer intgr = Integer.parseInt(inputStr);
				
				if(intgr < min || intgr > max)
					throw new NumberFormatException();
				
				return intgr;
				
			} catch(NumberFormatException e) {
				displayMessage("Expected integer between " + min + " and " + max + ", but " + inputStr + " was given.");
				continue;
			}
		}
	}
	
	private Integer readInt(String msg, int defaultResponse) {
		while(true) {
			String inputStr = prompt(msg + "(Default: " + defaultResponse + "):");
			
			if(inputStr.isEmpty())
				return defaultResponse;
			
			try{
				Integer intgr = Integer.parseInt(inputStr);
				
				return intgr;
				
			} catch(NumberFormatException e) {
				displayMessage("Expected integer, but " + inputStr + " was given.");
				continue;
			}
		}
	}
	
	private Integer readInt(String msg) {
		while(true) {
			String inputStr = prompt(msg + ":");
			
			try{
				Integer intgr = Integer.parseInt(inputStr);
				
				return intgr;
				
			} catch(NumberFormatException e) {
				displayMessage("Expected integer, but " + inputStr + " was given.");
				continue;
			}
		}
	}
	
	public void displayMessage(String msg) {
		System.out.println(msg);
	}
	
	public String prompt(String prompt) {
		System.out.print(prompt + "\n$ : ");
		
		s = new Scanner(System.in);
		return s.nextLine();
	}
	
	public void initialAirportConfiguration() {
		displayMessage("Airport Information needs to be configured.");
		controller.createAirport();
	}
	
	public void selectAirports() {
		while(true) {
			List<Airport> airports = controller.getAirports();
			String promptMsg = "Select an Airport:\n";
			int i;
			for(i = 0; i < airports.size(); i++) {
				String name = (airports.get(i).name == null || airports.get(i).name.isEmpty()) ? ("Airport " + i) : airports.get(i).name;
				promptMsg += "[" + i + "] : " + name + "\n";
			}
			promptMsg += "[" + i + "] : Configure New Airport";


			try {
				Integer result = Integer.parseInt(prompt(promptMsg));
				if(result < 0 || result > i)
					throw new NumberFormatException();
				if(result == i) {
					initialAirportConfiguration();
					continue;
				}
				else {
					controller.selectAirport(result);
				}
			} catch(NumberFormatException e) {
				displayMessage("Number expected between 0 and " + i + ".");
				continue;
			}
			break;
		}
	}
	
//	public void addThreshold(Integer airportId, Integer runwayId) {
//		Threshold threshold = configureThreshold();
//		if(threshold != null)
//			controller.addThreshold(airportId, runwayId, threshold);
//	}
	
	public Airport configureAirport() {
		while(true) {
			String airportName = prompt("Enter Airport Name:\n(Or enter '!' to cancel)");
			if(airportName.replaceAll("\\s+", "").equals("!")) {
				return null;
			}
			
			if(airportName.replaceAll("\\s+", "").isEmpty()) {
				displayMessage("An airport name cannot be empty.");
				continue;
			}

			Airport airport = new Airport(airportName);
			
			return airport;
		}
	}
	
	public Runway configureRunway(boolean toCancel) {
		while(true) {
			String runwayName = prompt("Enter runway name:\n(Or enter to '!' to " + (toCancel ? " cancel)" : " finish entering runways."));
			if(runwayName.replaceAll("\\s+", "").equals("!")) {
				return null;
			}

			if(runwayName.replaceAll("\\s+", "").isEmpty()) {
				displayMessage("A runway name cannot be empty.");
				continue;
			}

			Integer resa = readInt("Enter RESA value for runway", 240);
			Integer blastAllowance = readInt("Enter blast allowance value for runway.", 300);
			Integer stripEnd = readInt("Enter strip end value for runway");
			
			Runway runway = new Runway(runwayName, resa, blastAllowance, stripEnd);
			
			Integer angle = readInt("Enter an angle for the runway", 0, 359);
			Integer noRunways = readInt("Enter number of logical runways", 1, 3);
			
			Integer startStopway = readInt("Enter the length of the start stopway");
			Integer endStopway = readInt("Enter the length of the end stopway");
			
			angle /= 10;
			
			for(int i = 0; i < noRunways; i++) {
				for(int j = 0; j < 2; j++) {
					String letter = "";
					
					switch(noRunways) {
						case 1:
							letter = "";
							break;
						case 2:
							letter = (i == j) ? "L" : "R";
							break;
						case 3:
							letter = (i == 1) ? "C" : ((i == j) ? "L" : "R");
							break;
					}
					
					Integer logicalAngle = (j == 0) ? angle : 36 - angle;
					String designator = String.format("%02d" + letter, logicalAngle);
					
					Integer tora = readInt("Enter TORA value for " + designator);
					Integer toda = readInt("Enter TODA value for " + designator);
					Integer asda = readInt("Enter ASDA value for " + designator);
					Integer lda = readInt("Enter LDA value for " + designator);
					
					LogicalRunway lr = new LogicalRunway(String.format("%02d" + letter, logicalAngle), runway, tora, toda, asda, lda, (j == 0) ? endStopway : startStopway);
					runway.addLogicalRunway(lr);
				}
			}
			
			return runway;
		}
	}
	
	public Obstacle configureObstacle() {
		while(true) {
			String obstacleName = prompt("Enter Obstacle Name:\n(Or enter '!' to cancel)");
			if(obstacleName.replaceAll("\\s+", "").equals("!")) {
				return null;
			}
			
			if(obstacleName.replaceAll("\\s+", "").isEmpty()) {
				displayMessage("An obstacle name cannot be empty.");
				continue;
			}

			Integer width = readInt("Enter obstacle width");
			Integer length = readInt("Enter obstacle length");
			Integer height = readInt("Enter obstacle height");
			
			Obstacle obstacle = new Obstacle(obstacleName, width, length, height);
			
			return obstacle;
		}
	}
	
//	private Threshold configureThreshold() {
//		
//		String thresholdDesignator = prompt("Enter threshold designator: ( ##[LR] )\n(Or enter '!' to cancel)");
//
//		if(thresholdDesignator.replaceAll("\\s+", "").equals("!"))
//			return null;
//		
//		while(true) {
//			String data[] = prompt("Enter TORA,TODA,ASDA,LDA information in order separated by a comma,\ne.g. 3902,3902,3902,3592:").split(",");
//			if(data.length < 4) {
//				displayMessage("Not enough information supplied, expected 4 integer values separated by a comma.");
//				continue;
//			}
//
//			Integer iData[] = new Integer[data.length];
//
//			int i = 0;
//			try {
//				for(i = 0; i < 4; i++) {
//					iData[i] = Integer.parseInt(data[i].replaceAll("\\s+", ""));
//				}
//			} catch(NumberFormatException e) {
//				displayMessage("Information in incorrect format, expected number but " + data[i] + " was given.");
//				continue;
//			}
//
//			while(true) {
//				String confirm = prompt("Is this correct?\nTORA: " + data[0] + "\nTODA: " + data[1] + "\nASDA: " + data[2] + "\nLDA: " + data[3] + "\n(y/n)");
//				if(confirm.equalsIgnoreCase("y")) {
//					return new Threshold(thresholdDesignator, iData[0], iData[1], iData[2], iData[3]);
//				}
//				else if(confirm.equalsIgnoreCase("n")) {
//					break;
//				}
//				else {
//					displayMessage("Expected 'y' or 'n'");
//					continue;
//				}
//			}
//		}
//	}
	
	/* Gets user input */
	private String[] getInput()
	{
		System.out.print("$ : ");
		
		s = new Scanner(System.in);
		String input = s.nextLine();
		return input.split(" ");
	}
	
	/* Public input resolver
	 * resolves first argument
	 * checks number of arguments based on function
	 * resolves further arguments
	 * returns quit_flag
	 */
	public boolean handle_input()
	{
		boolean quit_flag = false;
		String[] input = getInput();
		switch( input[0] ) 
		{
		case "help":
			if ( input.length == 1 ) 
			{
				System.out.println("To use the application, use the following commands:"); 
				System.out.println("** NT: type = airport(s), runway(s), threshold(s), obstacle(s); (Use of plural where appropriate)"); 
				System.out.println("* list (types*)"); 
				System.out.println("* select (type) (id)");
				System.out.println("** NT: select object null   -- Clears object");
				System.out.println("* add (type) (id)");
				System.out.println("* delete (type) (id)"); 
				System.out.println("* calculate [-v]"); 
				System.out.println("* status"); 
				System.out.println("* quit"); 
			} else { wrong_args(input); }
			break;
		case "quit":
			if ( input.length == 1 ) 
			{
				quit_flag = true;
			} else { wrong_args(input); }
			break;
		case "list":
			if ( input.length == 2 ) 
			{
				switch ( input[1] )
				{
				case "airports":
					list_airports(controller.getAirports());
					break;
				case "runways":
					list_runways(controller.getRunways());
					break;
				case "thresholds":
					LogicalRunway lr = controller.getSelectedLogicalRunway();
					if(lr != null)
						list_thresholds(lr.runway.logicalRunways);
					else
						System.out.println("No runway is select, use 'select runway [runway_id]'");
					break;
				case "obstacles":
					list_obstacles(controller.getObstacles());
					break;
				default:
					System.out.println("Invalid argument to command 'list (type)'\n : Valid types are; 'airports', 'runways', 'thresholds', 'obstacles'");
					break;
				}
			} else { wrong_args(input); }
			break;
		case "add":
			if ( input.length >= 2 ) 
			{
				switch ( input[1] )
				{
				case "airport":
					controller.createAirport();
					break;
				case "runway":
					if(input.length == 3)
						try {
							Integer airport_id = Integer.parseInt(input[2]);
							if(airport_id < 0 || airport_id >= controller.getAirports().size())
								System.out.println("Invalid airport id, use 'list airports' to list valid airport ids.");
							else
								controller.createRunway(Integer.parseInt(input[2]));
							
						} catch(NumberFormatException e) {
							System.out.println("Expected number for second argument, but " + input[2] + " was given.");
						}
					else {
						wrong_args(input);
						System.out.println("Expected 'add runway [airport_id]'");
					}
					break;
				case "obstacle":
					controller.createObstacle();
					break;
				default:
					System.out.println("Invalid argument to command 'list (type)'\n : Valid types are; 'airports', 'runways', 'obstacles'");
					break;
				}
			} else { wrong_args(input); }
			break;
		case "delete":
			if ( input.length == 3 && isInt(input[2])) 
			{
				try {
					int ID = Integer.parseInt(input[2]);
					switch ( input[1] )
					{
					case "airport":
						controller.deleteAirport(ID);
						break;
					case "runway":
						controller.deleteRunway(ID);
						break;
					case "obstacle":
						controller.deleteObject(ID);
						break;
					default:
						System.out.println("Invalid argument to command 'delete (type) (id)'\n : Valid types are; 'airport', 'runway', 'obstacle'");
						break;
					}
				} catch (NumberFormatException e) {
					System.out.println("Invalid argument to command 'delete (type) (id)'\n : Expected number for id, but " + input[2] + " was given.");
				}
			} else { wrong_args(input); }
			break;
		case "select":
			if ( input.length == 3) 
			{
				try {
					int ID = 0;
					if(!(input[1].equals("obstacle") && input[2].equals("null")))
						ID = Integer.parseInt(input[2]);
					switch ( input[1] )
					{
					case "airport":
						if(controller.selectAirport(ID)) {
							System.out.println("Selected airport: " + controller.getSelectedAirport().name);
						}
						else
							System.out.println("Invalid airport ID, use 'list airports' to get a list of airport IDs.");
						break;
					case "runway":
						if(controller.selectRunway(ID)) {
							LogicalRunway selectedLr = controller.getSelectedLogicalRunway();
							System.out.println(selectedLr.designator + " threshold selected on " + selectedLr.runway.name + ".");
						}
						else
							System.out.println("Invalid runway ID, use 'list runways' to get a list of runway IDs.");
						break;
					case "threshold":
						if(controller.selectThreshold(ID)) {
							LogicalRunway selectedLr = controller.getSelectedLogicalRunway();
							System.out.println(selectedLr.designator + " threshold selected on " + selectedLr.runway.name + ".");
						}
						else
							System.out.println("Invalid threshold ID, use 'list thresholds' to get a list of threshold IDs for the currently selected runway.");
						break;
					case "obstacle":
						if(controller.getSelectedLogicalRunway() == null)
							System.out.println("Select a runway with 'select runway [runway_id]' before adding an obstacle.");
						else if(input[2].equals("null")) {
							controller.clearObstacle();
							System.out.println("Runway is cleared of obstacles.");
						}
						else if(controller.selectObstacle(ID)) {
							Obstacle selectedObstacle = controller.getSelectedObstacle();
							LogicalRunway selectedLr = controller.getSelectedLogicalRunway();
							System.out.println("Added " + selectedObstacle.name + " to " + selectedLr.runway.name + " " + Math.abs(selectedObstacle.distanceFromThreshold) + "m " + (selectedObstacle.distanceFromThreshold < 0 ? "before" : "after") + " " + selectedLr.designator + " threshold and " + Math.abs(selectedObstacle.distanceFromCenterline) + "m " + (selectedObstacle.distanceFromCenterline < 0 ? "south" : "north") + " of centerline.");
						}
						else
							System.out.println("Invalid obstacle ID, use 'list obstacles' to get a list of object IDs.");
						break;
					default:
						System.out.println("Invalid argument to command 'select (type) (id)'\n : Valid types are; 'airport', 'runway', 'threshold', 'obstacle'");
						break;
					}
				} catch (NumberFormatException e) {
					System.out.println("Invalid argument to command 'select (type) (id)'\n : Expected number for id, but " + input[2] + " was given.");
				}
			} else { wrong_args(input); }
			break;
		case "calculate":
			if ( input.length == 1 ) 
			{
				controller.calculate(false);
			}
			else if (input[1] == "-v" )
			{
				//verbose, show calculations
				controller.calculate(true);
			} else { wrong_args(input); }
			break;
		case "status":
			print_status();
			break;
		default:
			for ( int i = 0; i < input.length; i++) System.out.print(input[i] + " ");
			System.out.println(" : Command not found");
		}
		return quit_flag;
	}
	
	
	/* Checks if string is int
	 * returns false if error while converting string to int
	 */
	private boolean isInt(String string) {
		try 
		{
			Integer.parseInt(string);
		}
		catch (Exception e)
		{
			return false;
		}
		return true;
	}

	private void list_airports(List<Airport> airports) {
		if(airports.size() == 0)
			System.out.println("There are currently no airports registered to the system.");
		
		for(int i = 0; i < airports.size(); i++) {
			Airport airport = airports.get(i);
			String name = (airport.name == null || airport.name.isEmpty()) ? ("Airport " + i) : airport.name;
			System.out.println("[" + i + "] : " + name);
		}
	}
	
	private void list_runways(List<Runway> runways) {
		if(runways == null) {
			System.out.println("No airport is currently selected. Please select one with 'select airport'.");
			return;
		}
		if(runways.size() == 0)
			System.out.println("There are no runways registered to this airport.");
		
		for(int i = 0; i < runways.size(); i++) {
			Runway runway = runways.get(i);
			String name = (runway.name == null || runway.name.isEmpty()) ? ("Runway " + i) : runway.name;
			System.out.println("[" + i + "] : " + name);
		}
	}
	
	private void list_thresholds(List<LogicalRunway> lrs) {
		if(lrs.size() == 0)
			System.out.println("There are no thresholds configured for this runway. Add a new runway with 'add runway' to set up a new runway with thresholds.");
		
		for(int i = 0; i < lrs.size(); i++) {
			LogicalRunway lr = lrs.get(i);
			System.out.println("[" + i + "] : " + lr.designator);
		}
	}
	
	private void list_obstacles(List<Obstacle> obstacles) {
		if(obstacles.size() == 0)
			System.out.println("There are no obstacles registered to the system. Add some with 'add obstacle'");
		
		for(int i = 0; i < obstacles.size(); i++) {
			Obstacle obstacle = obstacles.get(i);
			String name = (obstacle.name == null || obstacle.name.isEmpty()) ? ("Obstacle " + i) : obstacle.name;
			System.out.println("[" + i + "] : " + name);
		}
	}
	
	private void wrong_args(String[] input)
	{
		for ( int i = 0; i < input.length; i++) System.out.print(input[i] + " ");
		System.out.println(" : Invalid number of arguments");
	}
	
	public Point getObstaclePosition() {
		Integer x = readInt("Enter the obstacle's distance from the " + controller.getSelectedLogicalRunway().designator + " threshold.\n(Negative for before threshold, Positive for after threshold)");
		Integer y = readInt("Enter the obstacle's distance from the centerline\n(Positive for north of centerline, Negative for south of centerline)");
		
		return new Point(x, y);
	}
	
	public void selectThreshold(Runway runway) {
		String prompt = "Select a threshold to use for " + runway.name + ":\n";
		for(int i = 0; i < runway.logicalRunways.size(); i++) {
			prompt += "[" + i + "] : " + runway.logicalRunways.get(i).designator + "\n";
		}
		controller.selectThreshold(readInt(prompt, 0, runway.logicalRunways.size() - 1));
	}
	
	private void print_status() {
		Airport airport = controller.getSelectedAirport();
		LogicalRunway lr = controller.getSelectedLogicalRunway();
		Obstacle obstacle = controller.getSelectedObstacle();
		
		System.out.println("Airport: " + (airport == null ? "None selected" : airport.name));
		System.out.println("Runway: " + (lr == null ? "None selected" : lr.runway.name));
		System.out.println("Threshold: " + (lr == null ? "None selected" : lr.designator));
		
		System.out.println("\nObstacle\n--------");
		if(obstacle == null)
			System.out.println("None selected.");
		else {
			System.out.println("Type: " + obstacle.name);
			System.out.println("Distance from " + lr.designator + ": " + obstacle.distanceFromThreshold + "m");
			System.out.println("Distance from centerline: " + obstacle.distanceFromCenterline + "m");
		}
		
		if(lr != null) {
			System.out.println("\nOriginal Values");
			System.out.println("---------------");
			System.out.println("TODA: " + lr.toda);
			System.out.println("TORA: " + lr.tora);
			System.out.println("ASDA: " + lr.asda);
			System.out.println("LDA: " + lr.lda);
			System.out.println("Displaced Threshold: " + lr.displacedThreshold);
			System.out.println("Clearway Length: " + lr.clearwayLength);
			System.out.println("Stopway Length: " + lr.stopwayLength);
			System.out.println("RESA: " + lr.getRESA());
			System.out.println("Blast Allowance: " + lr.getBlastAllowance());
			System.out.println("Strip End: " + lr.getStripEnd());
		}
	}
	
	/* Displays  sign 
	 * halts program for 3 seconds to notify user
	 */
	public void quit()
	{
		printBar("Exiting Application");
		s.close();
	}
}
