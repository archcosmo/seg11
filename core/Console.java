package core;

		/* TODO:
		 * list_objects
		 * printCalculations
		 * printAnswers
		 * printStatus
		 */


import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

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
		addAirport();
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
	
	public void addAirport() {
		Airport airport = configureAirport();
		if(airport != null)
			controller.addAirport(airport);
	}
	
	public void addRunway(Integer airportId) {
		Runway runway = configureRunway();
		if(runway != null)
			controller.addRunway(airportId, runway);
	}
	
//	public void addThreshold(Integer airportId, Integer runwayId) {
//		Threshold threshold = configureThreshold();
//		if(threshold != null)
//			controller.addThreshold(airportId, runwayId, threshold);
//	}
	
	private Airport configureAirport() {
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
			
			while(true) {
				String confirm = prompt("Add (another) runway to " + airportName + "?(y/n):");

				if(confirm.equalsIgnoreCase("y")) {
					Runway runway = configureRunway();
					if(runway != null)
						airport.addRunway(runway);
				}
				else if(confirm.equalsIgnoreCase("n")) {
					break;
				}
				else {
					displayMessage("Expected 'y' or 'n'");
					continue;
				}
			}
			return airport;
		}
	}
	
	private Runway configureRunway() {
		while(true) {
			String runwayName = prompt("Enter runway name:\n(Or enter to '!' to cancel)");
			if(runwayName.replaceAll("\\s+", "").equals("!")) {
				return null;
			}

			if(runwayName.replaceAll("\\s+", "").isEmpty()) {
				displayMessage("A runway name cannot be empty.");
				continue;
			}

			Runway runway = new Runway(runwayName, -1, -1);
			
			Integer angle = readInt("Enter an angle for the runway", 0, 359);
			Integer noRunways = readInt("Enter number of logical runways", 1, 3);
			
			Integer startClearway = readInt("Enter the length of the start clearway");
			Integer startStopway = readInt("Enter the length of the start stopway");
			Integer endClearway = readInt("Enter the length of the end clearway");
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
					Integer displacedThreshold = readInt("Enter Displaced Threshold value for " + designator);
					
					LogicalRunway lr = new LogicalRunway(String.format("%02d" + letter, logicalAngle), runway, runwayLength, (j == 0) ? endStopway : startStopway, (j == 0) ? endClearway : startClearway);

				}
			}
			
			return runway;
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
				System.out.println("** NT: type = airport(s), runway(s), object(s); (Use of plural where appropriate)"); 
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
				case "objects":
					controller.getObjects();
					break;
				default:
					System.out.println("Invalid argument to command 'list (type)'\n : Valid types are; 'airports', 'runways', 'objects'");
					break;
				}
			} else { wrong_args(input); }
			break;
		case "add":
			if ( input.length == 2 ) 
			{
				/* NEEDS COMPLETING WHEN ADD METHODS DONE */
			} else { wrong_args(input); }
			break;
		case "delete":
			if ( input.length == 3 && isInt(input[2])) 
			{
				int ID = Integer.parseInt(input[2]);
				switch ( input[1] )
				{
				case "airport":
					controller.deleteAirport(ID);
					break;
				case "runway":
					controller.deleteRunway(ID);
					break;
				case "object":
					controller.deleteObject(ID);
					break;
				default:
					System.out.println("Invalid argument to command 'delete (type) (id)'\n : Valid types are; 'airport', 'runway', 'object'");
					break;
				}
			} else { wrong_args(input); }
			break;
		case "select":
			if ( input.length == 3 && isInt(input[2])) 
			{
				int ID = Integer.parseInt(input[2]);
				switch ( input[1] )
				{
				case "airport":
					controller.selectAirport(ID);
					break;
				case "runway":
					controller.selectRunway(ID);
					break;
				case "object":
					controller.selectObject(ID);
					break;
				default:
					System.out.println("Invalid argument to command 'select (type) (id)'\n : Valid types are; 'airport', 'runway', 'object'");
					break;
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
			controller.getStatus();
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
	
	private void wrong_args(String[] input)
	{
		for ( int i = 0; i < input.length; i++) System.out.print(input[i] + " ");
		System.out.println(" : Invalid number of arguments");
	}
	
	/* Displays quit sign 
	 * halts program for 3 seconds to notify user
	 */
	public void quit()
	{
		printBar("Exiting Application");
		try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); } // Sleep 3 seconds
		s.close();
	}
}
