package UI;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import Application.Controller;

@SuppressWarnings("serial")
public class Window extends JFrame 
{
	final static String TITLE = "Runway Re-Declaration Calculator";
	Controller CONTROLLER;
	
	TopBarPanel TOP_BAR;
	SelectionPanel SELECTION;
	DataPanel DATA;
	SideViewPanel SIDE;
	TopViewPanel TOP;
	/* TODO: Notifications Panel */
	
	/* Constor
	 * - Stores reference to controller for input handler references later
	 * - Runs initFrame
	 */
	public Window(Controller c) 
	{ 
		CONTROLLER = c;
		initFrame(); 
	}

	private void initFrame() 
	{
		setTitle(TITLE);
		setSize(1200, 800);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		loadAirportSelectionLayout();
	}
	
	public DataPanel getDATA() {return DATA;}
	public TopViewPanel getTOP() {return TOP;}
	public SideViewPanel getBOTTOM() {return SIDE;}

	/* Initial Screen on load up, 
	 * - gets user to choose or make a new airport 
	 */
	private void loadAirportSelectionLayout() 
	{
		AirportSelectPanel AIRPORT = new AirportSelectPanel(CONTROLLER);
		add(AIRPORT, BorderLayout.CENTER);
	}

	/* Main program running screen,
	 * - Loaded when airport selection successful
	 */
	public void loadRunningLayout() 
	{
		JPanel contentPanel = (JPanel) this.getContentPane();
		contentPanel.removeAll();
		
		/* Adding top bar panel */
		TOP_BAR = new TopBarPanel(CONTROLLER);
		add(TOP_BAR, BorderLayout.PAGE_START);
		
		/* Adding right hand (Selection) panel */
		SELECTION = new SelectionPanel(CONTROLLER);
		add(SELECTION, BorderLayout.EAST);
		
		/* Adding left side (Data) tabbed panels */
		JTabbedPane tabs = new JTabbedPane();
		DATA = new DataPanel();
		tabs.addTab("Calculations", DATA);
		SIDE = new SideViewPanel(CONTROLLER.getDraw());
		tabs.addTab("Top View", TOP);
		TOP = new TopViewPanel(CONTROLLER.getDraw());
		tabs.addTab("Side View", SIDE);
		add(tabs, BorderLayout.CENTER);
		//TODO: New tab: Add/edit/remove Runways + Objects
	}
}
