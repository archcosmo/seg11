package core;

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
	//XML Airport info
	//XML Object info
	//PUBLIC Selected runway (Default NULL)
	//PUBLIC selected airport (Default NULL)
	//current obst. (Default NULL)

	public Model(Console view) 
	{
		this.view = view;
		init();
	}

	/* IMPORTANT
	 * many of these will need more than a single argument
	 * you will need to add the arguments you need
	 * if a function does not exist (ie.. Calculation and possibly thresholds), feel free to add them
	 * I need this done so I can finalise the controller passthrough
	 */

	private void init() {
		this.airports = new ArrayList<Airport>();
	}
	
	public boolean airportXMLInfoExists() {
		return XMLParser.defaultXMLFileExists();
	}
	
	public boolean saveAirportInfoToFile() {
		try {
			XMLParser.saveAirportInfoToXML(airports.get(0));
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean loadAirportInfoFromFile() {
		try {
			airports.add(XMLParser.readAirportInfoFromXML());
			selectedAirport = airports.get(0);
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
		boolean inList = false;
		for (Airport a : airports) {
			if (a.name.equals(name)) {
				inList = true;
			}
		}
		if (!inList) {
			airports.add(new Airport(name));
			return true;
		}
		return false;
	}
	
	public boolean addAirport(Airport airport) {
		boolean inList = false;
		for(Airport a : airports) {
			if (a.name.equals((airport.name)))
					inList = true;
		}
		if(!inList) {
			airports.add(airport);
			return true;
		}
		return false;
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
	public boolean addRunway(String name)
	{
		if (selectedAirport != null) {
			boolean inList = false;
			for (Runway r : selectedAirport.runways) {
				if (r.name.equals(name)) {
					inList = true;
				}
			}
			if (!inList) {
				selectedAirport.runways.add(new Runway(name));
				return true;
			}
		}
		return false; 
	}

	/* delete runway
	 * return false if runway name nonexistant
	 * return false if airport not selected
	 */
	public boolean deleteRunway(String name)
	{
		if (selectedAirport != null) {
			for (Runway r : selectedAirport.runways) {
				if (r.name.equals(name)) {
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
	public boolean addObject(String name)
	{
		return false;
	}

	/* delete object
	 * return false if object does not exist
	 */
	public boolean deleteObject(String name)
	{
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
	public void getObjects() {  }

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
			return true;
		}
		catch(IndexOutOfBoundsException e) { return false; }
	}

	/* Takes string
	 * resolves string to object
	 * returns false if string != object name
	 */
	public boolean selectObject(int id) // Add distance values 
	{
		return false;
	}

	/* Removes current object reference
	 * Returns false if object reference is already null
	 */
	public boolean removeObject() 
	{
		return false;
	}

	/* Deallocates memory and stores changes before a system shutdown */
	public void quit() 
	{

	}
}
