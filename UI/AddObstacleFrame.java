package UI;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Application.Controller;
import Model.Obstacle;

public class AddObstacleFrame {
	Controller controller;
	Obstacle obstacle;
	JTextField nameField, lengthField, widthField, heightField;
	String name; 
	int length, width, height;
	Boolean edit = false;
	JFrame errorFrame;
	
	public AddObstacleFrame(Controller c) {
		controller = c;
		init();
	}
	
	public AddObstacleFrame(Controller c, Obstacle o) {
		controller = c;
		edit = true;
		obstacle = o;
		init();
		nameField.setText(o.getName());
		nameField.setEditable(false);
		lengthField.setText(Integer.toString(o.length));
		widthField.setText(Integer.toString(o.width));
		heightField.setText(Integer.toString(o.height));
	}

	public void init() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		frame.setLayout(gridBagLayout);
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5,5,5,5);
		c.weightx = 1.0;
		c.weighty = 1.0;
		
		/* ========================================== */
		
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEtchedBorder());
		panel.setLayout(gridBagLayout);
		c.fill = GridBagConstraints.BOTH;
		
		c.gridx = 0; c.gridy = 0;
		panel.add(new JLabel("Name"), c);
		c.gridx = 1;
		nameField = new JTextField();
		nameField.setColumns(5);
		panel.add(nameField, c);

		c.gridx = 0; c.gridy = 1;
		panel.add(new JLabel("Length (m)"), c);
		c.gridx = 1;
		lengthField = new JTextField();
		lengthField.setColumns(5);
		panel.add(lengthField, c);
		
		c.gridx = 0; c.gridy = 2;
		panel.add(new JLabel("Width (m)"), c);
		c.gridx = 1;
		widthField = new JTextField();
		widthField.setColumns(5);
		panel.add(widthField, c);
		
		c.gridx = 0; c.gridy = 3;
		panel.add(new JLabel("Height (m)"), c);
		c.gridx = 1;
		heightField = new JTextField();
		heightField.setColumns(5);
		panel.add(heightField, c);
		
		c.gridx = 0; c.gridy = 4;
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(e -> {
			if (errorFrame != null) errorFrame.dispose();
			frame.dispose();
		});
		panel.add(cancel, c);
		
		c.gridx = 1;
		JButton confirm = new JButton("Confirm");
		confirm.addActionListener(e -> {
			if (checkValues()) {
				/* ================================================== */
				addObstacle();
				if (errorFrame != null) errorFrame.dispose();
				frame.dispose();
			}
		});
		panel.add(confirm, c);

		c.insets = new Insets(2,2,1,1);
		frame.add(panel, c);

		frame.setTitle("Add/Edit Obstacle");
		frame.pack();
		frame.setVisible(true);
	}
	
	public boolean checkValues() {
		boolean named = false;
		String nameFieldText = nameField.getText();
		if (nameFieldText.matches("[a-zA-Z0-9][a-zA-Z0-9 ]+")) {
			for (Obstacle obstacle : controller.getObstacles()) {
				if (nameFieldText.equals(obstacle)) {
					break;
				}
			}
			name = nameFieldText;
			named = true;
		}
		
		boolean len = false;
		if (lengthField.getText().matches("\\d+")) {
			length = Integer.parseInt(lengthField.getText());
			if (length <= 10000 && length > 0) len = true;
		}
		
		boolean wid = false;
		if (widthField.getText().matches("\\d+")) {
			width = Integer.parseInt(widthField.getText());
			if (width <= 5000 && width > 0) wid = true;
		}
		
		boolean hei = false;
		if (heightField.getText().matches("\\d+")) {
			height = Integer.parseInt(heightField.getText());
			if (height <= 100 && height > 0) hei = true;
		}
		
		if (!(named && len && wid && hei)) {
			if (errorFrame != null)	errorFrame.dispose();
			errorFrame = new JFrame();
			errorFrame.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			c.anchor = GridBagConstraints.WEST;
			c.insets = new Insets(2,2,2,2);
			
			c.gridx = 1; c.gridy = 0;
			errorFrame.add(new JLabel("Name must not be blank"), c);
			c.gridx = 0;
			JLabel nameValid = new JLabel();
			if (named) {
				nameValid.setForeground(Color.GREEN);
				nameValid.setText("Valid Input");
			} else {
				nameValid.setForeground(Color.RED);
				nameValid.setText("Invalid Input");
			}
			errorFrame.add(nameValid, c);
			
			c.gridx = 1; c.gridy = 1;
			errorFrame.add(new JLabel("Length must be integer between 1 and 1000"), c);
			c.gridx = 0;
			JLabel lengthValid = new JLabel();
			if (len) {
				lengthValid.setForeground(Color.GREEN);
				lengthValid.setText("Valid Input");
			} else {
				lengthValid.setForeground(Color.RED);
				lengthValid.setText("Invalid Input");
			}
			errorFrame.add(lengthValid, c);
			
			c.gridx = 1; c.gridy = 2;
			errorFrame.add(new JLabel("Width must be integer between 1 and 5000"), c);
			c.gridx = 0;
			JLabel widthValid = new JLabel();
			if (wid) {
				widthValid.setForeground(Color.GREEN);
				widthValid.setText("Valid Input");
			} else {
				widthValid.setForeground(Color.RED);
				widthValid.setText("Invalid Input");
			}
			errorFrame.add(widthValid, c);
			
			c.gridx = 1; c.gridy = 3;
			errorFrame.add(new JLabel("Height must be integer between 1 and 500"), c);
			c.gridx = 0;
			JLabel heightValid = new JLabel();
			if (hei) {
				heightValid.setForeground(Color.GREEN);
				heightValid.setText("Valid Input");
			} else {
				heightValid.setForeground(Color.RED);
				heightValid.setText("Invalid Input");
			}
			errorFrame.add(heightValid, c);
			
			errorFrame.pack();
			errorFrame.setLocationRelativeTo(null);
			errorFrame.setVisible(true);
			
			return false;
		}
		
		return true;
	}
	
	public void addObstacle() {
		if (!edit) {
			obstacle = new Obstacle(name, width, length, height);
			controller.obstacles.add(obstacle);
			controller.updateCombo(obstacle);
		}
		else {
			obstacle.length = length;
			obstacle.width = width;
			obstacle.height = height;
		}
	}
}
