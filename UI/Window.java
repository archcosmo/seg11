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
	public void loadAirportSelectionLayout() 
	{
		JPanel contentPanel = (JPanel) this.getContentPane();
		contentPanel.removeAll();
		
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
		SIDE = new SideViewPanel(CONTROLLER.getDraw());
		TOP = new TopViewPanel(CONTROLLER.getDraw());
		DATA = new DataPanel();
		tabs.addTab("Top View", TOP);
		tabs.addTab("Side View", SIDE);
		tabs.addTab("Calculations", DATA);
		add(tabs, BorderLayout.CENTER);
		//TODO: New tab: Add/edit/remove Runways + Objects
	}
	
	public void draw()
	{
		SIDE.repaint();
		TOP.repaint();
	}
}
