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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Application.Controller;
import Model.LogicalRunway;
import Model.Runway;

public class AddRunwayFrame {
	Controller controller;
	JTextField angleField, lengthField, widthField, resaField, baField, 
		seField, sTODAField, sTORAField, sASDAField, sLDAField, lTODAField, lTORAField, lASDAField, lLDAField;
	int angle, length, width, resa, blast, strip, sTor, sTod, sAsd, sLd, lTor, lTod, lAsd, lLd;
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
		//todo new runway L/R designator
		boolean ang = false;
		if (angleField.getText().matches("\\d+")) {
			angle = Integer.parseInt(angleField.getText());
			if (angle >= 0 && angle <= 360) ang = true;
			angle = angle/10;
		}
		
		boolean len = false;
		if (lengthField.getText().matches("\\d+")) {
			length = Integer.parseInt(lengthField.getText());
			if (length >= 100 && length < 10001) len = true;
		}

		boolean wid = false;
		if (widthField.getText().matches("\\d+")) {
			width = Integer.parseInt(widthField.getText());
			if (width >= 5 && width < 2001) wid = true;
		}

		boolean res = false;
		if (resaField.getText().matches("\\d+")) {
			resa = Integer.parseInt(resaField.getText());
			res = true;
		}

		boolean ba = false;
		if (baField.getText().matches("\\d+")) {
			blast = Integer.parseInt(baField.getText());
			ba = true;
		}

		boolean se = false;
		if (angleField.getText().matches("\\d+")) {
			strip = Integer.parseInt(seField.getText());
			se = true;
		}
		
		boolean sTORA = false;
		if (sTORAField.getText().matches("\\d+")) {
			sTor = Integer.parseInt(sTORAField.getText());
			if (sTor >= 0 && sTor <= 10000 && sTor <= length) sTORA = true;
		}
		
		boolean sTODA = false;
		if (sTORAField.getText().matches("\\d+")) {
			sTod = Integer.parseInt(sTORAField.getText());
			if (sTod >= 0 && sTod <= 10000 && sAsd <= sTod) sTODA = true;
		}
		
		boolean sASDA = false;
		if (sASDAField.getText().matches("\\d+")) {
			sAsd = Integer.parseInt(sASDAField.getText());
			if (sAsd >= 0 && sAsd <= 10000 && sTor <= sAsd) sASDA = true;
		}	

		boolean sLDA = false;
		if (sLDAField.getText().matches("\\d+")) {
			sLd = Integer.parseInt(sLDAField.getText());
			if (sLd >= 0 && sLd <= 10000 && sLd <= sTor) sLDA = true;
		}
		
		boolean lTORA = false;
		if (lTORAField.getText().matches("\\d+")) {
			lTor = Integer.parseInt(lTORAField.getText());
			if (lTor >= 0 && lTor <= 10000 && lTor <= length) lTORA = true;
		}
		
		boolean lTODA = false;
		if (lTORAField.getText().matches("\\d+")) {
			lTod = Integer.parseInt(lTORAField.getText());
			if (lTod >= 0 && lTod <= 10000 && lAsd <= lTod) lTODA = true;
		}
		
		boolean lASDA = false;
		if (lASDAField.getText().matches("\\d+")) {
			lAsd = Integer.parseInt(lASDAField.getText());
			if (lAsd >= 0 && lAsd <= 10000 && lTor <= lAsd) lASDA = true;
		}	

		boolean lLDA = false;
		if (lLDAField.getText().matches("\\d+")) {
			lLd = Integer.parseInt(lLDAField.getText());
			if (lLd >= 0 && lLd <= 10000 && lLd <= lTor) lLDA = true;
		}
		
