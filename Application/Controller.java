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
	
	public Controller() 
	{
		
		/* Load XML info from files */
		loadObstacleInfoFromFile();
		loadAirportInfoFromFile();
		
		this.calculator = new Calculations();
		
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
	public boolean loadObstacleInfoFromFile() 
	{
		try {
			obstacles = XML.readObstacleInfoFromXML();
			
			if (obstacles.isEmpty()) 
			{
				/* If no obstacles found, add a default obstacle */
				obstacles.add(new Obstacle("Generic Obstacle", 50, 400, 60));
				XML.saveObstacleInfoToXML(obstacles);
				//TODO: If file does not exist, make a new one
			}
			
			return true;	
			
		} catch (IOException e) { 
			return false;
		} catch(SAXException | ParserConfigurationException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	 
	/* XML_LOAD Airports */
	public boolean loadAirportInfoFromFile() 
	{
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
		UI.notify("Switching application to calculation");
		
		EventQueue.invokeLater(() -> {
			UI.loadRunningLayout();
			UI.setVisible(true);
			
			if (getRunways().size() >= 1) {
				selectedRunway = getRunways().get(0);
				recalculateValues();
			}
        });
	}
	
	
	public boolean newAirport(String airport)
	{
		if (airport.equals(" ") || airport.length() == 0) { return false; }
		//TODO: Add default runway to airport before saving
		selectedAirport = new Airport(airport);
		airports.add(selectedAirport);
		
		selectedRunway = new Runway(240, 300, 60, 2000, 400);
		selectedRunway.setLogicalRunways(
				new LogicalRunway("00", selectedRunway, 100, 100, 100, 100, 200), 
				new LogicalRunway("18", selectedRunway, 100, 100, 100, 100, 200));
		selectedAirport.addRunway(selectedRunway);
		
		try {
			XML.saveAirportInfoToXML(selectedAirport);
		} catch (IOException e) {}
		
		notify("New Airport Added and Selected");
		UI.loadRunningLayout();
		UI.setVisible(true);
		return true;
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

	public void selectRunway(String runway) 
	{
		for (Runway r: getRunways()) {
			if (runway.equals(r.getName())) 
			{
				selectedRunway = r;
				UI.notify("Runway Selected : " + runway);
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
	
	public LogicalRunway getSelectedLogicalRunway() 
	{
		return lowAngleRunway ? selectedRunway.lowAngle() : selectedRunway.highAngle();
	}
	
	private void recalculateValues() {
		if (selectedRunway != null) {
			recalculatedValues = calculator.calculateDistances(getSelectedLogicalRunway(), selectedObstacle, towardsSelectedLR);
			calcBreakdown = calculator.getLastCalculationBreakdown();
			UI.draw();
		}
	}

	public void setTakeoffDirection(String selectedItem) 
	{
		towardsSelectedLR = selectedItem.equals("Towards Selected Threshold End");
		recalculateValues();
	}

	public void notify(String string) 
	{
		UI.notify(string);
	}

	public List<Obstacle> getObstacles() 
	{
		return obstacles;
	}

	public void selectObstacle(String obj) 
	{
		if (obj.equals("None")) 
		{
			selectedObstacle = null;
		}
		else 
		{
			for (Obstacle o: obstacles) {
				if (obj.equals(o.getName())) 
				{
					selectedObstacle = o;
					break;
					
				}
			}
		}
		recalculateValues();
	}

	public boolean selectObstacleXPos(String inp) 
	{
		int parsedInt = 0;
		
		try { 	parsedInt = Integer.parseInt(inp);	}
		catch (Exception e) 
		{ 
			notify("Input is not a number. Resetting Obstacle X Offset to 0"); 
			if (selectedObstacle != null) selectedObstacle.setDistCenter(0);
			recalculateValues();
			return false;
		}
		
		if (selectedObstacle != null)
		{
			notify("Obstacle X Offset Selected : "+ parsedInt);
			selectedObstacle.setDistCenter(parsedInt); 
			recalculateValues();
			return true;
		}
		notify("Cannot select obstacle position while no obstacle is selected");
		return false;
	}
	
	public boolean selectObstacleYPos(String inp) 
	{
		int parsedInt = 0;
		
		try { 	parsedInt = Integer.parseInt(inp);	}
		catch (Exception e) 
		{ 
			notify("Input is not a number. Resetting Obstacle Y Offset to 0"); 
			if (selectedObstacle != null) selectedObstacle.setDistLowEnd(0); 
			recalculateValues();
			return false; 
		}
		
		if (selectedObstacle != null)
		{
			notify("Obstacle Y Offset Selected : "+ parsedInt);
			selectedObstacle.setDistLowEnd(parsedInt); 
			recalculateValues();
			return true;
		}
		notify("Cannot select obstacle position while no obstacle is selected");
		return false;
	}

	public String getCalculations() 
	{
		return calcBreakdown;
	}
}




