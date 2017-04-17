package UI;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Application.Controller;
import Model.Obstacle;

@SuppressWarnings("serial")
public class ObstacleEditPanel extends JPanel {
	
	Controller CONTROLLER;
	JPanel LEFT;
	JPanel RIGHT;
	
	public ObstacleEditPanel(Controller c) 
	{
		CONTROLLER = c;
		LEFT = new JPanel();
		RIGHT = new JPanel();

		addComponents();
	}
	
	public void addComponents() 
	{
		setLayout(new GridLayout(0, 2));
		
		LEFT.setLayout(new BoxLayout(LEFT, BoxLayout.Y_AXIS));
		RIGHT.setLayout(new BoxLayout(RIGHT, BoxLayout.Y_AXIS));
		
		/* LEFT side : Obstacle selecting, deleting and adding */
		
			/* Vertical Spacer */
			LEFT.add(Box.createRigidArea(new Dimension(0, 200)));
			
			/* Obstacle Selection and Dropdown Menu */
			MenuItem runwaySelection = new MenuItem("Select Obstacle To Edit : ");
			JComboBox<String> runwayDropdown = new JComboBox<String>();
			for (Obstacle r: CONTROLLER.getObstacles()) 
			{
				runwayDropdown.addItem(r.getName());
			}
			runwayDropdown.addActionListener( new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					// TODO: Handler
			}});
			runwaySelection.addComponent(runwayDropdown);
			LEFT.add(runwaySelection);
			
			/* Remove Selected Obstacle */
			MenuItem removeRunwayMenu = new MenuItem("");
			JButton removeRunway = new JButton("Delete Selected Obstacle");
			removeRunwayMenu.add(removeRunway);
			//TODO: Handler, calls remove obstacle in controller if runways.size != 0, updates lists
			LEFT.add(removeRunwayMenu);
			
			/* Add new generic obstacle */
			MenuItem addRunwayMenu = new MenuItem("");
			JButton addRunway = new JButton("Add New Generic Obstacle");
			addRunwayMenu.add(addRunway);
			//TODO: Handler, calls add obstacle in controller, updates lists
			LEFT.add(addRunwayMenu);
			
			/* Update selected runay with new values */
			MenuItem editRunwayMenu = new MenuItem("");
			JButton editRunway = new JButton("Update Selected Obstacle With New Values");
			editRunwayMenu.add(editRunway);
			//TODO: Handler, check validations, delete old, add new
			LEFT.add(editRunwayMenu);
			
			/* Vertical Spacer */
			LEFT.add(Box.createRigidArea(new Dimension(0, 300)));
			
		/* RIGHT side : Viewing and changing values of selected obstacle */
			
			/* Vertical Spacer */
			RIGHT.add(Box.createRigidArea(new Dimension(0, 200)));
			
			/* All other variable inputs for main obstacle data */
			MenuItem nameM = new MenuItem("Obstacle Name : "); //Needs explicit checking on validation
			MenuItem lengthM = new MenuItem("Obstacle Length : ");
			MenuItem widthM = new MenuItem("Obstacle Width : ");
			MenuItem heightM = new MenuItem("Obstacle Height : ");
			JTextField name = new JTextField();
			JTextField length = new JTextField();
			JTextField width = new JTextField();
			JTextField height = new JTextField();
			name.setPreferredSize(new Dimension(100, 20));
			length.setPreferredSize(new Dimension(80, 20));
			width.setPreferredSize(new Dimension(80, 20));
			height.setPreferredSize(new Dimension(80, 20));
			nameM.add(name);
			lengthM.add(length);
			widthM.add(width);
			heightM.add(height);
			RIGHT.add(nameM);
			RIGHT.add(lengthM);
			RIGHT.add(widthM);
			RIGHT.add(heightM);
			//TODO: Handlers: Validate, parse and set holder variables
			
			/* Vertical Spacer */
			RIGHT.add(Box.createRigidArea(new Dimension(0, 300)));
			
		
		add(LEFT);
		add(RIGHT);
	}
}
