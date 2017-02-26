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
		model = new Model(view);
		
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
		while ( !view.handle_input() );
		quit();
	}
	
	public void getAirports() { model.getAirports(); }
	public void getRunways() { model.getRunways(); }
	public void getObjects() { model.getObjects(); }

	/* Safely closes application */
	private void quit() 
	{
		view.quit();
		view = null;
		model.quit();
		model = null;
	}

	public void calculate(boolean b) {}
	public void getStatus() {}

	public void selectObject(String name) {}
	public void selectAirport(String name) {}
	public void selectRunway(String name) {}
}
