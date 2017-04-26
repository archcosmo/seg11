package UI;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import Application.Controller;

@SuppressWarnings("serial")
public class Window extends JFrame 
{
	final static String TITLE = "Runway Re-Declaration Calculator";
	Controller CONTROLLER;
	
	TopBarPanel TOP_BAR;
	SideBarPanel SIDE_BAR;
	DataPanel DATA;
	SideViewPanel SIDE;
	TopViewPanel TOP;
	NotificationsPanel NOTIFICATION;
	
	JTabbedPane tabs = new JTabbedPane();
	
	/* Constor
	 * - Stores reference to controller for input handler references later
	 * - Runs initFrame
	 */
	public Window(Controller c) 
	{ 
		NOTIFICATION = new NotificationsPanel();
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
	public SideViewPanel getSIDE() {return SIDE;}
	public SelectionPanel getSELECTION() {return SIDE_BAR.SELECTION;}

	/* Initial Screen on load up, 
	 * - gets user to choose or make a new airport 
	 */
	public void loadAirportSelectionLayout() 
	{
		JPanel contentPanel = (JPanel) this.getContentPane();
		contentPanel.removeAll();
		
		contentPanel.setLayout(new BorderLayout());
		AirportSelectPanel AIRPORT = new AirportSelectPanel(CONTROLLER);
		contentPanel.add(AIRPORT, BorderLayout.CENTER);
		contentPanel.add(NOTIFICATION, BorderLayout.SOUTH);
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

		contentPanel.add(TOP_BAR, BorderLayout.PAGE_START); //TODO: Doing this fucks up the top of the tabbed pane

		
		/* Adding right hand (Selection) panel */
		SIDE_BAR = new SideBarPanel(CONTROLLER);
		contentPanel.add(SIDE_BAR, BorderLayout.EAST);
		
		JPanel tabsExportWrapper = new JPanel();
		tabsExportWrapper.setLayout(new BorderLayout());
		
		JPanel exportWrapper = new JPanel();
		exportWrapper.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = gbc.weighty = 1.0;
		gbc.insets.top = 5;
		gbc.anchor = GridBagConstraints.EAST;
		exportWrapper.add(new ExportButton(CONTROLLER), gbc);
		tabsExportWrapper.add(exportWrapper, BorderLayout.NORTH);
		
		//Reduce border so view header looks better
		UIManager.getDefaults().put("TabbedPane.contentBorderInsets", new Insets(2,1,1,1));
		
		/* Adding left side (Data) tabbed panels */
		tabs = new JTabbedPane();
		
		SIDE = new SideViewPanel(CONTROLLER.getDraw(), CONTROLLER);
		TOP = new TopViewPanel(CONTROLLER.getDraw(), CONTROLLER);
		DATA = new DataPanel(CONTROLLER);

		tabs.addTab("Top View", TOP);
		tabs.addTab("Side View", SIDE);
		
		tabsExportWrapper.add(tabs, BorderLayout.CENTER);
	
		contentPanel.add(tabsExportWrapper, BorderLayout.CENTER);
		
		contentPanel.add(NOTIFICATION, BorderLayout.SOUTH);
		
		//TODO Add a button to bring up this frame
		new DrawPreferencesFrame(this, CONTROLLER);
		
		draw();
	}
	
	public void draw()
	{
		SIDE.repaint();
		TOP.repaint();
		DATA.repaint();

	}
	
	public void notify(String s)
	{
		NOTIFICATION.notify(s);
	}
}
