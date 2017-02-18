package core;

import javafx.application.Application;

public class Controller {
	
	GUI view;

	public static void main(String[] args) { new Controller(args); } /* Starts application */
	
	public Controller(String[] args) {
		Application.launch(GUI.class, args);
	}

}
