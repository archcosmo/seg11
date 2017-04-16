package UI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Application.Controller;
import Model.Obstacle;
import Model.Runway;

@SuppressWarnings("serial")
public class SelectionPanel extends JPanel
{
	Controller CONTROLLER;
	JTextField xPosInput;
	JTextField yPosInput;
	
	public SelectionPanel(Controller c) 
	{
		CONTROLLER = c;
		initPanel();
	}

	private void initPanel() 
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setPreferredSize(new Dimension(300, 0));
		addComponents();
	}

	/* Create menu */
	private void addComponents() 
	{
		/* Vertical Spacer */
		add(Box.createRigidArea(new Dimension(0, 50)));
		
		/* Runway Selection and Dropdown Menu : No Handler yet */
		MenuItem runwaySelection = new MenuItem("Runway : ");
		JComboBox<String> runwayDropdown = new JComboBox<String>();
		for (Runway r: CONTROLLER.getRunways()) 
		{
			runwayDropdown.addItem(r.getName());
		}
		runwayDropdown.addActionListener( new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				CONTROLLER.selectRunway((String) runwayDropdown.getSelectedItem());
		}});
		runwaySelection.addComponent(runwayDropdown);
		add(runwaySelection);
		
		/* Threshold selector */
		MenuItem angleSelection = new MenuItem("Threshold Angle (For Calculations) : ");
		JComboBox<String> angleDropdown = new JComboBox<String>();
		angleDropdown.addItem("Small Angle");
		angleDropdown.addItem("Large Angle");
		angleDropdown.addActionListener( new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				CONTROLLER.setRunwayAngle((String) angleDropdown.getSelectedItem());
				CONTROLLER.notify("Angle Threshold Selected : "+ (String) angleDropdown.getSelectedItem());
		}});
		angleSelection.add(angleDropdown);
		add(angleSelection);
		
		/* Take-off Direction Selector */
		MenuItem directionSelection = new MenuItem("Take-off Direction : ");
		JComboBox<String> directionDropdown = new JComboBox<String>();
		directionDropdown.addItem("Towards Selected Threshold End");
		directionDropdown.addItem("Away From Selected Threshold End");
		directionDropdown.addActionListener( new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				CONTROLLER.setTakeoffDirection((String) directionDropdown.getSelectedItem());
				CONTROLLER.notify("Take-Off Direction Selected : "+ (String) directionDropdown.getSelectedItem());
		}});
		directionSelection.add(directionDropdown);
		add(directionSelection);
		
		/* Horizontal Line */
		add(new JSeparator(SwingConstants.HORIZONTAL));
		
		/* Obstacle Selection and Dropdown Menu */
		MenuItem obsacleSelection = new MenuItem("Obstacle : ");
		JComboBox<String> obsacleDropdown = new JComboBox<String>();
		obsacleDropdown.addItem("None");
		for (Obstacle o: CONTROLLER.getObstacles()) 
		{
			obsacleDropdown.addItem(o.getName());
		}
		obsacleDropdown.addActionListener( new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				CONTROLLER.notify("Obstacle Selected : "+ (String) obsacleDropdown.getSelectedItem());
				CONTROLLER.selectObstacle((String) obsacleDropdown.getSelectedItem());
		}});
		obsacleSelection.addComponent(obsacleDropdown);
		add(obsacleSelection);
		
		/* Obstacle position selection */
		MenuItem xPosSelection = new MenuItem("Horizontal Displacement : ");
		MenuItem yPosSelection = new MenuItem("Vertical Displacement : ");
		xPosInput = new JTextField("0");
		yPosInput = new JTextField("0");
		xPosInput.setPreferredSize(new Dimension(50, 20));
		yPosInput.setPreferredSize(new Dimension(50, 20));
		xPosSelection.add(xPosInput);
		yPosSelection.add(yPosInput);
		yPosInput.addActionListener( new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (!CONTROLLER.selectObstacleYPos(yPosInput.getText())) yPosInput.setText("0");
		}});
		xPosInput.addActionListener( new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (!CONTROLLER.selectObstacleXPos(xPosInput.getText())) xPosInput.setText("0");
		}});
		add(xPosSelection);
		add(yPosSelection);
		
		/* Horizontal Line */
		add(new JSeparator(SwingConstants.HORIZONTAL));
		
		/* Vertical Spacer */
		add(Box.createRigidArea(new Dimension(0, 200)));
	}
}
