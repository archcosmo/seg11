package UI;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
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
	SideViewPanel TOP;
	TopViewPanel SIDE;
	
	/* Constructor
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
		/* TODO: Remove this line (Testing) */
		loadRunningLayout(); //*/
	}

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
		/* Adding top bar panel */
		TOP_BAR = new TopBarPanel(CONTROLLER);
		add(TOP_BAR, BorderLayout.PAGE_START);
		
		/* Adding right hand (Selection) panel */
		SELECTION = new SelectionPanel(CONTROLLER);
		add(SELECTION, BorderLayout.EAST);
		
		/* Adding left side (Data) tabbed panels */
		JTabbedPane tabs = new JTabbedPane();
		DATA = new DataPanel();
		tabs.addTab("Data", DATA);
		TOP = new SideViewPanel();
		tabs.addTab("Top View", TOP);
		SIDE = new TopViewPanel();
		tabs.addTab("Side View", SIDE);
		add(tabs, BorderLayout.CENTER);
		//TODO: New tab: Add Runways + Objects
	}

	public void updateUI(BufferedImage topView, BufferedImage sideView, String[] Calculations, String[] Answers) 
	{
		//TODO: Update panels with information
	}
}
