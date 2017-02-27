package core;

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
		
		controller.getAirports();
		controller.selectAirport("1"); //Parse this shit
		
		System.out.println("Use 'help' to get started.");
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
	
	private boolean isNumber() 
	{
		return false;
	}
	
	public void printAnswers()
	{
		
	}
	
	public void printStatus()
	{
		
	}
	
	/* Gets user input */
	private String[] getInput()
	{
		System.out.print("$ : ");
		
		s = new Scanner(System.in);
		String input = s.nextLine();
		return input.split(" ");
	}
	
	public void printList(String[] list)
	{
		
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
				System.out.println("* select (type) (name)");
				System.out.println("** NT: select object null   -- Clears object");
				System.out.println("* add (type) (name)");
				System.out.println("* delete (type) (name)"); 
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
					controller.getAirports();
					break;
				case "runways":
					controller.getRunways();
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
			if ( input.length == 3 ) 
			{
				switch ( input[1] )
				{
				case "airport":
					controller.getAirports();
					break;
				case "runway":
					controller.getRunways();
					break;
				case "object":
					controller.getObjects();
					break;
				default:
					System.out.println("Invalid argument to command 'delete (type) (id)'\n : Valid types are; 'airport', 'runway', 'object'");
					break;
				}
			} else { wrong_args(input); }
			break;
		case "select":
			if ( input.length == 3 ) 
			{
				switch ( input[1] )
				{
				case "airport":
					controller.selectAirport(input[2]);
					break;
				case "runway":
					controller.selectRunway(input[2]);
					break;
				case "object":
					controller.selectObject(input[2]);
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
