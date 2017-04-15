package UI;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Application.Controller;

@SuppressWarnings("serial")
public class DataPanel extends JPanel 
{
	JLabel calculations;
	Controller CONTROLLER;
	
	public DataPanel(Controller c) {
		CONTROLLER = c;
		/* Vertical Spacer */
		add(Box.createRigidArea(new Dimension(0, 500)));
		
		calculations = new JLabel("");
		add(calculations);
	}
	
	public void printStr(String s){
		EventQueue.invokeLater(() -> {
			if (s.equals("")) {
				calculations.setText("Currently no calculations to display");
			}
			else
			{
				calculations.setText("<html>"+s+"</html>");;
				CONTROLLER.notify("Printed Calculatons Breakdown");
			}
        });
	}
}
