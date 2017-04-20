package Application;

import java.awt.EventQueue;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import Model.Airport;
import Model.Calculations;
import Model.ColourScheme;
import Model.Draw;
import Model.LogicalRunway;
import Model.Obstacle;
import Model.Runway;
import Model.XML;
import UI.Window;

public class Controller 
{
	List<Airport> airports;
	Airport selectedAirport;
	public Runway selectedRunway;
	public Obstacle selectedObstacle;
	List<Obstacle> obstacles;
	List<ColourScheme> colourSchemes;
	Window UI;	
	public final static String TITLE = "Runway Re-Declaration Calculator";
	public String AIRPORT_NAME = "Error: No airport selected";
	public final float MAX_ZOOM = 10.0F;
	public boolean lowAngleRunway = true;
	public boolean towardsSelectedLR = true;
	
	public float zoom = 1.0F;
	
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
		
		this.colourSchemes = new ArrayList<ColourScheme>();
		Collections.addAll(colourSchemes, ColourScheme.defaultThemes());
		
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
				selectedRunway = getRunways().get(0);
				break;
			}
		}
		UI.notify("Switching application to calculation");
		
		EventQueue.invokeLater(() -> {
			UI.loadRunningLayout();
			UI.setVisible(true);
			if (getRunways().size() >= 1) {
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
		
		addGenericRunway();
		selectedRunway = selectedAirport.getRunways().get(0);
		
		try {
			XML.saveAirportInfoToXML(selectedAirport);
		} catch (IOException e) {}
		
		notify("New Airport Added and Selected");
		UI.loadRunningLayout();
		UI.setVisible(true);
		return true;
	}
	
	public void addGenericRunway()
	{
		Runway newRunway = new Runway(240, 300, 60, 2000, 400);
		newRunway.setLogicalRunways(
				new LogicalRunway("00", newRunway, 100, 100, 100, 100), 
				new LogicalRunway("18", newRunway, 100, 100, 100, 100));
		selectedAirport.addRunway(newRunway);
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

	public void setRunwayAngle(boolean lowAngle)
	{
		lowAngleRunway = lowAngle;
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
			if (selectedObstacle == null) { calcBreakdown = ""; }
			UI.draw();
			notify("Printed Calculations Breakdown, Updated Diagrams");
		}
	}

	public void setTakeoffDirection(String selectedItem) 
	{
		towardsSelectedLR = selectedItem.startsWith("Towards");
		recalculateValues();
	}
	
	public void setColourScheme(ColourScheme scheme) {
		draw.setColourScheme(scheme);
		UI.draw();
	}

	public void notify(String string) 
	{
		UI.notify(string);
	}

	public List<Obstacle> getObstacles() 
	{
		return obstacles;
	}
	
	public List<ColourScheme> getColourSchemes() 
	{
		return colourSchemes;
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
	
	/* Halfway holder values for object position */
	int distanceFromThreshold = 0;
	int distanceFromCenterline = 0;
	
	public boolean selectObstacleXPos(String inp) 
	{
		int parsedInt = 0;
		
		try { 	parsedInt = Integer.parseInt(inp);	}
		catch (Exception e) 
		{ 
			notify("Input is not a number. Resetting Obstacle X Offset to 0"); 
			if (selectedObstacle != null) distanceFromCenterline = 0;
			setObstaclePos();
			return false;
		}
		
		if (selectedObstacle != null)
		{
			notify("Obstacle X Offset Selected : "+ parsedInt);
			distanceFromCenterline = parsedInt;
			setObstaclePos();
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
			if (selectedObstacle != null) distanceFromThreshold = 0; 
			setObstaclePos();
			return false; 
		}
		
		if (selectedObstacle != null)
		{
			notify("Obstacle Y Offset Selected : "+ parsedInt);
			distanceFromThreshold = parsedInt; 
			setObstaclePos();
			return true;
		}
		notify("Cannot select obstacle position while no obstacle is selected");
		return false;
	}
	
	private void setObstaclePos() 
	{
		selectedObstacle.setPosition(distanceFromThreshold, getSelectedLogicalRunway(), lowAngleRunway, lowAngleRunway,distanceFromCenterline);
		recalculateValues();
	}
	
	/*Sets the factor to zoom the view by*/
	public float setViewZoom(float zoom) throws Exception {
		float percent = zoom / 100.0F;
		float zoomFactor = 1.0F + (percent * (MAX_ZOOM - 1));
		
		if(!draw.setZoomFactor(zoomFactor))
			throw new Exception("Zoom Factor cannot be negative!");
		UI.draw();
		
		return zoomFactor;
	}
	
	/*Adjusts the panning offset for the top view by a given amount*/
	public void adjustTopPan(int panX, int panY) {
		draw.setTopPan(panX, panY);
		UI.draw();
	}
	
	/*Adjusts the panning offset for the side view by a given amount*/
	public void adjustSidePan(int panX, int panY) {
		draw.setSidePan(panX, panY);
		UI.draw();
	}

	public String getCalculations() 
	{
		return calcBreakdown;
	}
}




