package core;

import java.awt.Point;
import java.util.List;
import java.util.Scanner;

import com.sun.media.sound.InvalidDataException;


public class Console 
{
	Scanner s;
	Controller controller;
	
	public Console(Controller controller) 
	{
		this.controller = controller;
		printBar("Runway Re-Declaration Tool");
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
	
	private Integer readInt(String msg, int min, int max) {
		while(true) {
			String inputStr = prompt(msg + "(Enter a number between: " + min + " and " + max + "):");
			
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
		displayMessage("\nNew Airport Information needs to be configured. \nEntering new Airport Configurator:\n");
		controller.createAirport();
	}
	
	public void selectAirports() {
		while(true) {
			List<Airport> airports = controller.getAirports();
			String promptMsg = "\nSelect an Airport: (Type corresponding number)\n";
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
				displayMessage("Number expected between 0 and " + i + ".\n");
				continue;
			}
			displayMessage("\nAirport has been selected.\n\nEntering application: Use 'help' command for a list of commands and tips.");
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
			//Not sure how to word first prompt.
			String confirmation = prompt("Press Enter to add runway:\n(Or type '!' to " + (toCancel ? " cancel)" : " finish entering runways."));
			if(confirmation.replaceAll("\\s+", "").equals("!")) {
				return null;
			}

			Integer runwayLength = readInt("Enter runway length in meters", 100, 10000);
			System.out.println("");
			Integer runwayWidth = readInt("Enter runway width in meters", 5, 100);
			System.out.println("");
			Integer resa = readInt("Enter RESA value for runway in meters", 240);
			System.out.println("");
			Integer blastAllowance = readInt("Enter blast allowance value for runway in meters", 300);
			System.out.println("");
			Integer stripEnd = readInt("Enter strip end value for runway in meters", 60);
			System.out.println("");
			Runway runway = new Runway(resa, blastAllowance, stripEnd, runwayLength, runwayWidth);
			
			Integer firstAngle = readInt("Enter an angle for the runway", 0, 359);
			System.out.println("");
			firstAngle /= 10;
			Integer reciprocalAngle = (firstAngle + 18) % 36;

			LogicalRunway shortAngleLogicalRunway = null; //needs to be initialised for add to runway method
			LogicalRunway longAngleLogicalRunway = null;
			System.out.println("");

			for(int i = 0; i < 2; i++) {
				String letter = "";
				//TODO::letter - find any other runways in airport with same angle
				int runwayAngle = (i == 0) ? firstAngle : reciprocalAngle;
				String designator = String.format("%02d" + letter, runwayAngle);

				Integer tora, toda, asda, lda;
				Boolean acceptedValue;
				do {
					tora = readInt("Enter TORA value in meters for " + designator, 0, 10000);
					System.out.println("");
					if (tora > runwayLength) {
						acceptedValue = false;
						System.out.println("TORA can't be larger than runway length");
					} else {
						acceptedValue = true;
					}
				} while (!acceptedValue);
				do {
					lda = readInt("Enter LDA value in meters for " + designator, 0, 10000);
					System.out.println("");
					if (lda > tora) {
						acceptedValue = false;
						System.out.println("LDA can't be larger than TORA");
					} else {
						acceptedValue = true;
					}
				} while (!acceptedValue);
				do {
					asda = readInt("Enter ASDA value in meters for " + designator, 0, 10000);
					System.out.println("");
					if (asda < tora) {
						acceptedValue = false;
						System.out.println("ASDA can't be less than TORA");
					} else {
						acceptedValue = true;
					}
				} while (!acceptedValue);
				do {
					toda = readInt("Enter TODA value in meters for " + designator, 0, 10000);
					System.out.println("");
					if (toda < tora) {
						acceptedValue = false;
						System.out.println("TODA can't be less than TORA");
					} else {
						acceptedValue = true;
					}
				} while (!acceptedValue);

				LogicalRunway lr = new LogicalRunway(designator, runway, tora, toda, asda, lda, 0);
				if (runwayAngle < 18) {
					shortAngleLogicalRunway = lr;
				} else {
					longAngleLogicalRunway = lr;
				}
			}
			runway.setLogicalRunways(shortAngleLogicalRunway, longAngleLogicalRunway);
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
				displayMessage("An obstacle name cannot be blank.");
				continue;
			}

			Integer width = readInt("Enter obstacle width in meters", 1, 1000);
			Integer length = readInt("Enter obstacle length in meters", 1, 5000);
			Integer height = readInt("Enter obstacle height in meters", 1, 500);
			
			Obstacle obstacle = new Obstacle(obstacleName, width, length, height);
			
			return obstacle;
		}
	}
	