		if (!(ang && len && wid && res && ba && se && sTORA && sTODA && sASDA && sLDA && lTORA && lTODA && lASDA && lLDA)) {
			if (errorFrame !=  null) errorFrame.dispose();
			errorFrame = new JFrame();
			errorFrame.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			c.anchor = GridBagConstraints.WEST;
			c.insets = new Insets(2,2,2,2);
			
			c.gridx = 1; c.gridy = 0;
			errorFrame.add(new JLabel("Angle must be integer between 0 and 360"), c);
			c.gridx = 0;
			JLabel angValid = new JLabel();
			if (ang) {
				angValid.setForeground(Color.GREEN);
				angValid.setText("Valid Input");
			} else {
				angValid.setForeground(Color.RED);
				angValid.setText("Invalid Input");
			}
			errorFrame.add(angValid, c);

			c.gridx = 1; c.gridy = 1;
			errorFrame.add(new JLabel("Length must be integer between 100 and 10000"), c);
			c.gridx = 0;
			JLabel lenValid = new JLabel();
			if (len) {
				lenValid.setForeground(Color.GREEN);
				lenValid.setText("Valid Input");
			} else {
				lenValid.setForeground(Color.RED);
				lenValid.setText("Invalid Input");
			}
			errorFrame.add(lenValid, c);
			
			c.gridx = 1; c.gridy = 2;
			errorFrame.add(new JLabel("Width must be integer between 5 and 2000"), c);
			c.gridx = 0;
			JLabel widValid = new JLabel();
			if (wid) {
				widValid.setForeground(Color.GREEN);
				widValid.setText("Valid Input");
			} else {
				widValid.setForeground(Color.RED);
				widValid.setText("Invalid Input");
			}
			errorFrame.add(widValid, c);
			
			c.gridx = 1; c.gridy = 3;
			errorFrame.add(new JLabel("RESA must be integer"), c);
			c.gridx = 0;
			JLabel resValid = new JLabel();
			if (res) {
				resValid.setForeground(Color.GREEN);
				resValid.setText("Valid Input");
			} else {
				resValid.setForeground(Color.RED);
				resValid.setText("Invalid Input");
			}
			errorFrame.add(resValid, c);
			
			c.gridx = 1; c.gridy = 4;
			errorFrame.add(new JLabel("Blast Allowance must be integer"), c);
			c.gridx = 0;
			JLabel baValid = new JLabel();
			if (ba) {
				baValid.setForeground(Color.GREEN);
				baValid.setText("Valid Input");
			} else {
				baValid.setForeground(Color.RED);
				baValid.setText("Invalid Input");
			}
			errorFrame.add(baValid, c);
			
			c.gridx = 1; c.gridy = 5;
			errorFrame.add(new JLabel("Strip End must be integer"), c);
			c.gridx = 0;
			JLabel seValid = new JLabel();
			if (se) {
				seValid.setForeground(Color.GREEN);
				seValid.setText("Valid Input");
			} else {
				seValid.setForeground(Color.RED);
				seValid.setText("Invalid Input");
			}
			errorFrame.add(seValid, c);
			
			c.gridx = 1; c.gridy = 6;
			errorFrame.add(new JLabel("Short Angle Log. Runway TORA must be integer between 0 and 10000 and can't be less than runway length"), c);
			c.gridx = 0;
			JLabel sTORAValid = new JLabel();
			if (sTORA) {
				sTORAValid.setForeground(Color.GREEN);
				sTORAValid.setText("Valid Input");
			} else {
				sTORAValid.setForeground(Color.RED);
				sTORAValid.setText("Invalid Input");
			}
			errorFrame.add(sTORAValid, c);

			c.gridx = 1; c.gridy = 7;
			errorFrame.add(new JLabel("Short Angle Log. Runway TODA must be integer between 0 and 10000 and can't be less than ASDA"), c);
			c.gridx = 0;
			JLabel sTODAValid = new JLabel();
			if (sTODA) {
				sTODAValid.setForeground(Color.GREEN);
				sTODAValid.setText("Valid Input");
			} else {
				sTODAValid.setForeground(Color.RED);
				sTODAValid.setText("Invalid Input");
			}
			errorFrame.add(sTODAValid, c);
			
			c.gridx = 1; c.gridy = 8;
			errorFrame.add(new JLabel("Short Angle Log. Runway ASDA must be integer between 0 and 10000 and can't be less than TORA"), c);
			c.gridx = 0;
			JLabel sASDAValid = new JLabel();
			if (sASDA) {
				sASDAValid.setForeground(Color.GREEN);
				sASDAValid.setText("Valid Input");
			} else {
				sASDAValid.setForeground(Color.RED);
				sASDAValid.setText("Invalid Input");
			}
			errorFrame.add(sASDAValid, c);
			
			c.gridx = 1; c.gridy = 9;
			errorFrame.add(new JLabel("Short Angle Log. Runway LDA must be integer between 0 and 10000 and can't be more than TORA"), c);
			c.gridx = 0;
			JLabel sLDAValid = new JLabel();
			if (sLDA) {
				sLDAValid.setForeground(Color.GREEN);
				sLDAValid.setText("Valid Input");
			} else {
				sLDAValid.setForeground(Color.RED);
				sLDAValid.setText("Invalid Input");
			}
			errorFrame.add(sLDAValid, c);
			
			c.gridx = 1; c.gridy = 10;
			errorFrame.add(new JLabel("Long Angle Log. Runway TORA must be integer between 0 and 10000 and can't be less than runway length"), c);
			c.gridx = 0;
			JLabel lTORAValid = new JLabel();
			if (lTORA) {
				lTORAValid.setForeground(Color.GREEN);
				lTORAValid.setText("Valid Input");
			} else {
				lTORAValid.setForeground(Color.RED);
				lTORAValid.setText("Invalid Input");
			}
			errorFrame.add(lTORAValid, c);

			c.gridx = 1; c.gridy = 11;
			errorFrame.add(new JLabel("Long Angle Log. Runway TODA must be integer between 0 and 10000 and can't be less than ASDA"), c);
			c.gridx = 0;
			JLabel lTODAValid = new JLabel();
			if (lTODA) {
				lTODAValid.setForeground(Color.GREEN);
				lTODAValid.setText("Valid Input");
			} else {
				lTODAValid.setForeground(Color.RED);
				lTODAValid.setText("Invalid Input");
			}
			errorFrame.add(lTODAValid, c);
			
			c.gridx = 1; c.gridy = 12;
			errorFrame.add(new JLabel("Long Angle Log. Runway ASDA must be integer between 0 and 10000 and can't be less than TORA"), c);
			c.gridx = 0;
			JLabel lASDAValid = new JLabel();
			if (lASDA) {
				lASDAValid.setForeground(Color.GREEN);
				lASDAValid.setText("Valid Input");
			} else {
				lASDAValid.setForeground(Color.RED);
				lASDAValid.setText("Invalid Input");
			}
			errorFrame.add(lASDAValid, c);
			
			c.gridx = 1; c.gridy = 13;
			errorFrame.add(new JLabel("Long Angle Log. Runway LDA must be integer between 0 and 10000 and can't be more than TORA"), c);
			c.gridx = 0;
			JLabel lLDAValid = new JLabel();
			if (lLDA) {
				lLDAValid.setForeground(Color.GREEN);
				lLDAValid.setText("Valid Input");
			} else {
				lLDAValid.setForeground(Color.RED);
				lLDAValid.setText("Invalid Input");
			}
			errorFrame.add(lLDAValid, c);
			
			errorFrame.pack();
			errorFrame.setLocationRelativeTo(null);
			errorFrame.setVisible(true);
			
			return false;
		}
		return true;
	}

	String sDes, lDes;
	public void makeRunway() {
		runway = new Runway(resa, blast, strip, length, width);
		int sAngle, lAngle;
		if (angle >= 18) {
			lAngle = angle;
			sAngle = (angle + 18) % 36;
		} else {
			sAngle = angle;
			lAngle = (angle + 18) % 36;
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
		
		LogicalRunway s = new LogicalRunway(sDes, runway, sTor, sTod, sAsd, sLd);
		LogicalRunway l = new LogicalRunway(lDes, runway, lTor, lTod, lAsd, lLd);
		runway.setLogicalRunways(s, l);
	}
	
	String r1Des, r2Des;
	JComboBox<String> choiceBox1, choiceBox2, choiceBox3;
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
		lrFrame.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5,5,5,5);
		
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEtchedBorder());
		panel.setLayout(new GridBagLayout());
		
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
		
		c.gridx = 0; c.gridy = 0;
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
				runway.lowAngle().designator = r1.lowAngle().designator.substring(0, 2) + newDes1;
				runway.highAngle().designator = r1.highAngle().designator.substring(0, 2) + opNewDes1;
				
				runway.setDesignator();

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
		c.gridx = 0; c.gridy = 1;
		lrFrame.add(confirm, c);
		
		lrFrame.pack();
		lrFrame.setVisible(true);
		return true;
	}
	
	public void addRunway() {
		if (!edit) {
			controller.selectedAirport.addRunway(runway);
			controller.updateCombo(runway);
		}
	}
}