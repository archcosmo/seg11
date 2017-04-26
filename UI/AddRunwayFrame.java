package UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Application.Controller;
import Model.LogicalRunway;
import Model.Runway;

public class AddRunwayFrame {
	Controller controller;
	JTextField angleField, lengthField, widthField, resaField, baField, 
		seField, sTODAField, sTORAField, sASDAField, sLDAField, lTODAField, lTORAField, lASDAField, lLDAField;
	ValidateValue angleVal, lengthVal, widthVal, resaVal, blastVal, stripVal, sTorVal, sTodVal, sAsdVal, sLdVal, lTorVal, lTodVal, lAsdVal, lLdVal;
	Runway runway;
	Boolean edit = false;
	JFrame errorFrame;

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
		angleVal.value = angleVal.value /10;
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
			sTorVal.validationString += "<html>TORA must not be greater than runway length.</html>";
			sTorVal.valid = false;			
		}
		if (sTodVal.value < sTorVal.value) {
			sTorVal.validationString += "<html>TORA must not be greater than TODA.</html>";
			sTorVal.valid = false;
		}
		if (sAsdVal.value < sTorVal.value) {
			sTorVal.validationString += "<html>TORA must not be greater than ASDA.</html>";
			sTorVal.valid = false;
		}
		if (sTorVal.value < sLdVal.value) {
			sTorVal.validationString += "<html>LDA must not be greater than TORA.</html>";
			sLdVal.valid = false;
		}

		if (lTorVal.value > lengthVal.value) {
			lTorVal.validationString += "<html>TORA must not be greater than runway length.</html>";
			lTorVal.valid = false;
		}
		if (lTodVal.value < lTorVal.value) {
			lTorVal.validationString += "<html>TORA must not be greater than TODA.</html>";
			lTorVal.valid = false;
		}
		if (lAsdVal.value < lTorVal.value) {
			lTorVal.validationString += "<html>TORA must not be greater than ASDA.</html>";
			lTorVal.valid = false;
		}
		if (lTorVal.value < lLdVal.value) {
			lTorVal.validationString += "<html>LDA must not be greater than TORA.</html>";
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
			errorFrame.add(createValidationTitleJLabel(sTorVal, "Short Angle TORA"), c);
			c.gridx = 1;
			errorFrame.add(new JLabel(sTorVal.validationString), c);

			c.gridx = 0; c.gridy = 7;
			errorFrame.add(createValidationTitleJLabel(sTodVal, "Short Angle TODA"), c);
			c.gridx = 1;
			errorFrame.add(new JLabel(sTodVal.validationString), c);

			c.gridx = 0; c.gridy = 8;
			errorFrame.add(createValidationTitleJLabel(sAsdVal, "Short Angle ASDA"), c);
			c.gridx = 1;
			errorFrame.add(new JLabel(sAsdVal.validationString), c);

			c.gridx = 0; c.gridy = 9;
			errorFrame.add(createValidationTitleJLabel(sLdVal, "Short Angle LDA"), c);
			c.gridx = 1;
			errorFrame.add(new JLabel(sLdVal.validationString), c);

			c.gridx = 0; c.gridy = 10;
			errorFrame.add(createValidationTitleJLabel(lTorVal, "Long Angle TORA"), c);
			c.gridx = 1;
			errorFrame.add(new JLabel(lTorVal.validationString), c);

			c.gridx = 0; c.gridy = 11;
			errorFrame.add(createValidationTitleJLabel(lTodVal, "Long Angle TODA"), c);
			c.gridx = 1;
			errorFrame.add(new JLabel(lTodVal.validationString), c);

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

	public void makeRunway() {
		runway = new Runway(resaVal.value, blastVal.value, stripVal.value, lengthVal.value, widthVal.value);
		int sAngle, lAngle;
		if (angleVal.value >= 18) {
			lAngle = angleVal.value;
			sAngle = (angleVal.value + 18) % 36;
		} else {
			sAngle = angleVal.value;
			lAngle = (angleVal.value + 18) % 36;
		}

		String sDes = Integer.toString(sAngle);
		String lDes = Integer.toString(lAngle);
		if (sDes.length()==1) sDes = "0"+sDes;
		if (lDes.length()==1) lDes = "0"+lDes;
		LogicalRunway s = new LogicalRunway(sDes, runway, sTorVal.value, sTodVal.value, sAsdVal.value, sLdVal.value);
		LogicalRunway l = new LogicalRunway(lDes, runway, lTorVal.value, lTodVal.value, lAsdVal.value, lLdVal.value);
		runway.setLogicalRunways(s, l);
	}
	
	public void addRunway() {
		if (!edit) {
			controller.selectedAirport.addRunway(runway);
			controller.updateCombo(runway);
		}
	}

	private ValidateValue validateNumber(String input, int min, int max) {
		boolean valid = true;
		int value = 0;
		String validation = "<html>";
		if (input.matches("\\d+")) {
			value = Integer.parseInt(input);
			if (value < min) {
				validation += "Value must be a greater than " + min + ".<br>";
				valid = false;
			}
			if (value > max) {
				validation += "Value must be less than " + max + ".<br>";
				valid = false;
			}
		} else {
			validation += "Input not valid.<br>";
			valid = false;
		}
		validation += "</html>";
		return new ValidateValue(valid, value, validation);
	}

	private JLabel createValidationTitleJLabel (ValidateValue validateValue, String labelTitle) {
		JLabel label = new JLabel(labelTitle);
		if (validateValue.valid) {
			label.setForeground(Color.GREEN);
		} else {
			label.setForeground(Color.RED);
		}
		return  label;
	}
}