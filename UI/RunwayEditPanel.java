package UI;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;

import javax.swing.*;

import Application.Controller;
import Model.Runway;


@SuppressWarnings("serial")
public class RunwayEditPanel extends JPanel {
	
	Controller CONTROLLER;
	JPanel LEFT;
	JPanel RIGHT;
	
	public RunwayEditPanel(Controller c) 
	{
		CONTROLLER = c;
		LEFT = new JPanel();
		RIGHT = new JPanel();

		JFrame frame = new JFrame("Manage Runways");
		frame.setSize(1200, 800);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		addComponents();
		frame.add(this);
		frame.setVisible(true);
	}
	
	public void addComponents() 
	{
		setLayout(new GridLayout(0, 2));
		
		LEFT.setLayout(new BoxLayout(LEFT, BoxLayout.Y_AXIS));
		RIGHT.setLayout(new BoxLayout(RIGHT, BoxLayout.Y_AXIS));
		
		/* LEFT side : Runway selecting, deleting and adding */
		
			/* Vertical Spacer */
			LEFT.add(Box.createRigidArea(new Dimension(0, 200)));
			
			/* Runway Selection and Dropdown Menu */
			MenuItem runwaySelection = new MenuItem("Select Runway To Edit : ");
			JComboBox<String> runwayDropdown = new JComboBox<String>();
			for (Runway r: CONTROLLER.getRunways()) 
			{
				runwayDropdown.addItem(r.getName());
			}
			runwayDropdown.addActionListener( new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					//CONTROLLER.selectRunway((String) runwayDropdown.getSelectedItem());
					// TODO: Handler
			}});
			runwaySelection.addComponent(runwayDropdown);
			LEFT.add(runwaySelection);
			
			/* Remove Selected Runway */
			MenuItem removeRunwayMenu = new MenuItem("");
			JButton removeRunway = new JButton("Delete Selected Runway");
			removeRunwayMenu.add(removeRunway);
			//TODO: Handler, calls remove runway in controller if runways.size != 0, updates lists
			LEFT.add(removeRunwayMenu);
			
			/* Add new generic runway */
			MenuItem addRunwayMenu = new MenuItem("");
			JButton addRunway = new JButton("Add New Generic Runway 00/18");
			addRunwayMenu.add(addRunway);
			//TODO: Handler, calls add runway in controller, updates lists
			LEFT.add(addRunwayMenu);
			
			/* Update selected runay with new values */
			MenuItem editRunwayMenu = new MenuItem("");
			JButton editRunway = new JButton("Update Selected Runway With New Values");
			editRunwayMenu.add(editRunway);
			//TODO: Handler, check validations, delete old, add new
			LEFT.add(editRunwayMenu);
			
			/* Vertical Spacer */
			LEFT.add(Box.createRigidArea(new Dimension(0, 300)));
			
		/* RIGHT side : Viewing and changing values of selected runway */
			
			/* Vertical Spacer */
			RIGHT.add(Box.createRigidArea(new Dimension(0, 50)));
			
			MenuItem runwayNameSelect = new MenuItem("Runway Small Angle Threshold : ");
			MenuItem bigAngleMenu = new MenuItem("Runway Large Angle Threshold : ");
			JLabel bigAngle = new JLabel("00"); //Tells the user the large angle counterpart of the selected small angle. Prints error if parse error or ! 0 <= x < 18
			bigAngleMenu.add(bigAngle);
			JTextField runwayAngle = new JTextField();
			runwayAngle.setPreferredSize(new Dimension(24, 20));
			runwayNameSelect.add(runwayAngle);
			//TODO: Handler, parse int, store value, update bigAngle
			RIGHT.add(runwayNameSelect);
			RIGHT.add(bigAngleMenu);
			
			/* All other variable inputs for main runway data */
			MenuItem lnM = new MenuItem("Runway Length : ");
			MenuItem wdM = new MenuItem("Runway Width : ");
			MenuItem brM = new MenuItem("Blast Radius : ");
			MenuItem rsM = new MenuItem("RESA : ");
			MenuItem seM = new MenuItem("Strip End Length : ");
			JTextField ln = new JTextField();
			JTextField wd = new JTextField();
			JTextField br = new JTextField();
			JTextField rs = new JTextField();
			JTextField se = new JTextField();
			ln.setPreferredSize(new Dimension(80, 20));
			wd.setPreferredSize(new Dimension(80, 20));
			br.setPreferredSize(new Dimension(80, 20));
			rs.setPreferredSize(new Dimension(80, 20));
			se.setPreferredSize(new Dimension(80, 20));
			lnM.add(ln);
			wdM.add(wd);
			brM.add(br);
			rsM.add(rs);
			seM.add(se);
			RIGHT.add(lnM);
			RIGHT.add(wdM);
			RIGHT.add(brM);
			RIGHT.add(rsM);
			RIGHT.add(seM);
			//TODO: Handlers: Validate, parse and set holder variables
			
		/* RIGHT side : Viewing and changing values of selected runway LR */
			
			MenuItem smallL = new MenuItem("- Small Angle Logical Runway - ");
			RIGHT.add(smallL);
			
			/* Add attributes */
			MenuItem toraM = new MenuItem("TORA : ");
			MenuItem todaM = new MenuItem("TODA : ");
			MenuItem asdaM = new MenuItem("ASDA : ");
			MenuItem ldaM = new MenuItem("LDA : ");
			JTextField tora = new JTextField();
			JTextField toda = new JTextField();
			JTextField asda = new JTextField();
			JTextField lda = new JTextField();
			tora.setPreferredSize(new Dimension(80, 20));
			toda.setPreferredSize(new Dimension(80, 20));
			asda.setPreferredSize(new Dimension(80, 20));
			lda.setPreferredSize(new Dimension(80, 20));
			toraM.add(tora);
			todaM.add(toda);
			asdaM.add(asda);
			ldaM.add(lda);
			RIGHT.add(toraM);
			RIGHT.add(todaM);
			RIGHT.add(asdaM);
			RIGHT.add(ldaM);
			//TODO: Validate, parse and set holder variables
			
			MenuItem largeL = new MenuItem("- Large Angle Logical Runway - ");
			RIGHT.add(largeL);
			
			/* Add attributes */
			MenuItem toraM2 = new MenuItem("TORA : ");
			MenuItem todaM2 = new MenuItem("TODA : ");
			MenuItem asdaM2 = new MenuItem("ASDA : ");
			MenuItem ldaM2 = new MenuItem("LDA  : ");
			JTextField tora2 = new JTextField();
			JTextField toda2 = new JTextField();
			JTextField asda2 = new JTextField();
			JTextField lda2 = new JTextField();
			tora2.setPreferredSize(new Dimension(80, 20));
			toda2.setPreferredSize(new Dimension(80, 20));
			asda2.setPreferredSize(new Dimension(80, 20));
			lda2.setPreferredSize(new Dimension(80, 20));
			toraM2.add(tora2);
			todaM2.add(toda2);
			asdaM2.add(asda2);
			ldaM2.add(lda2);
			RIGHT.add(toraM2);
			RIGHT.add(todaM2);
			RIGHT.add(asdaM2);
			RIGHT.add(ldaM2);
			//TODO: Validate, parse and set holder variables
			
			/* Vertical Spacer */
			RIGHT.add(Box.createRigidArea(new Dimension(0, 300)));
			
		
		add(LEFT);
		add(RIGHT);
	}
}
