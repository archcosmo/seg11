package UI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Application.Controller;
import Model.ColourScheme;
import Model.Obstacle;
import Model.Runway;

@SuppressWarnings("serial")
public class SelectionPanel extends JPanel
{
	Controller CONTROLLER;
	JTextField xPosInput;
	JTextField yPosInput;
	JComboBox<String> runwayComboBox;
	JComboBox<String> obstacleComboBox;

	public SelectionPanel(Controller c) 
	{
		CONTROLLER = c;
		initPanel();
	}

	private void initPanel() 
	{
		setLayout(new GridBagLayout());
		setPreferredSize(new Dimension(420, 390));
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		addComponents();
	}

	/* Create menu */
	private void addComponents() 
	{

		Font labelFont = this.getFont().deriveFont(16.0f);
		Font notLabelFont = this.getFont().deriveFont(16.0f);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets.bottom = 8;
		gbc.insets.left = 5;

		//RUNWAY LABEL
		gbc.gridwidth = 4;

		JLabel runwayLabel = new JLabel("Select Runway");
		runwayLabel.setFont(labelFont);
		add(runwayLabel, gbc);

		//RUNWAY COMBOBOX
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.weightx = 0.4;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		runwayComboBox = new JComboBox<String>();

		runwayComboBox.setPreferredSize(new Dimension(75, 30));
		runwayComboBox.setFont(notLabelFont);
		for (Runway r: CONTROLLER.getRunways())
		{
			runwayComboBox.addItem(r.getName());
		}
		runwayComboBox.addActionListener(e -> {
			CONTROLLER.selectRunway((String) runwayComboBox.getSelectedItem());
			CONTROLLER.notify("Runway selected : "+ (String) runwayComboBox.getSelectedItem());
		});
		add(runwayComboBox, gbc);

		gbc.weightx =0;
		gbc.fill = GridBagConstraints.NONE;

		//Runway add, remove and edit
		JButton addRunway = new JButton("+");
		JButton removeRunway = new JButton("-");
		JButton editRunway = new JButton("✎");

		addRunway.setMargin(new Insets(0, 0, 0, 0));
		removeRunway.setMargin(new Insets(0, 0, 0, 0));
		editRunway.setMargin(new Insets(0, 0, 0, 0));

		addRunway.setFont(labelFont);
		removeRunway.setFont(labelFont);
		editRunway.setFont(labelFont);

		addRunway.setPreferredSize(new Dimension(30, 30));
		removeRunway.setPreferredSize(new Dimension(30, 30));
		editRunway.setPreferredSize(new Dimension(30, 30));

		addRunway.addActionListener(e -> {
			new AddRunwayFrame(CONTROLLER);
		});

		removeRunway.addActionListener(e -> {
			CONTROLLER.uiDraw();
		});

		removeRunway.addActionListener(e -> {
			if (runwayComboBox.getItemCount() > 1) {
				int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove the runway " + 
						CONTROLLER.selectedRunway.getName() + "?", "Remove Runway?",  JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					CONTROLLER.selectedAirport.removeRunway(CONTROLLER.selectedRunway);
					updateRunways();
				}
			}
		});
		editRunway.addActionListener(e -> {
			new AddRunwayFrame(CONTROLLER, CONTROLLER.selectedRunway);
			CONTROLLER.selectRunway((String) runwayComboBox.getSelectedItem());
		});

		gbc.gridy = 1;
		gbc.weightx = 0.2;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.gridx = 1;
		add(addRunway, gbc);

		gbc.gridx = 2;
		add(removeRunway, gbc);

		gbc.gridx = 3;
		add(editRunway, gbc);

		gbc.weightx = 0;
		gbc.fill = GridBagConstraints.NONE;

		//DESIGNATOR LABEL
		gbc.gridx = 4;
		gbc.gridy = 0;
		gbc.gridwidth = 1;

		JLabel designatorLabel = new JLabel("Select Designator");
		designatorLabel.setFont(labelFont);
		add(designatorLabel, gbc);

