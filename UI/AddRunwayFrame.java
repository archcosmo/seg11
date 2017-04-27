package UI;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Application.Controller;
import Model.LogicalRunway;
import Model.Runway;

import static UI.ValidateValue.createValidationTitleJLabel;
import static UI.ValidateValue.validateNumber;

public class AddRunwayFrame {
	Controller controller;
	JTextField angleField, lengthField, widthField, resaField, baField, 
		seField, sTODAField, sTORAField, sASDAField, sLDAField, lTODAField, lTORAField, lASDAField, lLDAField;
	ValidateValue angleVal, lengthVal, widthVal, resaVal, blastVal, stripVal, sTorVal, sTodVal, sAsdVal, sLdVal, lTorVal, lTodVal, lAsdVal, lLdVal;
	Runway runway;
	Runway newRunway;
	Boolean edit = false;
	JFrame errorFrame;
	String sDes, lDes;
	String r1Des, r2Des;
	JComboBox<String> choiceBox1, choiceBox2, choiceBox3;

	public AddRunwayFrame(Controller c) {
		controller = c;
		init();
	}
	
	public AddRunwayFrame(Controller c, Runway runway) {
		controller = c;
		this.runway = runway;
		edit = true;
		init();
		int angle = Integer.parseInt(runway.getName().substring(0,2))*10;
		angleField.setText(Integer.toString(angle));
		lengthField.setText(Integer.toString(runway.length));
		widthField.setText(Integer.toString(runway.width));
		resaField.setText(Integer.toString(runway.RESA));
		baField.setText(Integer.toString(runway.blastAllowance));
		seField.setText(Integer.toString(runway.stripEnd));
		
		sTODAField.setText(Integer.toString(runway.lowAngle().toda));
		sTORAField.setText(Integer.toString(runway.lowAngle().tora));
		sASDAField.setText(Integer.toString(runway.lowAngle().asda));
		sLDAField.setText(Integer.toString(runway.lowAngle().lda));
		
		lTODAField.setText(Integer.toString(runway.highAngle().toda));
		lTORAField.setText(Integer.toString(runway.highAngle().tora));
		lASDAField.setText(Integer.toString(runway.highAngle().asda));
		lLDAField.setText(Integer.toString(runway.highAngle().lda));
	}
	
