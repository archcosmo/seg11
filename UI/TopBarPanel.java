package UI;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
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
		/* Exit Button, Event Handler done locally */
		JButton exitButton = new JButton("QUIT");
		add(exitButton, BorderLayout.EAST);
		exitButton.addActionListener( (ActionEvent) -> { System.exit(0); } );
	}
}
