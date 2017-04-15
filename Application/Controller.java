package Application;

import java.awt.EventQueue;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import Model.Airport;
import Model.Obstacle;
import Model.Runway;
import Model.XML;
import UI.Window;
import Model.Calculations;
import Model.Draw;
import Model.LogicalRunway;

public class Controller 
{
	List<Airport> airports;
	Airport selectedAirport;
	public Runway selectedRunway;
	public Obstacle selectedObstacle;
	List<Obstacle> obstacles;
	Window UI;
	public final static String TITLE = "Runway Re-Declaration Calculator";
	public String AIRPORT_NAME = "Error: No airport selected";
	
	public boolean lowAngleRunway = true;
	public boolean towardsSelectedLR = true;
	
	Draw draw = new Draw(this);
	
	private Calculations calculator;
	public ArrayList<Integer> recalculatedValues;
	String calcBreakdown;
	
	public Draw getDraw() { return draw; }
	
	/* Initialise frame on swing worker thread */
	public static void main(String[] args)	{	new Controller();	}
	
	public Controller() {
		
		/* Load XML info from files */
		loadObstacleInfoFromFile();
		loadAirportInfoFromFile();
		
		this.calculator = new Calculations();
		//selectedObstacle = obstacles.get(0);
		//this.draw = new Draw(this);
		
		/* Start UI */
		EventQueue.invokeLater(() -> {
			UI = new Window(this);
			UI.setVisible(true);
        });
	}
	
	/* Redisplays Airport Selection Panel */
	public void showAirportSelection() 
	{
		EventQueue.invokeLater(() -> {
			UI.loadAirportSelectionLayout();
			UI.setVisible(true);
        });
	}
	
	/* XML_LOAD Obstacles */
	public boolean loadObstacleInfoFromFile() {
		try {
			obstacles = XML.readObstacleInfoFromXML();
			return true;	
			
		} catch (IOException e) { 
			return false;
		} catch(SAXException | ParserConfigurationException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	 
	/* XML_LOAD Airports */
	public boolean loadAirportInfoFromFile() {
		try {
			airports = XML.readAirportInfoFromXML();
			return true;
			
		} catch (IOException | SAXException | ParserConfigurationException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void selectAirport(String airport)
	{
		AIRPORT_NAME = airport;
		for (Airport a: airports) {
			if (airport.equals(a.getName())) 
			{
				selectedAirport = a;
				break;
			}
		}
		System.err.println("Switching application to calculation");
		
		EventQueue.invokeLater(() -> {
			UI.loadRunningLayout();
			UI.setVisible(true);
			
			if (getRunways().size() >= 1) {
				selectedRunway = getRunways().get(0);
				recalculateValues();
			}
        });
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

	public void selectRunway(String runway) {
		for (Runway r: getRunways()) {
			if (runway.equals(r.getName())) 
			{
				selectedRunway = r;
				System.err.println("Runway Selected");
				break;
			}
		}
		recalculateValues();
	}

	public void setRunwayAngle(String selectedItem) 
	{
		lowAngleRunway = selectedItem.equals("Small Angle");
		recalculateValues();
	}
	
	public LogicalRunway getSelectedLogicalRunway() {
		return lowAngleRunway ? selectedRunway.lowAngle() : selectedRunway.highAngle();
	}
	
	private void recalculateValues() {
		if (selectedRunway != null) {
			recalculatedValues = calculator.calculateDistances(getSelectedLogicalRunway(), selectedObstacle, towardsSelectedLR);
			calcBreakdown = calculator.getLastCalculationBreakdown();
			System.err.println("Running Calculations");
			UI.getDATA().printStr(calcBreakdown);
			UI.draw();
			/* TODO: Re Draw Call */
		}
	}

	public void setTakeoffDirection(String selectedItem) 
	{
		towardsSelectedLR = selectedItem.equals("Towards Selected Threshold End");
		recalculateValues();
	}
}