	/* Gets user input */
	private String[] getInput()
	{
		System.out.print("$ : ");
		
		s = new Scanner(System.in);
		String input = s.nextLine();
		return input.split(" ");
	}
	
	private void promptNext() 
	{
		System.out.print("\n[Press Enter to Continue]");
		s = new Scanner(System.in);
		s.nextLine();
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
				printBar("\nProgram Help:\n"
						+ "(For a full walkthrough of using the program, use \"help walkthrough\")\n");
				
//				System.out.println("Commands are written in the UNIX standard notation:\n"
//						+ "\t (bracketed) should be replaced with an input value\n"
//						+ "\t\teg.. list (type) would become \"list airports\"\n"
//						+ "\t [square] bracketed terms are optional inputs\n"
//						+ "\t\teg.. calculate [-v] (T|A) could become \"calculate -v T\" or \"calculate T\" or \"calculate -v A\" or other combinations\n");
				System.out.println("\nlist (type) : returns a list of the selected type.");
				System.out.println("  | (type) = airports, runways, thresholds, or obstacles");

				System.out.println("\nselect (type) : selects which airport, runway, designator, direction, and obstacle to use in calculation.");
				System.out.println("  | (type) = airport, runway, designator, direction, or obstacle");
				System.out.println("\nadd (type) (id)");
				System.out.println("  | (type) = airport, runway, or obstacle");
				System.out.println("  | get (id) using list command.");
//				System.out.println("\ncalculate [-v] [T|A]");
//				System.out.println("  | -v : Verbose, prints the calculation breakdown.");
//				System.out.println("  |  T : Take-off/Land towards selected designator.");
//				System.out.println("  |  A : Take-off/Land away from selected designator.");	
				System.out.println("\nstatus : Prints current selected system to console view\n");
				System.out.println("breakdown : Prints a breakdown of the last calculation\n");
				System.out.println("quit : Quits the program\n");
				System.out.println("######################################################################\n");
			} 
			else if ( input.length == 2 && input[1].equals("walkthrough")) 
			{
				printBar("Program Walkthrough Tutorial:");
				System.out.println("\nWelcome to the program walkthrough section. This will guide you through your first time using the application");
				System.out.println("\tThis tutorial will reference several commands, however a short-form detailed list of commands and inputs can be obtained from the 'help' command");
				promptNext(); 
				
				System.out.println("\nWhen you entered the application, you had to select (or configure, then select) an airport.");
				System.out.println("You can check which airport the program is currently configured to with \"status\" : this command will print everything about the current application state");
				System.out.println("\nThe application works a lot like a shelf. You must put one of each item on the shelf before you run any calculations.");
				System.out.println("\tThe application stores the last selected airport, runway and object information for you, and uses that information when you run \"calculate\"");
				promptNext(); 
				
				System.out.println("\nIf you wanted to change the currenly configured airport, you can list the airports avaluable in the system, listed by ID, and then use the corresponding ID to select one"
						+ "\n\t\"list airports\" : This will return a numbered list of all airports in the system."
						+ "\n\t\"select airport 0\" : This will select the top airport in the list, which had a [0] next to it in the list.");
				promptNext(); 
				
				System.out.println("\nUsing this same method, you can now select a runway"
						+ "\n\t\"list runways\" : Prints a list of runways"
						+ "\n\t\"select runway 0\" : Selects first runway on list"
						+ "At this point, you will be prompted with further input, asking you to select a logical runway direction, for this runway selection"
						+ "\n\nWhen doing this for objects, you will be prompted for input of the object position. Input the values as indicated."
						+ "It is also possible to clear object using \"select object null\" for when you dont want an object in the system\n\n");
				promptNext();
				
				System.out.println("Once you have an airport and runway selected (optionally an object too), you are ready to run any calculations"
						+ "\n\t\"calculate -v T\" : Prints the calculation results, along with a calculation breakdown. 'T' Indicating takeoff direction as towards the selected threshold"
						+ "\n\tWhile doing these commands, the second window of this application, containing the diagrams, will be updated");
				System.out.println("Finally, once you are done with the application, you can quit the application safely using the \"quit\" command.");
				promptNext();
				
				printBar("End of tutorial");
				
			}
			else { wrong_args(input); }
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
				case "designators":
					LogicalRunway lr = controller.getSelectedLogicalRunway();
					if(lr != null) {
						System.out.print("[0]: ");
						list_thresholds(lr.runway.shortAngleLogicalRunway);
						System.out.print("[1]: ");
						list_thresholds(lr.runway.longAngleLogicalRunway);
					}
					else
						System.out.println("No runway is select, use 'select runway [runway_id]'");
					break;
				case "obstacles":
					list_obstacles(controller.getObstacles());
					break;
				default:
					System.out.println("Invalid argument to command 'list (type)'\n : Valid types are; 'airports', 'runways', 'designators', 'obstacles'\n");
					break;
				}
			} else { 
				System.out.println("Invalid argument to command 'list (type)'\n : Valid types are; 'airports', 'runways', 'designators', 'obstacles'\n");
			}
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
								System.out.println("Invalid airport id, use 'list airports' to list valid airport ids.\n");
							else
								controller.createRunway(Integer.parseInt(input[2]));
							
						} catch(NumberFormatException e) {
							System.out.println("Expected number for second argument, but \"" + input[2] + "\" was given.\n");
						}
					else {
						wrong_args(input);
						System.out.println("Expected 'add runway [airport_id]'\n");
					}
					break;
				case "obstacle":
					controller.createObstacle();
					break;
				default:
					System.out.println("Invalid argument to command 'add (type)'\n : Valid types are; 'airport', 'runway', 'obstacle'\n");
					break;
				}
			} else { 
				System.out.println("Invalid argument to command 'add (type)'\n : Valid types are; 'airport', 'runway', 'obstacle'\n");
			}
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
						System.out.println("Invalid argument to command 'delete (type) (id)'\n : Valid types are; 'airport', 'runway', 'obstacle'\n");
						break;
					}
				} catch (NumberFormatException e) {
					System.out.println("Invalid argument to command 'delete (type) (id)'\n : Expected number for id, but \"" + input[2] + "\" was given.\n");
				}
			} else { 
				System.out.println("Invalid argument to command 'delete (type) (id)'\n : Valid types are; 'airport', 'runway', 'obstacle'\n");
			}
			break;
		case "select":
			if ( input.length == 2) 
			{
					switch ( input[1] )
					{
					case "airport":
						selectAirports();
						break;
					case "runway":
						selectRunway();
						break;
					case "designator":
						if(controller.getSelectedLogicalRunway() != null)
							selectThreshold(controller.getSelectedLogicalRunway().runway);
						else
							selectRunway();
						break;
					case "obstacle":
						if(controller.getSelectedLogicalRunway() == null)
							System.out.println("Select a runway with 'select runway' before adding an obstacle.");
						else
							selectObstacle();
						break;
					case "direction":
						if(controller.getSelectedLogicalRunway() == null)
							System.out.println("Select a runway first with 'select runway' before choosing a direction.");
						else {
							selectDirection();
						}
						break;
					default:
						System.out.println("Invalid argument to command 'select (type)'\n : Valid types are; 'airport', 'runway', 'designator', 'obstacle'\n");
						break;
					}
			} else { 
				System.out.println("Invalid argument to command 'select (type)'\n : Valid types are; 'airport', 'runway', 'designator', 'obstacle'\n");
			}
			break;
		case "calculate":
			if (input.length == 2 && (input[1].equalsIgnoreCase("T") || input[1].equalsIgnoreCase("A"))) 
			{
				try {
					List<Integer> newValues = controller.calculate(false, null, input[1].equalsIgnoreCase("T"));
					System.out.println("TORA: " + newValues.get(0) + "m");
					System.out.println("TODA: " + newValues.get(1) + "m");
					System.out.println("ASDA: " + newValues.get(2) + "m");
					System.out.println("LDA: " + newValues.get(3) + "m");
					System.out.println("");
				} catch (InvalidDataException e) {
					System.out.println(e.getMessage());
				}
			} 
			else if(input.length == 3 && input[1].equalsIgnoreCase("-v") && (input[2].equalsIgnoreCase("T") || input[2].equalsIgnoreCase("A"))) {
				//verbose, show calculations
				try {
					Calculations.BreakdownWrapper breakdown = new Calculations.BreakdownWrapper();
					List<Integer> newValues = controller.calculate(true, breakdown, input[2].equalsIgnoreCase("T"));
					
					System.out.println("Breakdown\n---------\n" + (breakdown.breakdownStr == null ? "No redeclaration required." : breakdown.breakdownStr));
					System.out.println("");
					System.out.println("\nResults\n-------");
					System.out.println("TORA: " + newValues.get(0) + "m");
					System.out.println("TODA: " + newValues.get(1) + "m");
					System.out.println("ASDA: " + newValues.get(2) + "m");
					System.out.println("LDA: " + newValues.get(3) + "m");
					System.out.println("");
				} catch (InvalidDataException e) {
					System.out.println(e.getMessage());
				}
			} else { wrong_args(input); }
			break;
		case "status":
			print_status();
			break;
		case "breakdown":
			if (controller.model.calcBreakdown != null) {
				System.out.println(controller.model.calcBreakdown);
			}
			else {
				System.out.println("No calculations to print out.");
			}
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
			System.out.println("There are currently no airports registered to the system.\n");
		
		for(int i = 0; i < airports.size(); i++) {
			Airport airport = airports.get(i);
			String name = (airport.name == null || airport.name.isEmpty()) ? ("Airport " + i) : airport.name;
			System.out.println("[" + i + "] : " + name);
		}
	}
	
	private void list_runways(List<Runway> runways) {
		if(runways == null) {
			System.out.println("No airport is currently selected. Please select one with 'select airport'.\n");
			return;
		}
		if(runways.size() == 0)
			System.out.println("There are no runways registered to this airport.\n");
		
		for(int i = 0; i < runways.size(); i++) {
			Runway runway = runways.get(i);
			String name = (runway.designator == null || runway.designator.isEmpty()) ? ("Runway " + i) : runway.designator;
			System.out.println("[" + i + "] : " + name);
		}
	}
	
	private void list_thresholds(LogicalRunway lr) {
		if(lr == null)
			System.out.println("There are no designators configured for this runway. Add a new runway with 'add runway' to set up a new runway with designators.\n");

		System.out.println(lr.designator);
	}
	
	private void list_obstacles(List<Obstacle> obstacles) {
		if(obstacles.size() == 0)
			System.out.println("There are no obstacles registered to the system. Add some with 'add obstacle'\n");
		
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
		Integer x = readInt("Enter the obstacle's distance from the " + controller.getSelectedLogicalRunway().designator + " designator in meters.\n(Negative for before threshold, Positive for after threshold)\n");
		Integer y = readInt("Enter the obstacle's distance from the centerline in meters\n(Positive for north of centerline, Negative for south of centerline)\n");
		
		return new Point(x, y);
	}
	
	private void selectRunway() {
		List<Runway> runways = controller.getRunways();
		String[] designators = new String[runways.size()];
		for(int i = 0; i < runways.size(); i++)
			designators[i] = runways.get(i).designator;
			
		int ID = selectFromList("Select a runway", designators);
		if(controller.selectRunway(ID)) {
			LogicalRunway selectedLr = controller.getSelectedLogicalRunway();
			System.out.println(selectedLr.designator + " designator selected on " + selectedLr.runway.designator + ".\n");
		}
	}
	
	public void selectThreshold(Runway runway) {
		String prompt = "Select a logical runway / direction to use for " + runway.designator;
		int result = selectFromList(prompt, new String[] { runway.shortAngleLogicalRunway.designator, runway.longAngleLogicalRunway.designator });
		controller.selectThreshold(result); //TODO: not sure if this selects the correct logical runway
	}
	
	public void selectDirection() {
		String selectedLr = controller.getSelectedLogicalRunway().designator;
		int result = selectFromList("Select a direction in relation to the " + selectedLr + "logical runway.", new String[] { "Towards " + selectedLr, "Away from " + selectedLr });
		if(result == 0) {
			controller.setDirection(true);
			System.out.println("Direction set towards " + controller.getSelectedLogicalRunway().designator + " logical runway.");
		}
		else if(result == 1) {
			controller.setDirection(false);
			System.out.println("Direction set away from " + controller.getSelectedLogicalRunway().designator + " logical runway.");
		}
	}
	
	public void selectObstacle() {
		List<Obstacle> obs = controller.getObstacles();
		String[] obNames = new String[obs.size() + 1];
		for(int i = 0; i < obs.size(); i++)
			obNames[i] = obs.get(i).name;
		obNames[obs.size()] = "Remove obstacle";
		
			
		int ID = selectFromList("Select an obstacle", obNames);
		if(ID == obs.size()) {
			controller.clearObstacle();
			System.out.println("Runway is cleared of obstacles.");
		}
		else if(controller.selectObstacle(ID)) {
			Obstacle selectedObstacle = controller.getSelectedObstacle();
			LogicalRunway selectedLr = controller.getSelectedLogicalRunway();
			System.out.println("Added " + selectedObstacle.name + " to " + selectedLr.runway.designator + " " + Math.abs(selectedObstacle.distanceFromThreshold) + "m " + (selectedObstacle.distanceFromThreshold < 0 ? "before" : "after") + " " + selectedLr.designator + " threshold and " + Math.abs(selectedObstacle.distanceFromCenterline) + "m " + (selectedObstacle.distanceFromCenterline < 0 ? "south" : "north") + " of centerline.");
		}
		
	}
	
	private int selectFromList(String message, String[] listItems) {
		String prompt = message + ":\n";
		for(int i = 0; i < listItems.length; i++) {
			prompt += "[" + i + "] : " + listItems[i] + "\n";
		}
		return readInt(prompt, 0, listItems.length-1);
	}
	
	private void print_status() {
		Airport airport = controller.getSelectedAirport();
		LogicalRunway lr = controller.getSelectedLogicalRunway();
		Obstacle obstacle = controller.getSelectedObstacle();
		
		System.out.println("Airport: " + (airport == null ? "None selected" : airport.name));
		System.out.println("Runway: " + (lr == null ? "None selected" : lr.runway.designator));
		System.out.println("Designator: " + (lr == null ? "None selected" : lr.designator));
		
		System.out.println("\nObstacle\n--------");
		if(obstacle == null)
			System.out.println("None selected.");
		else {
			System.out.println("");
			System.out.println("Type: " + obstacle.name);
			System.out.println("Width: " + obstacle.width + "m");
			System.out.println("Height: " + obstacle.height + "m");
			System.out.println("Length: " + obstacle.length + "m");
			System.out.println("Distance from " + lr.designator + ": " + obstacle.distanceFromThreshold + "m");
			System.out.println("Distance from centerline: " + obstacle.distanceFromCenterline + "m");
			System.out.println("");
		}
		
		if(lr != null) {
			System.out.println("");
			System.out.println("\nOriginal Values");
			System.out.println("---------------");
			System.out.println("TORA: " + lr.tora + "m");
			System.out.println("TODA: " + lr.toda + "m");
			System.out.println("ASDA: " + lr.asda + "m");
			System.out.println("LDA: " + lr.lda + "m");
			System.out.println("Displaced Threshold: " + lr.displacedThreshold + "m");
			System.out.println("Clearway Length: " + lr.clearwayLength + "m");
			System.out.println("Stopway Length: " + lr.stopwayLength + "m");
			System.out.println("RESA: " + lr.getRESA() + "m");
			System.out.println("Blast Allowance: " + lr.getBlastAllowance() + "m");
			System.out.println("Strip End: " + lr.getStripEnd() + "m");
			System.out.println("");
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
