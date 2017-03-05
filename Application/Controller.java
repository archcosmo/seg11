package Application;

import java.awt.EventQueue;

import UI.Window;

public class Controller 
{
	/* Reference to each model component */
	/* Current Program status */
	Window view;
	public final static String TITLE = "Runway Re-Declaration Calculator";
	
	/* Initialise frame on swing worker thread */
	public static void main(String[] args)
	{
		new Controller();
	}
	
	public Controller() {
		EventQueue.invokeLater(() -> {
			view = new Window(this);
			view.setVisible(true);
        });
	}

	/*public ActionListener exitButtonListener() {
		System.exit(0);
		return new ActionListener() {
			@Override
			public void actionPerformed (ActionEvent e) {
				System.exit(0);
			}
		};
	}*/
}
