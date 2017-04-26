package UI;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import Application.Controller;

@SuppressWarnings("serial")
public class SideBarPanel extends JPanel {
	
	OrigValPanel ORIG; 
	RecalcValPanel RECALC;
	DataPanel DATA;
	SelectionPanel SELECTION;
	
	public SelectionPanel getSELECTION() { return SELECTION; }
	
	public SideBarPanel(Controller CONTROLLER, Window WINDOW) {
		init(CONTROLLER, WINDOW);
	}
	
	public void init(Controller CONTROLLER, Window WINDOW) {
		setLayout(new BorderLayout());
		
		ORIG = new OrigValPanel(CONTROLLER);
		RECALC = new RecalcValPanel(CONTROLLER);
		DATA = new DataPanel(CONTROLLER);
		
		addComponents(CONTROLLER, WINDOW);
	}
	
	public void addComponents(Controller CONTROLLER, Window WINDOW) {
		SELECTION = new SelectionPanel(CONTROLLER);
		add(SELECTION, BorderLayout.NORTH);
		
		
		JTabbedPane valueTabs = new JTabbedPane();
		valueTabs.addTab("Original Values", ORIG);
		valueTabs.addTab("Recalculated Values", RECALC);
		valueTabs.addTab("Calculation Breakdown", DATA);
		valueTabs.addTab("Diagram Preferences", new DrawPreferencesFrame(WINDOW, CONTROLLER));
		add(valueTabs, BorderLayout.CENTER);
	}
}
