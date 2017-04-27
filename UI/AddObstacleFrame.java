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

import static UI.ValidateValue.createValidationTitleJLabel;
import static UI.ValidateValue.validateNumber;

public class AddObstacleFrame {
	Controller controller;
	Obstacle obstacle;
	JTextField nameField, lengthField, widthField, heightField;
	String name;
	ValidateValue lengthVal, widthVal, heightVal;
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
		String nameValidation = "";
		String nameFieldText = nameField.getText();
		if (nameFieldText.matches("[a-zA-Z0-9][a-zA-Z0-9 ]+")) {
			for (Obstacle obstacle : controller.getObstacles()) {
				if (nameFieldText.equals(obstacle.getName())) {
					nameValidation = "There is already an object with this name.";
					break;
				}
			}
			name = nameFieldText;
			named = true;
		} else {
			if (nameFieldText.length() < 2) {
				nameValidation = "Name must be at least 2 characters.";
			} else {
				nameValidation = "Can only use letters, numbers and spaces.";
			}
		}

		lengthVal = validateNumber(lengthField.getText(), 0, 10000);
		widthVal = validateNumber(widthField.getText(), 0, 5000);
		heightVal = validateNumber(heightField.getText(), 0, 100);
		
		if (!(named && lengthVal.valid && widthVal.valid && heightVal.valid)) {
			if (errorFrame != null)	errorFrame.dispose();
			errorFrame = new JFrame();
			errorFrame.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			c.anchor = GridBagConstraints.WEST;
			c.insets = new Insets(2,2,2,2);


			c.gridx = 0; c.gridy = 0;
			JLabel nameLabel = new JLabel("Name");
			if (named) {
				nameLabel.setForeground(Color.GREEN);
			} else {
				nameLabel.setForeground(Color.RED);
			}
			errorFrame.add(nameLabel, c);
			c.gridx = 1;
			errorFrame.add(new JLabel(nameValidation), c);


			c.gridx = 0; c.gridy = 1;
			errorFrame.add(createValidationTitleJLabel(lengthVal, "Length"), c);
			c.gridx = 1;
			errorFrame.add(new JLabel(lengthVal.validationString), c);

			c.gridx = 0; c.gridy = 2;
			errorFrame.add(createValidationTitleJLabel(widthVal, "Width"), c);
			c.gridx = 1;
			errorFrame.add(new JLabel(widthVal.validationString), c);

			c.gridx = 0; c.gridy = 3;
			errorFrame.add(createValidationTitleJLabel(heightVal, "height"), c);
			c.gridx = 1;
			errorFrame.add(new JLabel(heightVal.validationString), c);
			
			errorFrame.pack();
			errorFrame.setLocationRelativeTo(null);
			errorFrame.setVisible(true);
			
			return false;
		}
		
		return true;
	}
	
	public void addObstacle() {
		if (!edit) {
			obstacle = new Obstacle(name, widthVal.value, lengthVal.value, heightVal.value);
			controller.obstacles.add(obstacle);
			controller.notify("Obstacle added: " + name);
			controller.updateCombo(obstacle);
		}
		else {
			obstacle.length = lengthVal.value;
			obstacle.width = widthVal.value;
			obstacle.height = heightVal.value;
		}
	}
}
