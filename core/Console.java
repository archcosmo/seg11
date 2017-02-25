package core;

import java.util.concurrent.TimeUnit;

public class Console 
{
	
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
	
	/* Gets user input
	 * parses input
	 */
	public String getInput()
	{
		return null;	
	}
	
	/* Displays quit sign 
	 * halts program for 3 seconds to notify user
	 */
	public void quit()
	{
		printBar("Exiting Application");
		try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); } // Delay 3 seconds
	}

}
