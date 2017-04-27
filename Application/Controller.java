package Application;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
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
	public Airport selectedAirport;
	public Runway selectedRunway;
	public Obstacle selectedObstacle;
	public List<Obstacle> obstacles;
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
	String calcBreakdown = "";
	String calcValues = "";
	String originalValues = "";
	
	public Draw getDraw() { return draw; }
	
	/* Initialise frame on swing worker thread */
	public static void main(String[] args)	{	new Controller();	}
	
	public Controller() 
	{
		
		/* Load XML info from files */
		loadObstacleInfoFromFile();
		loadAirportInfoFromFile();
		
		if (obstacles == null || obstacles.isEmpty()) addGenericObstacle();
		
		this.colourSchemes = new ArrayList<ColourScheme>();
		Collections.addAll(colourSchemes, ColourScheme.defaultThemes());
		
		this.calculator = new Calculations();
		
		/* Start UI */
		EventQueue.invokeLater(() -> {
			UI = new Window(this);
			UI.setVisible(true);
        });
	}
	
	private void addGenericObstacle() 
	{
		obstacles = new ArrayList<Obstacle>();
		obstacles.add(new Obstacle("Default Obst.", 20, 100, 40));
		try {
			XML.saveObstacleInfoToXML(obstacles);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		
		selectedAirport = new Airport(airport);
		airports.add(selectedAirport);
		
		addGenericRunway();
		selectedRunway = selectedAirport.getRunways().get(0);
		
		try {
			XML.saveAirportInfoToXML(selectedAirport);
		} catch (IOException e) {}
		
		notify("New Airport Added and Selected");
		AIRPORT_NAME = airport;
		UI.loadRunningLayout();
		UI.setVisible(true);
		return true;
	}
	
	public void addGenericRunway()
	{
		Runway newRunway = new Runway(240, 300, 60, 3000, 250);
		newRunway.setLogicalRunways(
				new LogicalRunway("00", newRunway, 3000, 3500, 3300, 2900),
				new LogicalRunway("18", newRunway, 3000, 3700, 3400, 3000));
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
			calcValues = calculator.getLastCalculationValues();
			if (selectedObstacle == null) { calcBreakdown = ""; }
			
			LogicalRunway temp = getSelectedLogicalRunway();
			originalValues = 
				"<center>Original Values:</center> <br> <br>" +
				"<center>TORA = " + temp.tora + "<br></center>" +
				"<center>TODA = " + temp.toda + "<br></center>" +
				"<center>ASDA = " + temp.tora + "<br></center>" +
				"<center>LDA  = " + temp.lda + "<br></center>";
			
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
		//Trigger value tabs to update
		UI.getSIDE_BAR().repaint();
	}
	
	public String getCalculations() 
	{
		return calcBreakdown;
	}
	
	//Exports the Graphical Views to files in the given directory
	public void exportGraphs(File dir) throws Exception {
		if(!dir.isDirectory())
			throw new Exception(dir.getName() + " is not a directory!");
		
		Dimension topSize = UI.getTOP().getSize();
		Dimension sideSize = UI.getSIDE().getSize();
		
		BufferedImage topImage = new BufferedImage(topSize.width, topSize.height, BufferedImage.TYPE_INT_RGB);
		BufferedImage sideImage = new BufferedImage(sideSize.width, sideSize.height, BufferedImage.TYPE_INT_RGB);
		
		this.draw.drawTopView(topImage.createGraphics(), topSize.width, topSize.height);
		this.draw.drawSideView(sideImage.createGraphics(), sideSize.width, sideSize.height);
		
		long timeStamp = (new Date()).getTime();
		File topFile = new File(dir, timeStamp + "-topView.jpg");
		File sideFile = new File(dir, timeStamp + "-sideView.jpg");
		
		if(topFile.exists()) topFile.delete();
		if(sideFile.exists()) sideFile.delete();
		
		topFile.createNewFile();
		sideFile.createNewFile();
		
		ImageIO.write(topImage, "jpg", topFile);
		ImageIO.write(sideImage, "jpg", sideFile);
		
		UI.notify("Exported top view to " + topFile.getAbsolutePath());
		UI.notify("Exported side view to " + sideFile.getAbsolutePath());
	}
	
	/////////////////
	//DRAW CONTROLS//
	/////////////////
	
	/*Sets the factor to zoom the view by*/
	public float setViewZoom(float zoom) throws Exception {
		float percent = zoom / 100.0F;
		float zoomFactor = 1.0F + (percent * (MAX_ZOOM - 1));
		
		if(!draw.setZoomFactor(zoomFactor))
			throw new Exception("Zoom Factor cannot be negative!");
		UI.draw();
		
		return zoomFactor;
	}
	
	/*Sets the rotation of the top view in degrees*/
	public void setViewRotation(int rotdeg) {
		draw.setRotation(rotdeg);
		UI.draw();
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
	
	public void setDrawPreference(String pref, boolean val) throws Exception {
		if(!draw.setPreference(pref, val))
			throw new Exception(pref + " is not a valid draw property!");
		UI.draw();
	}
	
	//Tracks mouse position, so legend can be made invisible
	public void setTopMousePos(int x, int y) {
		draw.setTopMousePos(x,y);
		UI.draw();
	}
	
	public void setSideMousePos(int x, int y) {
		draw.setSideMousePos(x,y);
		UI.draw();
	}
	

	public void updateCombo(Obstacle o) {
		UI.getSELECTION().updateObstacles(o);
	}
	public void updateCombo(Runway r) {
		UI.getSELECTION().updateRunways(r);
	}
	public void updateRunways() {
		UI.getSELECTION().updateRunways();
	}

	public void uiDraw() {
		UI.draw();
	}

	public String getCalculationValues() 
	{
		return calcValues;
	}

	public String getOriginalValues() 
	{
		return originalValues;
	}
}




