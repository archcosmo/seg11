package UI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Application.Controller;

@SuppressWarnings("serial")
public class DrawPreferencesFrame extends JPanel {

	Window PARENT;
	Controller CONTROLLER;
	
	public DrawPreferencesFrame(Window parent, Controller controller) {
		PARENT = parent;
		CONTROLLER = controller;
		
		init();
	}
	
	private void init() {
		setLayout(new GridBagLayout());
		
		initComponents();
		
		setSize(420, 300);
		setLocation(PARENT.getLocation().x + PARENT.getWidth(), PARENT.getLocation().y + PARENT.getHeight() - getHeight());
		setVisible(true);
	}
	
	private void initComponents() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = gbc.gridy = 0;		
		
		JCheckBox drawLabels = new JCheckBox("Draw Labels");
		drawLabels.setSelected(true);
		drawLabels.addChangeListener(l -> {
			try {
				CONTROLLER.setDrawPreference("drawLabels", drawLabels.isSelected());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, e.getMessage());
			}
		});
		add(drawLabels, gbc);
		
		JCheckBox labelMeasurements = new JCheckBox("Draw Labels on Measurements");
		labelMeasurements.setSelected(true);
		labelMeasurements.addChangeListener(l -> {
			try {
				CONTROLLER.setDrawPreference("labelMeasurements", labelMeasurements.isSelected());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, e.getMessage());
			}
		});
		
		gbc.gridy++;
		add(labelMeasurements, gbc);
		
		JCheckBox displayDistancesOnMeasurements = new JCheckBox("Draw Distance on Measurements");
		displayDistancesOnMeasurements.setSelected(true);
		displayDistancesOnMeasurements.addChangeListener(l -> {
			try {
				CONTROLLER.setDrawPreference("displayDistancesOnMeasurements", displayDistancesOnMeasurements.isSelected());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, e.getMessage());
			}
		});
		
		gbc.gridy++;
		add(displayDistancesOnMeasurements, gbc);
		
		JCheckBox drawOrigMeasurements = new JCheckBox("Draw Original Measurements");
		drawOrigMeasurements.setSelected(true);
		drawOrigMeasurements.addChangeListener(l -> {
			try {
				CONTROLLER.setDrawPreference("drawOrigMeasurements", drawOrigMeasurements.isSelected());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, e.getMessage());
			}
		});
		
		gbc.gridy++;
		add(drawOrigMeasurements, gbc);
		
		JCheckBox drawLegend = new JCheckBox("Draw Legend");
		drawLegend.setSelected(true);
		drawLegend.addChangeListener(l -> {
			try {
				CONTROLLER.setDrawPreference("drawLegend", drawLegend.isSelected());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, e.getMessage());
			}
		});
		
		gbc.gridy++;
		add(drawLegend, gbc);
		
		JCheckBox drawDirection = new JCheckBox("Draw Takeoff/Landing Direction Arrow");
		drawDirection.setSelected(true);
		drawDirection.addChangeListener(l -> {
			try {
				CONTROLLER.setDrawPreference("drawDirection", drawDirection.isSelected());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, e.getMessage());
			}
		});
		
		gbc.gridy++;
		add(drawDirection, gbc);
		
		JCheckBox drawCompass = new JCheckBox("Draw Compass Heading");
		drawCompass.setSelected(true);
		drawCompass.addChangeListener(l -> {
			try {
				CONTROLLER.setDrawPreference("drawCompass", drawCompass.isSelected());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, e.getMessage());
			}
		});
		
		gbc.gridy++;
		add(drawCompass, gbc);
	}
}
