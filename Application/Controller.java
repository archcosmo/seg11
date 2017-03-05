package Application;

import java.awt.EventQueue;
import java.util.List;

import Model.Airport;
import Model.Obstacle;
import Model.Runway;
import UI.Window;

public class Controller 
{
	List<Airport> airports;
	Airport selectedAirport;
	List<Obstacle> obstacles;
	Window UI;
	public final static String TITLE = "Runway Re-Declaration Calculator";
	public String AIRPORT_NAME = "Error: No airport selected";
	
	/* Initialise frame on swing worker thread */
	public static void main(String[] args)
	{
		new Controller();
	}
	
	public Controller() {
		
		/* TODO: Initialise XML */
		
		/* Start UI */
		EventQueue.invokeLater(() -> {
			UI = new Window(this);
			UI.setVisible(true);
        });
	}
	
	/* Run calculation and drawing, 
	 * - call updateUI to return info 
	 * - TODO: Data types might need changing, ie Runway as string/runway, same with objects
	 */
	public void run(String runway, String object, int objX, int objY) 
	{
		//TODO: UI.updateUI(null);
	}
	
	public void selectAirport(String airport)
	{
		AIRPORT_NAME = airport;
		//TODO: find airport, set runway list
		UI.loadRunningLayout();
	}
	
	public void newAirport(String airport)
	{
		//TODO: Add airport
		selectAirport(airport);
	}
	
	public List<Airport> getAirports()
	{
		return airports;
	}
	
	/* WARNING: Do not call before airport has been set! */
	public List<Runway> getRunways() 
	{
		return selectedAirport.getRunways();
	}
}