		//DESIGNATOR COMBOBOX		
		gbc.gridy = 1;
		gbc.insets.right = 10;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JComboBox<String> designatorComboBox = new JComboBox<String>();
		designatorComboBox.setFont(notLabelFont);
		designatorComboBox.addItem(CONTROLLER.selectedRunway.lowAngle().designator);
		designatorComboBox.addItem(CONTROLLER.selectedRunway.highAngle().designator);
		designatorComboBox.addActionListener(e -> {
			CONTROLLER.setRunwayAngle(designatorComboBox.getSelectedIndex() == 0);
			CONTROLLER.notify("Designator selected : "+ (String) designatorComboBox.getSelectedItem());
		});
		add(designatorComboBox, gbc);

		gbc.fill = GridBagConstraints.NONE;
		gbc.insets.right = 0;

		//PLANE POSITION LABEL
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 4;
		gbc.insets.top = 10;

		JLabel planePositionLabel = new JLabel("Plane Position");
		planePositionLabel.setFont(labelFont);
		add(planePositionLabel, gbc);

		gbc.insets.top = 0;

		//PLANE POSITION COMBOBOX
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JComboBox<String> planePositionComboBox = new JComboBox<String>();
		planePositionComboBox.setFont(notLabelFont);
		planePositionComboBox.addItem("Towards obstacle");
		planePositionComboBox.addItem("Away from obstacle");
		planePositionComboBox.addActionListener(e -> {
			CONTROLLER.setTakeoffDirection((String) planePositionComboBox.getSelectedItem());
			CONTROLLER.notify("Plane position selected : "+ (String) planePositionComboBox.getSelectedItem());
		});
		add(planePositionComboBox, gbc);

		gbc.fill = GridBagConstraints.NONE;

		//COLOUR SCHEME LABEL
		gbc.gridx = 4;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.insets.top = 10;

		JLabel colourSchemeLabel = new JLabel("Colour Scheme");
		colourSchemeLabel.setFont(labelFont);
		add(colourSchemeLabel, gbc);

		gbc.insets.top = 0;

		//COLOUR SCHEME COMBOBOX
		gbc.gridx = 4;
		gbc.gridy = 3;
		gbc.insets.right = 10;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JComboBox<ColourScheme> colourSchemeComboBox = new JComboBox<ColourScheme>();
		colourSchemeComboBox.setFont(notLabelFont);
		for(ColourScheme cs : CONTROLLER.getColourSchemes()) {
			colourSchemeComboBox.addItem(cs);
		}
		colourSchemeComboBox.addActionListener(e -> {
			CONTROLLER.setColourScheme(colourSchemeComboBox.getItemAt(colourSchemeComboBox.getSelectedIndex()));
			CONTROLLER.notify("Colour scheme selected : "+ colourSchemeComboBox.getSelectedItem().toString());
		});
		add(colourSchemeComboBox, gbc);

		gbc.insets.right = 0;
		gbc.fill = GridBagConstraints.NONE;

		//TODO add scale and rotation listeners to update labels and view

		//SCALE LABEL
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 4;
		gbc.insets.top = 10;

		JLabel scaleLabel = new JLabel("View Scale: x1");
		scaleLabel.setFont(labelFont);
		add(scaleLabel, gbc);

		gbc.insets.top = 0;

		//SCALE SLIDER
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 4;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JSlider scaleSlider = new JSlider(0, 100, 1);
		scaleSlider.addChangeListener(l -> {
			try {
				float result = CONTROLLER.setViewZoom(scaleSlider.getValue());
				scaleLabel.setText(String.format("View Scale: x%.2f", result));
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(this, e1.getMessage());
			}
		});
		add(scaleSlider, gbc);

		gbc.fill = GridBagConstraints.NONE;

		//ROTATION LABEL
		gbc.gridx = 4;
		gbc.gridy = 4;
		gbc.gridwidth = 1;
		gbc.insets.top = 10;

