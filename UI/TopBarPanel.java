package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Application.Controller;

@SuppressWarnings("serial")
public class TopBarPanel extends JPanel 
{
	Controller CONTROLLER;
	
	public TopBarPanel(Controller c) 
	{
		CONTROLLER = c;
		initPanel();
	}

	private void initPanel() 
	{
        setBackground(Color.LIGHT_GRAY);
        setLayout(new BorderLayout());
        addComponents();
	}

	private void addComponents() 
	{
		/* Airport label */
		JLabel airportName = new JLabel("Selected Airport: " + CONTROLLER.AIRPORT_NAME);
		add(airportName, BorderLayout.CENTER);
		
		/* Switch Airports Button */
		JButton switchAirport = new JButton("Switch Airport");
		switchAirport.addActionListener( new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				CONTROLLER.notify("Airport Switch Initiated");
				CONTROLLER.showAirportSelection();
		}});
		add(switchAirport, BorderLayout.EAST);
	}
}
