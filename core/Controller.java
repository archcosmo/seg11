package core;

public class Controller 
{
	
	Console view;
	Model model;
	
	static public void main(String[] args) { new Controller(); } /* Initialise program */
	
	public Controller() 
	{
		/* Initialise state */
		view = new Console();
		model = new Model();
		
		//Ask model to get airport, returns NULL if none
		//Ask model for run-ways
		loop();
	}
	
	/* Main program loop
	 * Handles input for all top-level commands
	 */
	private void loop()
	{
		String[] input;
		boolean quit_flag = false;
		while ( !quit_flag )
		{
			/* Handle Input */
			input = view.getInput();
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
		}
		quit();
	}

	/* Safely closes application */
	private void quit() {
		view.quit();
		view = null;
		
		//Saves any persistent data and halts
	}
	

}