		JLabel rotationLabel = new JLabel("View Rotation: 90°"); //Alt + 248 = °
		rotationLabel.setFont(labelFont);
		add(rotationLabel, gbc);

		gbc.insets.top = 0;

		//ROTATION SLIDER
		gbc.gridx = 4;
		gbc.gridy = 5;
		gbc.gridwidth = 1;
		gbc.insets.right = 10;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JSlider rotationSlider = new JSlider(0, 359, 90);
		add(rotationSlider, gbc);

		gbc.fill = GridBagConstraints.NONE;
		gbc.insets.right = 0;

		//OBSTACLE LABEL
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.gridwidth = 4;
		gbc.insets.top = 40;

		JLabel obstacleLabel = new JLabel("Select obstacle");
		obstacleLabel.setFont(labelFont);
		add(obstacleLabel, gbc);

		gbc.insets.top = 0;

		//OBSTACLE COMBOBOX
		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.gridwidth = 1;
		gbc.weightx = 0.4;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		obstacleComboBox = new JComboBox<String>();
		obstacleComboBox.setPreferredSize(new Dimension(100, 30));
		obstacleComboBox.setFont(notLabelFont);
		obstacleComboBox.addItem("None");
		for (Obstacle o: CONTROLLER.getObstacles())
		{
			obstacleComboBox.addItem(o.getName());
		}
		obstacleComboBox.addActionListener(e -> {
			CONTROLLER.selectObstacle((String) obstacleComboBox.getSelectedItem());
			CONTROLLER.notify("Obstacle selected : "+ (String) obstacleComboBox.getSelectedItem());
		});
		add(obstacleComboBox, gbc);

		gbc.weightx = 0;
		gbc.fill = GridBagConstraints.NONE;

		//Obstacle add, remove and edit
		JButton addObstacle = new JButton("+");
		JButton removeObstacle = new JButton("-");
		JButton editObstacle = new JButton("✎");

		addObstacle.setMargin(new Insets(0, 0, 0, 0));
		removeObstacle.setMargin(new Insets(0, 0, 0, 0));
		editObstacle.setMargin(new Insets(0, 0, 0, 0));

		addObstacle.setFont(labelFont);
		removeObstacle.setFont(labelFont);
		editObstacle.setFont(labelFont);

		addObstacle.setPreferredSize(new Dimension(30, 30));
		removeObstacle.setPreferredSize(new Dimension(30, 30));
		editObstacle.setPreferredSize(new Dimension(30, 30));


