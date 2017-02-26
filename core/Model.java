package core;

public class Model 
{
	Console view;
	//XML Airport info
	//XML Object info
	//PUBLIC Selected runway (Default NULL)
	//PUBLIC selected airport (Default NULL)
	//current obst. (Default NULL)

	public Model(Console view) 
	{
		this.view = view;
	}
	
	/* IMPORTANT
	 * many of these will need more than a single argument
	 * you will need to add the arguments you need
	 * if a function does not exist (ie.. Calculation and possibly thresholds), feel free to add them
	 * I need this done so I can finalise the controller passthrough
	 */
	
	/* Add airport
	 * return false if airport name taken
	 */
	public boolean addAirport(String name) 
	{
		return false;
	}
	
	/* delete airport
	 * return false if name does not exist
	 */
	public boolean deleteAirport(String name)
	{
		return false;
	}
	
	/* add runway
	 * return false if runway name taken
	 * return false if airport not selected
	 */
	public boolean addRunway(String name)
	{
		return false;
	}
	
	/* delete runway
	 * return false if runway name nonexistant
	 * return false if airport not selected
	 */
	public boolean deleteRunway(String name)
	{
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
	
	//Passthroughs, calls *printList()* in view, passing ordered array of String[]
	public void getAirports() {  }
	public void getRunways() {  }
	public void getObjects() {  }
	
	/* Takes string
	 * resolves string to object
	 * returns false if string != object name
	 */
	public boolean selectAirport(int id) 
	{
		return false;
	}
	
	/* Takes string
	 * resolves string to object
	 * returns false if string != object name
	 * returns false if an airport is selected
	 */
	public boolean selectRunway(int id) 
	{
		return false;
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
