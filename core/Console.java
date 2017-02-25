package core;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Console 
{
	Scanner s;
	
	public Console() 
	{
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
		System.out.print("input: ");
		
		s = new Scanner(System.in);
		String input = s.nextLine();
		return input.split(" ");
	}
	
	/* Public input resolver
	 * returns quit_flag
	 */
	public boolean handle_input()
	{
		boolean quit_flag = false;
		String[] input = getInput();
		switch( input[0] ) 
		{
			case "help":
				System.out.println("Placeholder Help Page..."); /* PLACEHOLDER HELP DOCUMENTATION */
				break;
			case "quit":
				quit_flag = true;
				break;
			default:
				System.out.println("Invalid input. Use 'help' for a list of commands and uses.");
				break;
		}
		return quit_flag;
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