		addObstacle.addActionListener(e -> {
			new AddObstacleFrame(CONTROLLER);
		});
		editObstacle.addActionListener(e -> {
			if (CONTROLLER.selectedObstacle != null) {
				new AddObstacleFrame(CONTROLLER, CONTROLLER.selectedObstacle);
				CONTROLLER.selectObstacle((String) obstacleComboBox.getSelectedItem());
			}
		});
		removeObstacle.addActionListener(e -> {
			if (CONTROLLER.selectedObstacle != null) {
				int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove " + 
						CONTROLLER.selectedObstacle.getName() + "?", "Remove Obstacle",  JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					CONTROLLER.obstacles.remove(CONTROLLER.selectedObstacle);
					updateObstacles();
				}
			}
		});

		gbc.weightx = 0.2;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.gridx = 1;
		add(addObstacle, gbc);

		gbc.gridx = 2;
		add(removeObstacle, gbc);

		gbc.gridx = 3;
		add(editObstacle, gbc);

		gbc.weightx = 0;
		gbc.fill = GridBagConstraints.NONE;

		//OBSTACLE CENTERLINE LABEL
		gbc.gridx = 0;
		gbc.gridy = 8;
		gbc.gridwidth = 4;
		gbc.insets.top = 10;

		JLabel centrelineLabel = new JLabel("Distance from centreline");
		centrelineLabel.setFont(labelFont);
		add(centrelineLabel, gbc);

		gbc.insets.top = 0;

		//OBSTACLE CENTERLINE TEXTFIELD
		gbc.gridx = 0;
		gbc.gridy = 9;
		gbc.gridwidth = 4;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipady = 5;

		JTextField centrelineDistanceTextField = new JTextField();
		centrelineDistanceTextField.getDocument().addDocumentListener( new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent e) {
				if (CONTROLLER.selectedObstacle == null || centrelineDistanceTextField.getText() == "" || !CONTROLLER.selectObstacleXPos(centrelineDistanceTextField.getText()));	
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				if (CONTROLLER.selectedObstacle == null || centrelineDistanceTextField.getText() == "" || !CONTROLLER.selectObstacleXPos(centrelineDistanceTextField.getText()));	
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				if (CONTROLLER.selectedObstacle == null || centrelineDistanceTextField.getText() == "" || !CONTROLLER.selectObstacleXPos(centrelineDistanceTextField.getText()));	
			}
		});
		add(centrelineDistanceTextField, gbc);

		gbc.fill = GridBagConstraints.NONE;
		gbc.ipady = 0;

		//OBSTACLE THRESHOLD DISTANCE LABEL
		gbc.gridx = 4;		//TODO notify controller when scale/rotation changed?
		gbc.gridy = 8;
		gbc.gridwidth = 1;
		gbc.insets.top = 10;

		JLabel thresholdLabel = new JLabel("Distance from threshold");
		thresholdLabel.setFont(labelFont);
		add(thresholdLabel, gbc);

		gbc.insets.top = 0;

		//OBSTACLE THRESHOLD DISTANCE TEXTFIELD
		gbc.gridx = 4;
		gbc.gridy = 9;
		gbc.gridwidth = 1;
		gbc.insets.right = 10;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipady = 5;

		JTextField thresholdDistanceTextField = new JTextField();
		thresholdDistanceTextField.getDocument().addDocumentListener(new DocumentListener()
				{

					@Override
					public void changedUpdate(DocumentEvent arg0) {
						if (CONTROLLER.selectedObstacle == null || thresholdDistanceTextField.getText() == "" || !CONTROLLER.selectObstacleYPos(thresholdDistanceTextField.getText()));	
					}

					@Override
					public void insertUpdate(DocumentEvent arg0) {
						if (CONTROLLER.selectedObstacle == null || thresholdDistanceTextField.getText() == "" || !CONTROLLER.selectObstacleYPos(thresholdDistanceTextField.getText()));	
					}

					@Override
					public void removeUpdate(DocumentEvent arg0) {
						if (CONTROLLER.selectedObstacle == null || thresholdDistanceTextField.getText() == "" || !CONTROLLER.selectObstacleYPos(thresholdDistanceTextField.getText()));	
					}
				}
				);
				
				
				
				/*e -> {
			if (!CONTROLLER.selectObstacleYPos(thresholdDistanceTextField.getText()))
				thresholdDistanceTextField.setText("0");
		}); */
		add(thresholdDistanceTextField, gbc);

		gbc.insets.right = 0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.ipady = 0;
	}

	public void updateRunways(Runway r) {
		runwayComboBox.addItem(r.getName());
	}
	public void updateRunways() {
		int size = runwayComboBox.getItemCount();
		for (Runway run : CONTROLLER.selectedAirport.getRunways()) {
			runwayComboBox.addItem(run.getName());
		}
		for (int i = 0; i < size; i++) {
			runwayComboBox.removeItemAt(0);
		}
	}

	public void updateObstacles(Obstacle o) {
		obstacleComboBox.addItem(o.getName());
	}
	public void updateObstacles() {
		int size = obstacleComboBox.getItemCount();
		obstacleComboBox.addItem("None");
		for (Obstacle ob : CONTROLLER.getObstacles()) {
			obstacleComboBox.addItem(ob.getName());
		}
		for (int i = 0; i < size; i++) {
			obstacleComboBox.removeItemAt(0);
		}
	}
}
