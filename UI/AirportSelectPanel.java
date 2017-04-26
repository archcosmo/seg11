package UI;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Application.Controller;
import Model.Airport;

@SuppressWarnings("serial")
public class AirportSelectPanel extends JPanel {
	
	Controller CONTROLLER;
	
	public AirportSelectPanel(Controller c) 
	{
		CONTROLLER = c;
		initPanel();
	}

	private void initPanel() 
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		addComponents();
	}

	/* Create menu */
	private void addComponents() 
	{
		Font labelFont = this.getFont().deriveFont(20.0f);
		boolean noAirports = (CONTROLLER.getAirports().size() == 0);
		if (!noAirports)
		{
			/* Vertical Spacer */
			add(Box.createRigidArea(new Dimension(0, 100)));
			
			/* Intro label */
			JLabel intro = new JLabel("Welcome to " + Controller.TITLE);
			intro.setFont(labelFont);
			intro.setAlignmentX(Component.CENTER_ALIGNMENT);
			add(intro);
			
			/* Vertical Spacer */
			add(Box.createRigidArea(new Dimension(0, 100)));
			
			/* Airport Selection and Dropdown Menu : No Handler yet */
			MenuItem airportSelection = new MenuItem("Select an Airport : ", labelFont);
			JComboBox<String> airportDropdown = new JComboBox<String>();
			airportDropdown.setFont(labelFont);
			for (Airport a: CONTROLLER.getAirports()) 
			{
				airportDropdown.addItem(a.getName());
			}
			airportSelection.addComponent(airportDropdown);
			add(airportSelection);
			
			/* Confirmation button */
			JButton selectAirport = new JButton("Use this airport");
			selectAirport.setFont(labelFont);
			selectAirport.setAlignmentX(Component.CENTER_ALIGNMENT);
			add(selectAirport);
			selectAirport.addActionListener( new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						CONTROLLER.notify("Airport Selected : "+ (String) airportDropdown.getSelectedItem());
						CONTROLLER.selectAirport((String) airportDropdown.getSelectedItem());
				}});
			
			/* Vertical Spacer */
			add(Box.createRigidArea(new Dimension(0, 150)));
		}
		MenuItem newAirportNotification = new MenuItem("<html><center><br>NT: New Airports will be pre-loaded with a generic runway 00/18 when they are first made.");
		add(newAirportNotification);
		
		/* Add a newAirport option */
		String temp = noAirports ? "No airports found in files. Create a new airport with name: " : "or create and use a new airport with name : ";
		MenuItem newAirport = new MenuItem(temp, labelFont);
		JTextField newAirportName = new JTextField("");
		newAirportName.setFont(labelFont);
		newAirportName.setPreferredSize(new Dimension(200, 32));
		newAirport.add(newAirportName);
		add(newAirport);
		
		/* Confirmation button */
		JButton makeAirport = new JButton("Create and use this airport");
		makeAirport.setFont(labelFont);
		makeAirport.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(makeAirport);
		makeAirport.addActionListener( new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (!CONTROLLER.newAirport((String) newAirportName.getText())) CONTROLLER.notify("Invalid Airport Name");
		}});
	
		/* Vertical Spacer */
		add(Box.createRigidArea(new Dimension(0, 600)));
	}
}
