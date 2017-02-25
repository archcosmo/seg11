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
				System.out.println("Placeholder Help Page..."); /* PLACEHOLDER HELP DOCUMENTATION */
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
		case "calculate":
			if ( input.length == 1 ) 
			{
				// Recalculate and show
			}
			else if (input[1] == "-n" )
			{
				// Show calculations no recalculate
			} else { wrong_args(input); }
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