	public void init() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		frame.setLayout(gridBagLayout);
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5,5,5,5);
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		
		/* ========================================== */
		
		JPanel runway = new JPanel();
		runway.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Runway"));
		runway.setLayout(gridBagLayout);
		
		c.gridx = 0; c.gridy = 0;
		runway.add(new JLabel("Angle (degrees)"), c);
		c.gridx = 1;
		angleField = new JTextField();
		angleField.setColumns(5);
		runway.add(angleField, c);

		c.gridx = 0; c.gridy = 1;
		runway.add(new JLabel("Length (m)"), c);
		c.gridx = 1;
		lengthField = new JTextField();
		lengthField.setColumns(5);
		runway.add(lengthField, c);
		
		c.gridx = 0; c.gridy = 2;
		runway.add(new JLabel("Width (m)"), c);
		c.gridx = 1;
		widthField = new JTextField();
		widthField.setColumns(5);
		runway.add(widthField, c);
		
		c.gridx = 0; c.gridy = 3;
		runway.add(new JLabel("RESA (m)"), c);
		c.gridx = 1;
		resaField = new JTextField("240");
		resaField.setColumns(5);
		runway.add(resaField, c);

		c.gridx = 0; c.gridy = 4;
		runway.add(new JLabel("Blast Allowance (m)"), c);
		c.gridx = 1;
		baField = new JTextField("300");
		baField.setColumns(5);
		runway.add(baField, c);
		
		c.gridx = 0; c.gridy = 5;
		runway.add(new JLabel("Strip End (m)"), c);
		c.gridx = 1;
		seField = new JTextField("60");
		seField.setColumns(5);
		runway.add(seField, c);

		c.gridx = 0; c.gridy = 0;
		frame.add(runway, c);
		
		/* ========================================== */
		JPanel midPanel = new JPanel();
		midPanel.setLayout(gridBagLayout);
		JPanel shortPanel = new JPanel();
		shortPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Short Angle Log. Runway"));
		shortPanel.setLayout(gridBagLayout);
		
		c.gridx = 0; c.gridy = 0;
		shortPanel.add(new JLabel("TODA (m)"), c);
		c.gridx = 1;
		sTODAField = new JTextField();
		sTODAField.setColumns(10);
		shortPanel.add(sTODAField, c);
		
		c.gridx = 0; c.gridy = 1;
		shortPanel.add(new JLabel("TORA (m)"), c);
		c.gridx = 1;
		sTORAField = new JTextField();
		sTORAField.setColumns(10);
		shortPanel.add(sTORAField, c);

		c.gridx = 0; c.gridy = 2;
		shortPanel.add(new JLabel("ASDA (m)"), c);
		c.gridx = 1;
		sASDAField = new JTextField();
		sASDAField.setColumns(10);
		shortPanel.add(sASDAField, c);

		c.gridx = 0; c.gridy = 3;
		shortPanel.add(new JLabel("LDA (m)"), c);
		c.gridx = 1;
		sLDAField = new JTextField();
		sLDAField.setColumns(10);
		shortPanel.add(sLDAField, c);
		
		c.gridx = 0; c.gridy = 0;
		midPanel.add(shortPanel, c);
		c.gridy = 1;
		JButton cancel = new JButton("Cancel");
		cancel.setPreferredSize(new Dimension(150, 20));
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		midPanel.add(cancel, c);
		c.gridx = 1; c.gridy = 0;
		frame.add(midPanel, c);

		/* ========================================== */
		
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(gridBagLayout);
		JPanel longPanel = new JPanel();
		longPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Long Angle Log. Runway"));
		longPanel.setLayout(gridBagLayout);
		
		c.gridx = 0; c.gridy = 0;
		longPanel.add(new JLabel("TODA (m)"), c);
		c.gridx = 1;
		lTODAField = new JTextField();
		lTODAField.setColumns(10);
		longPanel.add(lTODAField, c);
		
		c.gridx = 0; c.gridy = 1;
		longPanel.add(new JLabel("TORA (m)"), c);
		c.gridx = 1;
		lTORAField = new JTextField();
		lTORAField.setColumns(10);
		longPanel.add(lTORAField, c);

		c.gridx = 0; c.gridy = 2;
		longPanel.add(new JLabel("ASDA (m)"), c);
		c.gridx = 1;
		lASDAField = new JTextField();
		lASDAField.setColumns(10);
		longPanel.add(lASDAField, c);

		c.gridx = 0; c.gridy = 3;
		longPanel.add(new JLabel("LDA (m)"), c);
		c.gridx = 1;
		lLDAField = new JTextField();
		lLDAField.setColumns(10);
		longPanel.add(lLDAField, c);
		
		c.gridx = 0; c.gridy = 0;
		rightPanel.add(longPanel, c);
		c.gridy = 1;
		JButton confirm = new JButton("Confirm");
		confirm.setPreferredSize(new Dimension(150, 20));
		confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (checkValues()) {
					makeRunway();
					addRunway();
					frame.dispose();
				}
			}
		});
		rightPanel.add(confirm, c);
		c.gridx = 2; c.gridy = 0;
		frame.add(rightPanel, c);

		/* ========================================== */
		
		frame.setTitle("Add/Edit Runway");
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setVisible(true);
	}
	
	public boolean checkValues(){
		
		//Input validation
		angleVal = validateNumber(angleField.getText(), 0, 359);
		angleVal.value = (angleVal.value+5) /10;
		lengthVal = validateNumber(lengthField.getText(), 100, 10000);
		widthVal = validateNumber(widthField.getText(), 5, 2000);
		resaVal = validateNumber(resaField.getText(), 0, 1000);
		blastVal = validateNumber(baField.getText(), 0, 1000);
		stripVal = validateNumber(seField.getText(), 0, 1000);
		sTorVal = validateNumber(sTORAField.getText(), 0, 10000);
		sTodVal = validateNumber(sTODAField.getText(), 0, 10000);
		sAsdVal = validateNumber(sASDAField.getText(), 0, 10000);
		sLdVal = validateNumber(sLDAField.getText(), 0, 10000);
		lTorVal = validateNumber(lTORAField.getText(), 0, 10000);
		lTodVal = validateNumber(lTODAField.getText(), 0, 10000);
		lAsdVal = validateNumber(lASDAField.getText(), 0, 10000);
		lLdVal = validateNumber(lLDAField.getText(), 0, 10000);
		
		if (sTorVal.value > lengthVal.value) {
			sTorVal.validationString = appendHTMLToString(sTorVal.validationString, "TORA must not be greater than runway length.");

			sTorVal.valid = false;			
		}
		if (sTodVal.value < sTorVal.value) {
			sTodVal.validationString = appendHTMLToString(sTodVal.validationString, "TODA must not be less than TORA.");
			sTodVal.valid = false;
		}
		if (sAsdVal.value < sTorVal.value) {
			sAsdVal.validationString = appendHTMLToString(sAsdVal.validationString, "ASDA must not be less than TORA.");
			sAsdVal.valid = false;
		}
		if (sTorVal.value < sLdVal.value) {
			sLdVal.validationString = appendHTMLToString(sLdVal.validationString, "LDA must not be greater than TORA.");
			sLdVal.valid = false;
		}

		if (lTorVal.value > lengthVal.value) {
			lTorVal.validationString = appendHTMLToString(lTorVal.validationString, "TORA must not be greater than runway length.");
			lTorVal.valid = false;
		}
		if (lTodVal.value < lTorVal.value) {
			lTodVal.validationString = appendHTMLToString(lTodVal.validationString, "TODA must not be less than TORA.");
			lTodVal.valid = false;
		}
		if (lAsdVal.value < lTorVal.value) {
			lAsdVal.validationString = appendHTMLToString(lAsdVal.validationString, "ASDA must not be less than TORA.");
			lAsdVal.valid = false;
		}
		if (lTorVal.value < lLdVal.value) {
			lLdVal.validationString = appendHTMLToString(lLdVal.validationString, "LDA must not be greater than TORA.");
			lLdVal.valid = false;
		}

	
		if (!(angleVal.valid && lengthVal.valid && widthVal.valid && resaVal.valid && blastVal.valid && stripVal.valid && sTorVal.valid && sTodVal.valid && sAsdVal.valid && sLdVal.valid && lTorVal.valid && lTodVal.valid && lAsdVal.valid && lLdVal.valid)) {
			if (errorFrame !=  null) errorFrame.dispose();
			errorFrame = new JFrame();
			errorFrame.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			c.anchor = GridBagConstraints.WEST;
			c.insets = new Insets(2,2,2,2);
			
			c.gridx = 0; c.gridy = 0;
			errorFrame.add(createValidationTitleJLabel(angleVal, "Angle"), c);
			c.gridx = 1;
			errorFrame.add(new JLabel(angleVal.validationString), c);

			c.gridx = 0; c.gridy = 1;
			errorFrame.add(createValidationTitleJLabel(lengthVal, "Length"), c);
			c.gridx = 1;
			errorFrame.add(new JLabel(lengthVal.validationString), c);

			c.gridx = 0; c.gridy = 2;
			errorFrame.add(createValidationTitleJLabel(widthVal, "Width"), c);
			c.gridx = 1;
			errorFrame.add(new JLabel(widthVal.validationString), c);

			c.gridx = 0; c.gridy = 3;
			errorFrame.add(createValidationTitleJLabel(resaVal, "RESA"), c);
			c.gridx = 1;
			errorFrame.add(new JLabel(resaVal.validationString), c);

			c.gridx = 0; c.gridy = 4;
			errorFrame.add(createValidationTitleJLabel(blastVal, "Blast Allowance"), c);
			c.gridx = 1;
			errorFrame.add(new JLabel(blastVal.validationString), c);

			c.gridx = 0; c.gridy = 5;
			errorFrame.add(createValidationTitleJLabel(stripVal, "Strip End"), c);
			c.gridx = 1;
			errorFrame.add(new JLabel(stripVal.validationString), c);

			c.gridx = 0; c.gridy = 6;
			errorFrame.add(createValidationTitleJLabel(sTorVal, "Short Angle TODA"), c);
			c.gridx = 1;
			errorFrame.add(new JLabel(sTodVal.validationString), c);

			c.gridx = 0; c.gridy = 7;
			errorFrame.add(createValidationTitleJLabel(sTodVal, "Short Angle TORA"), c);
			c.gridx = 1;
			errorFrame.add(new JLabel(sTorVal.validationString), c);

			c.gridx = 0; c.gridy = 8;
			errorFrame.add(createValidationTitleJLabel(sAsdVal, "Short Angle ASDA"), c);
			c.gridx = 1;
			errorFrame.add(new JLabel(sAsdVal.validationString), c);

			c.gridx = 0; c.gridy = 9;
			errorFrame.add(createValidationTitleJLabel(sLdVal, "Short Angle LDA"), c);
			c.gridx = 1;
			errorFrame.add(new JLabel(sLdVal.validationString), c);

			c.gridx = 0; c.gridy = 10;
			errorFrame.add(createValidationTitleJLabel(lTorVal, "Long Angle TODA"), c);
			c.gridx = 1;
			errorFrame.add(new JLabel(lTodVal.validationString), c);

			c.gridx = 0; c.gridy = 11;
			errorFrame.add(createValidationTitleJLabel(lTodVal, "Long Angle TORA"), c);
			c.gridx = 1;
			errorFrame.add(new JLabel(lTorVal.validationString), c);

			c.gridx = 0; c.gridy = 12;
			errorFrame.add(createValidationTitleJLabel(lAsdVal, "Long Angle ASDA"), c);
			c.gridx = 1;
			errorFrame.add(new JLabel(lAsdVal.validationString), c);

			c.gridx = 0; c.gridy = 13;
			errorFrame.add(createValidationTitleJLabel(lLdVal, "Long Angle LDA"), c);
			c.gridx = 1;
			errorFrame.add(new JLabel(lLdVal.validationString), c);

			errorFrame.pack();
			errorFrame.setLocationRelativeTo(null);
			errorFrame.setVisible(true);
			
			return false;
		}
		return true;
	}

	LogicalRunway l, s;
	public void makeRunway() {
		newRunway = new Runway(resaVal.value, blastVal.value, stripVal.value, lengthVal.value, widthVal.value);
		int sAngle, lAngle;
		if (angleVal.value >= 18) {
			lAngle = angleVal.value;
			sAngle = (angleVal.value + 18) % 36;
		} else {
			sAngle = angleVal.value;
			lAngle = (angleVal.value + 18) % 36;
		}

		sDes = Integer.toString(sAngle);
		lDes = Integer.toString(lAngle);
		if (sDes.length()==1) sDes = "0"+sDes;
		if (lDes.length()==1) lDes = "0"+lDes;
		
		/* Check if a runway exists with same angle */
		Runway run1 = null, run2 = null;
		int i = 1;
		for (Runway r : controller.selectedAirport.getRunways()) {
			if (r.lowAngle().designator.substring(0, 2).equals(sDes)) {
				if (i == 1) run1 = r;
				if (i == 2) run2 = r;
				i++;
			}
		}
		if (i>1 && !edit) setLR(sDes, run1, run2);
		
		s = new LogicalRunway(sDes, newRunway, sTorVal.value, sTodVal.value, sAsdVal.value, sLdVal.value);
		l = new LogicalRunway(lDes, newRunway, lTorVal.value, lTodVal.value, lAsdVal.value, lLdVal.value);
		newRunway.setLogicalRunways(s, l);
	}
	

	public boolean setLR(String r, Runway r1, Runway r2) {
		String rDes = r; 
		r1Des = null; r2Des = null;
		if (r1 != null && r2 !=null) {
			r1Des = r1.getName().substring(0, 3);
			r2Des = r2.getName().substring(0, 3);
		}
		if (r1 != null && r2 == null) r1Des = r1.getName().substring(0, 2); 
		
		String[] choices = new String[] {"L", "R", "C"};
		if (r2 == null) choices = new String[] {"L", "R"};
		
		
		JFrame lrFrame = new JFrame("Designator for "+rDes);
		lrFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		lrFrame.setLocationRelativeTo(null);
		lrFrame.setResizable(false);
		lrFrame.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5,5,5,5);
		
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEtchedBorder());
		panel.setLayout(new GridBagLayout());
		
		c.gridx = 0; c.gridy = 0;
		JLabel info = new JLabel("<html><body>Runway already exists at<br>this angle. Set designator<br>for these runways.</body></html>");
		lrFrame.add(info, c);
		
		c.gridx = 0; c.gridy = 0;
		JPanel newPan = new JPanel();
		newPan.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "New Runway"));
		JLabel newPos = new JLabel(rDes + " Position");
		newPan.add(newPos, c);
		
		c.gridx = 1;
		choiceBox1 = new JComboBox<String>(choices);
		newPan.add(choiceBox1, c);
		
		int y = 0;
		c.gridx = 0; c.gridy = y;
		panel.add(newPan, c);
		
		if (r1Des !=null) {
			c.gridx = 0; c.gridy = 0;
			JPanel oldPan1 = new JPanel();
			oldPan1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Old "+r1.getName()+" Runway"));
			JLabel oldPos1 = new JLabel(r1Des + " Position");
			oldPan1.add(oldPos1, c);
			
			c.gridx = 1;
			choiceBox2 = new JComboBox<String>(choices);
			oldPan1.add(choiceBox2, c);
			
			y++;
			c.gridx = 0; c.gridy = y;
			panel.add(oldPan1, c);
		}
		
		if (r2Des !=null) {
			c.gridx = 0; c.gridy = 0;
			JPanel oldPan2 = new JPanel();
			oldPan2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Old "+r2.getName()+" Runway"));
			JLabel oldPos2 = new JLabel(r2Des + " Position");
			oldPan2.add(oldPos2, c);
			
			c.gridx = 1;
			choiceBox3 = new JComboBox<String>(choices);
			oldPan2.add(choiceBox3, c);
			
			y++;
			c.gridx = 0; c.gridy = y;
			panel.add(oldPan2, c);
		}
		
		c.gridx = 0; c.gridy = 1;
		lrFrame.add(panel, c);
		
		JButton confirm = new JButton("Confirm");
		confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newDes1 = (String) choiceBox1.getSelectedItem();
				String newDes2 = null,newDes3 = null;
				if (r1Des !=null) newDes2 = (String) choiceBox2.getSelectedItem();
				if (r2Des !=null) newDes3 = (String) choiceBox3.getSelectedItem();
				boolean valid = true;
				
				if (newDes2 != null) {
					if (newDes3 != null) {
						if (newDes1.equals(newDes2) || newDes1.equals(newDes3) || newDes2.equals(newDes3)) {
							JOptionPane.showMessageDialog(lrFrame, "Cannot choose the same value twice");
							valid = false;
						}
					} else if (newDes1.equals(newDes2) || newDes1.equals(newDes3)) {
						JOptionPane.showMessageDialog(lrFrame, "Cannot choose the same value twice");
						valid = false;
					} 					
				}
				
				String opNewDes1 = null;
				if (newDes1.equals("C")) opNewDes1 = "C";
				else if (newDes1.equals("L")) opNewDes1 = "R";
				else opNewDes1 = "L";
				
				sDes = sDes + newDes1;
				lDes = lDes + opNewDes1;
				newRunway.lowAngle().designator = r1.lowAngle().designator.substring(0, 2) + newDes1;
				newRunway.highAngle().designator = r1.highAngle().designator.substring(0, 2) + opNewDes1;
				
				newRunway.setDesignator();

				String opNewDes2 = null;
				if (r1Des !=null) {
					if (newDes2.equals("C")) opNewDes2 = "C";
					else if (newDes2.equals("L")) opNewDes2 = "R";
					else opNewDes2 = "L";

					r1.lowAngle().designator = r1.lowAngle().designator.substring(0, 2) + newDes2;
					r1.highAngle().designator = r1.highAngle().designator.substring(0, 2) + opNewDes2;
					
					r1.setDesignator();
				}

				String opNewDes3 = null;
				if (r2Des !=null) {
					newDes3 = (String) choiceBox3.getSelectedItem();
					if (newDes3.equals("C")) opNewDes3 = "C";
					else if (newDes3.equals("L")) opNewDes3 = "R";
					else opNewDes3 = "L";

					r2.lowAngle().designator = r2.lowAngle().designator.substring(0, 2) + newDes3;
					r2.highAngle().designator = r2.highAngle().designator.substring(0, 2) + opNewDes3;
					
					r2.setDesignator();
				}
				if (valid) {
					controller.updateRunways();
					lrFrame.dispose();
				}
			}
		});
		c.gridx = 0; c.gridy = 2;
		lrFrame.add(confirm, c);
		
		lrFrame.pack();
		lrFrame.setVisible(true);
		return true;
	}
	
	public void addRunway() {
		if (!edit) {
			controller.selectedAirport.addRunway(newRunway);
			controller.notify("Runway added: " + newRunway.getName());
			controller.updateCombo(newRunway);
		} else {
			runway.RESA = newRunway.RESA;
			runway.blastAllowance = newRunway.blastAllowance;
			runway.stripEnd = newRunway.stripEnd;
			runway.length = newRunway.length;
			runway.width = newRunway.width;
			runway.setLogicalRunways(s, l);
			runway.setDesignator();
			controller.updateRunways();
		}
	}

	private String appendHTMLToString(String htmlString, String appendString) {
		htmlString = htmlString.substring(0, htmlString.length() - 7);
		htmlString += appendString;
		htmlString += "</html>";
		return htmlString;
	}
}