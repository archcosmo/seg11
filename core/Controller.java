package core;

public class Controller 
{
	Console view;
	Model model;
	
	static public void main(String[] args) { new Controller(); } /* Initialise program */
	
	public Controller() 
	{
		/* Initialise state */
		view = new Console(this);
		model = new Model();
		
		model.registerView(view);
		
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
		/* Loop user input
		 * returns true when flagging program exit
		 */
		while ( !view.handle_input() );
		quit();
	}

	/* Safely closes application */
	private void quit() {
		view.quit();
		view = null;
		
		//Saves any persistent data and halts
	}
}
