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
	
	/* Main program loop */
	private void loop()
	{
		boolean quit = false;
		while ( !quit )
		{
			
			quit = true; // Force exit application
		}
		quit();
	}
	
	/* Safely closes application */
	private void quit() {
		view.quit();
		view = null;
		
	}
	

}
