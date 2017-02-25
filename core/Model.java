package core;

import java.util.ArrayList;
import java.util.List;

public class Model 
{
	private List<Updatable> views;

	public Model() 
	{
		this.views = new ArrayList<Updatable>();
	}
	
	public void registerView(Updatable view) {
		this.views.add(view);
	}
	
	//Updates all registered views
	//TODO: Change to reference to this so GUI can extract information they need.
	private void updateViews(String msg) {
		for(Updatable v : views)
			v.updateView(msg);
	}
}
