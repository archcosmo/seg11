package UI;

import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

import Application.Controller;

@SuppressWarnings("serial")
public class RecalcValPanel extends JPanel 
{
	JLabel calculations;
	Controller CONTROLLER;
	
	public RecalcValPanel(Controller c) 
	{
		CONTROLLER = c;
		
		calculations = new JLabel("");
		add(calculations);
	}
	
	@Override
	public void paintComponent(Graphics g) 
	{
		String s = CONTROLLER.getCalculations();
		if (s.equals("")) 
		{
			calculations.setText("Currently no calculations to display");
		}
		else
		{
			calculations.setText("<html>"+s+"</html>");;
		}
	}
}
