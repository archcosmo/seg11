package core;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

@SuppressWarnings("serial")
public class DiagramFrame extends JFrame {
	
	static final String TITLE = "Runway Re-Declaration Runway View";
	Controller CONTROLLER;
	
	SideViewPanel SIDE;
	TopViewPanel TOP;
	
	Draw drawModule;
	
	public DiagramFrame(Controller c, Draw drawModule) 
	{
		this.drawModule = drawModule;
		CONTROLLER = c;
		initFrame();
	}
	
	public void updateUI(BufferedImage topView, BufferedImage sideView)
	{
		SIDE.updateUI(sideView);
		TOP.updateUI(topView);
	}
	
	private void initFrame() 
	{
		setTitle(TITLE);
		setSize(800, 800);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		loadLayout();
	}

	private void loadLayout() 
	{
		/* Adding left side (Data) tabbed panels */
		JTabbedPane tabs = new JTabbedPane();
		TOP = new TopViewPanel(drawModule);
		tabs.addTab("Top View", TOP);
		SIDE = new SideViewPanel();
		tabs.addTab("Side View", SIDE);
		add(tabs, BorderLayout.CENTER);
	}
}
