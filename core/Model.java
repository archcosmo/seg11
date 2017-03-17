package core;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class Model 
{
	Console view;
	List<Airport> airports;
	Airport selectedAirport;
	Runway selectedRunway;
	//LogicalRunway selectedLogicalRunway;
	boolean highAngleLRSelected;
	boolean towardsSelectedLR;
	Obstacle selectedObstacle;
	List<Obstacle> obstacles;
	Draw draw;
	
	//Calculations result
	private Calculations calculator;
	ArrayList<Integer> recalculatedValues;
	String calcBreakdown;

	

	public Model(Console view) 
	{
		this.view = view;
		init();
	}

	private void init() {
		this.airports = new ArrayList<Airport>();
		this.obstacles = new ArrayList<Obstacle>();
		this.highAngleLRSelected = true;
		this.towardsSelectedLR = true;
		this.calculator = new Calculations();
		this.draw = new Draw(this);
	}
	
	private void recalculateValues() {
		recalculatedValues = calculator.calculateDistances(getSelectedLogicalRunway(), selectedObstacle, towardsSelectedLR);
		calcBreakdown = calculator.getLastCalculationBreakdown();
	}
	
	public boolean saveObstacleInfoToFile() {
		try {
			XMLParser.saveObstacleInfoToXML(obstacles);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean loadObstacleInfoFromFile() {
		try {
			List<Obstacle> loadedObstacles = XMLParser.readObstacleInfoFromXML();
			
			if(loadedObstacles.size() == 0)
				return false;
			
			for(Obstacle obstacle : loadedObstacles)
				obstacles.add(obstacle);
			return true;
		} catch (IOException e) {
			return false;
		} catch(SAXException | ParserConfigurationException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	 
	public boolean airportXMLInfoExists() {
		return XMLParser.defaultXMLFileExists();
	}
	
	public boolean saveAirportInfoToFile() {
		try {
			for(Airport airport : airports)
				XMLParser.saveAirportInfoToXML(airport);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean loadAirportInfoFromFile() {
		try {
			List<Airport> loadedAirports = XMLParser.readAirportInfoFromXML();
			
			if(loadedAirports.size() == 0)
				return false;
			
			for(Airport airport : loadedAirports)
				airports.add(airport);
			return true;
		} catch (IOException | SAXException | ParserConfigurationException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/* Add airportindex
	 * return false if airport name taken
	 */
	public boolean addAirport(String name) 
	{
		for(Airport a : airports) {
			if (a.name.equals(name))
					return false;
		}
		airports.add(new Airport(name));
		return true;
	}
	
	public boolean addAirport(Airport airport) {
		for(Airport a : airports) {
			if (a.name.equals((airport.name)))
					return false;
		}
		airports.add(airport);
		return true;
	}

	/* delete airport
	 * return false if name does not exist
	 */
	public boolean deleteAirport(String name)
	{
		for (Airport a : airports) {
			if (a.name.equals(name)) {
				airports.remove(a);
				return true;
			}
		}
		return false;
	}

	/* add runway
	 * return false if runway name taken
	 * return false if airport not selected
	 */
	public boolean addRunway(String name, int resa, int blastAllowance, int stripEnd, int length, int width)
	{
		if (selectedAirport != null) {
			for(Runway r : selectedAirport.runways) {
				if (r.designator.equals((name)))
					return false;
			}
			selectedAirport.runways.add(new Runway(resa, blastAllowance, stripEnd, length, width));
			return true;
		}
		return false;
	}

	/* delete runway
	 * return false if runway name nonexistant
	 * return false if airport not selected
	 */
	public boolean deleteRunway(String designator)
	{
		if (selectedAirport != null) {
			for (Runway r : selectedAirport.runways) {
				if (r.designator.equals(designator)) {
					airports.remove(r);
					return true;
				}
			}
		}
		return false;
	}

	/* Add object
	 * return false if airport name taken
	 */
	public boolean addObstacle(Obstacle obstacle)
	{
		for (Obstacle o : obstacles) {
			if (o.name.equals(obstacle.name)) {
				return false;
			}
		}

		obstacles.add(obstacle);
		return true;
	}

	/* delete object
	 * return false if object does not exist
	 */
	public boolean deleteObject(String name)
	{
		for (Obstacle o : obstacles) {
			if (o.name.equals(name)) {
				obstacles.remove(o);
				return true;
			}
		}
		return false;
	}

	public List<Airport> getAirports() { 
		return airports;
	}
	public List<Runway> getRunways() {
		if(selectedAirport == null)
			return null;
		else
			return selectedAirport.runways;
	}
	public List<Obstacle> getObjects() { 
		return obstacles;
	}

	/* Takes string
	 * resolves string to object
	 * returns false if string != object name
	 */
	public boolean selectAirport(int id) 
	{
		try {
			this.selectedAirport = airports.get(id);
			return true;
		}
		catch(IndexOutOfBoundsException e) { return false; }
	}

	/* Takes string
	 * resolves string to object
	 * returns false if string != object name
	 * returns false if an airport is selected
	 */
	public boolean selectRunway(int id) 
	{
		try {
			this.selectedRunway = selectedAirport.runways.get(id);
			this.recalculateValues();
			return true;
		}
		catch(IndexOutOfBoundsException e) { return false; }
	}
	
	public void selectDirection(boolean towards) {
		int displacementX = (this.towardsSelectedLR && !towards) ? this.selectedObstacle.length : 
							(!this.towardsSelectedLR && towards) ? -this.selectedObstacle.length : 0;
		this.towardsSelectedLR = towards;
		this.selectedObstacle.setPosition(this.selectedObstacle.distanceFromThreshold + displacementX, this.getSelectedLogicalRunway(), !highAngleLRSelected, towardsSelectedLR, this.selectedObstacle.distanceFromCenterline);
		this.recalculateValues();
	}
	
	public LogicalRunway getSelectedLogicalRunway() {
		if(selectedRunway == null)
			return null;
		return highAngleLRSelected ? selectedRunway.longAngleLogicalRunway : selectedRunway.shortAngleLogicalRunway;
	}
	
	public boolean selectThreshold(int id) {
		try{
			if (id == 0) {
				this.highAngleLRSelected = false;
			} else if(id == 1) {
				this.highAngleLRSelected = true;
			} else { throw new IndexOutOfBoundsException("Invalid designator selection."); }
			this.recalculateValues();
			return true;
		}
		catch(IndexOutOfBoundsException e) { return false; }
	}

	/* Takes string
	 * resolves string to object
	 * returns false if string != object name
	 */
	public boolean selectObstacle(int id, int xPos, int yPos) // Add distance values 
	{
		try {
			this.selectedObstacle = obstacles.get(id);
			this.selectedObstacle.setPosition(xPos, this.getSelectedLogicalRunway(), !highAngleLRSelected, towardsSelectedLR, yPos);
			this.recalculateValues();
			return true;
		} catch(IndexOutOfBoundsException e) {
			return false;
		}
	}
	
	public boolean clearObstacle() {
		this.selectedObstacle = null;
		this.recalculateValues();
		return true;
	}

	/* Removes current object reference
	 * Returns false if object reference is already null
	 */
	public boolean removeObject() 
	{
		return false;
	}
	
	public BufferedImage getTopView() 
	{
		BufferedImage i = new BufferedImage(600, 600, BufferedImage.TYPE_4BYTE_ABGR);
		draw.drawTopView(i.createGraphics(), 600, 600);
		return i;
	}

	public BufferedImage getSideView() 
	{
		BufferedImage i = new BufferedImage(600, 600, BufferedImage.TYPE_4BYTE_ABGR);
		draw.drawSideView(i.createGraphics(), 600, 600);
		return i;
	}
}
