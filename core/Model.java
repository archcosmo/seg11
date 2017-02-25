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
	
	/* Add airport
	 * return false if airport name taken
	 */
	public boolean addAirport(String name) 
	{
		return false;
	}
	
	public boolean deleteAirport(String name)
	{
		return false;
	}
	
	public boolean addRunway(String name)
	{
		return false;
	}
	
	public boolean deleteRunway(String name)
	{
		return false;
	}
	
	public boolean addObject(String name)
	{
		return false;
	}
	
	public boolean deleteObject(String name)
	{
		return false;
	}
	
	//Passthroughs, calls printList in view, passing ordered array of String[]
	public void getAirports() {  }
	public void getRunways() {  }
	public void getObjects() {  }
	
	public boolean selectAirport(int id) 
	{
		return false;
	}
	
	public boolean selectRunway(int id) 
	{
		return false;
	}
	
	public boolean selectObject(int id) // Add distance values 
	{
		return false;
	}
	
	public boolean removeObject() {
		return false;
	}

	public void quit() {
		// TODO Auto-generated method stub
		
	}
}
