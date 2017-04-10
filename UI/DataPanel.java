package UI;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DataPanel extends JPanel 
{
	JLabel calculations;
	
	public DataPanel() {
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
				System.err.println("Printing calculation breakdown");
			}
        });
	}
}
