package UI;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Application.Controller;

@SuppressWarnings("serial")
public class RecalcValPanel extends JScrollPane
{
	JLabel calculations;
	Controller CONTROLLER;
	
	public RecalcValPanel(Controller c) 
	{
		CONTROLLER = c;
		
		this.setPreferredSize(new Dimension(420, this.getPreferredSize().height));
		
		JPanel pane = new JPanel();
		this.setViewportView(pane);
		
		calculations = new JLabel("");
		pane.add(calculations);
	}
	
	@Override
	public void paintComponent(Graphics g) 
	{
		String s = CONTROLLER.getCalculationValues();
		if (s == null || s.equals("")) 
		{
			calculations.setText("<html><br><br>Currently no calculated values to display");
		}
		else
		{
			calculations.setText("<html><br><br>"+s+"</html>");;
		}
	}
}
