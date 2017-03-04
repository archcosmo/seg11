package UI;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import Application.Controller;

@SuppressWarnings("serial")
public class Window extends JFrame 
{
	String TITLE = "Runway Re-Declaration Calculator";
	Controller CONTROLLER;
	JPanel PANE;
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
		addComponents();
	}

	private void addComponents() 
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
	}
}
