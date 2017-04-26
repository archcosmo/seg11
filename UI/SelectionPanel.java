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
		//gbc.fill = GridBagConstraints.BOTH;
		
//		JPanel emptyPanel1 = new JPanel();
//		JPanel exportViewPanel = new JPanel();
//		JPanel runwayPanel = new JPanel(new GridLayout(2, 1));
//		JPanel designatorPanel = new JPanel(new GridLayout(2, 1));
//		JPanel planePositionPanel = new JPanel(new GridLayout(2, 1));
//		JPanel colourSchemePanel = new JPanel(new GridLayout(2, 1));
//		JPanel scalePanel = new JPanel(new GridLayout(2, 1));
//		JPanel rotationPanel = new JPanel(new GridLayout(2, 1));
//		JPanel obstaclePanel = new JPanel(new GridLayout(2, 1));
//		JPanel emptyPanel2 = new JPanel();
//		JPanel obstacleThresholdDistancePanel = new JPanel(new GridLayout(2, 1));
//		JPanel obstacleCentrelineDistancePanel = new JPanel(new GridLayout(2, 1));
		
		//Add borders to all panels
//		exportViewPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 18, 10));
//		runwayPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 18, 10));
//		designatorPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 18, 10));
//		planePositionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 18, 10));
//		colourSchemePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 18, 10));
//		scalePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 18, 10));
//		rotationPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 18, 10));
//		obstaclePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 18, 10));
//		obstacleThresholdDistancePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 18, 10));
//		obstacleCentrelineDistancePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 18, 10));


		//RUNWAY LABEL
		gbc.gridwidth = 4;
		
		JLabel runwayLabel = new JLabel("Select Runway");
		runwayLabel.setFont(labelFont);
		add(runwayLabel, gbc);
		//JPanel runwaySelectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

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
		//JLabel spaceMaker = new JLabel("");
		//spaceMaker.setPreferredSize(new Dimension(10, 20));
		//runwaySelectionPanel.add(spaceMaker);

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
			RunwayEditPanel runwayEditPanel = new RunwayEditPanel(CONTROLLER);
			new AddRunwayFrame(CONTROLLER);
		});
		
		removeRunway.addActionListener(e -> {
			CONTROLLER.uiDraw();
		});

		//todo add create runway popup in button listener
		// todo edit runway popup + event listener
		//todo remove runway even listener
		removeRunway.addActionListener(e -> {
			CONTROLLER.selectedAirport.removeRunway(CONTROLLER.selectedRunway);
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

		//todo add scale and rotation listeners to update labels and view
		//todo notify controller when scale/rotation changed?
		
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
		
		//todo add create obstacle popup in button listener
		// todo edit obstacle popup + event listener
		//todo remove obstacle even listener


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
			if (CONTROLLER.selectedObstacle != null) CONTROLLER.obstacles.remove(CONTROLLER.selectedObstacle);
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
		gbc.gridx = 4;
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

	public void updateObstacles(Obstacle o) {
		obstacleComboBox.addItem(o.getName());
	}
}
