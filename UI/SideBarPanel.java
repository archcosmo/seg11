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
	
	public SideBarPanel(Controller CONTROLLER) {
		init(CONTROLLER);
	}
	
	public void init(Controller CONTROLLER) {
		setLayout(new BorderLayout());
		
		ORIG = new OrigValPanel(CONTROLLER);
		RECALC = new RecalcValPanel(CONTROLLER);
		DATA = new DataPanel(CONTROLLER);
		
		addComponents(CONTROLLER);
	}
	
	public void addComponents(Controller CONTROLLER) {
		SELECTION = new SelectionPanel(CONTROLLER);
		add(SELECTION, BorderLayout.NORTH);
		
		JTabbedPane valueTabs = new JTabbedPane();
		valueTabs.addTab("Original Values", ORIG);
		valueTabs.addTab("Recalculated Values", RECALC);
		valueTabs.addTab("Calculation Breakdown", DATA);
		add(valueTabs, BorderLayout.CENTER);
	}
}
