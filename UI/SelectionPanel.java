package UI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

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
	
	public SelectionPanel(Controller c) 
	{
		CONTROLLER = c;
		initPanel();
	}

	private void initPanel() 
	{
		setLayout(new GridLayout(6, 2));
		setPreferredSize(new Dimension(500, 0));
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		addComponents();
	}

	/* Create menu */
	private void addComponents() 
	{
		Font labelFont = this.getFont().deriveFont(20.0f);
		Font notLabelFont = this.getFont().deriveFont(17.0f);

		JPanel emptyPanel1 = new JPanel();
		JPanel exportViewPanel = new JPanel();
		JPanel runwayPanel = new JPanel(new GridLayout(2, 1));
		JPanel designatorPanel = new JPanel(new GridLayout(2, 1));
		JPanel planePositionPanel = new JPanel(new GridLayout(2, 1));
		JPanel colourSchemePanel = new JPanel(new GridLayout(2, 1));
		JPanel scalePanel = new JPanel(new GridLayout(2, 1));
		JPanel rotationPanel = new JPanel(new GridLayout(2, 1));
		JPanel obstaclePanel = new JPanel(new GridLayout(2, 1));
		JPanel emptyPanel2 = new JPanel();
		JPanel obstacleThresholdDistancePanel = new JPanel(new GridLayout(2, 1));
		JPanel obstacleCentrelineDistancePanel = new JPanel(new GridLayout(2, 1));
		
		//Add borders to all panels
		exportViewPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 18, 10));
		runwayPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 18, 10));
		designatorPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 18, 10));
		planePositionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 18, 10));
		colourSchemePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 18, 10));
		scalePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 18, 10));
		rotationPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 18, 10));
		obstaclePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 18, 10));
		obstacleThresholdDistancePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 18, 10));
		obstacleCentrelineDistancePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 18, 10));



		add(emptyPanel1);

		//Export view button
		JButton exportViewButton = new JButton("Export view");
		//todo add export view functionality
		exportViewPanel.add(exportViewButton);
		add(exportViewPanel);

		//RUNWAY PANEL
		JLabel runwayLabel = new JLabel("Select Runway");
		runwayLabel.setFont(labelFont);
		runwayPanel.add(runwayLabel);
		JPanel runwaySelectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		//Runway ComboBox
		JComboBox<String> runwayComboBox = new JComboBox<String>();
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
		runwaySelectionPanel.add(runwayComboBox);
		//JLabel spaceMaker = new JLabel("");
		//spaceMaker.setPreferredSize(new Dimension(10, 20));
		//runwaySelectionPanel.add(spaceMaker);

		//Runway add, remove and edit
		JButton addRunway = new JButton("+");
		JButton removeRunway = new JButton("-");
		JButton editRunway = new JButton("P");
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
		});

		//todo add create runway popup in button listener
		// todo edit runway popup + event listener
		//todo remove runway even listener
		runwaySelectionPanel.add(addRunway);
		runwaySelectionPanel.add(removeRunway);
		runwaySelectionPanel.add(editRunway);

		runwayPanel.add(runwaySelectionPanel);
		add(runwayPanel);

		
		//DESIGNATOR PANEL
		JLabel designatorLabel = new JLabel("Select Designator");
		designatorLabel.setFont(labelFont);
		designatorPanel.add(designatorLabel);
		JComboBox<String> designatorComboBox = new JComboBox<String>();
		designatorComboBox.setFont(notLabelFont);
		designatorComboBox.addItem(CONTROLLER.selectedRunway.lowAngle().designator);
		designatorComboBox.addItem(CONTROLLER.selectedRunway.highAngle().designator);
		designatorComboBox.addActionListener(e -> {
			CONTROLLER.setRunwayAngle(designatorComboBox.getSelectedIndex() == 0);
			CONTROLLER.notify("Designator selected : "+ (String) designatorComboBox.getSelectedItem());
		});
		designatorPanel.add(designatorComboBox);
		add(designatorPanel);

		
		//PLANE POSITION PANEL
		JLabel planePositionLabel = new JLabel("Plane Position");
		planePositionLabel.setFont(labelFont);
		planePositionPanel.add(planePositionLabel);
		JComboBox<String> planePositionComboBox = new JComboBox<String>();
		planePositionComboBox.setFont(notLabelFont);
		planePositionComboBox.addItem("Towards obstacle");
		planePositionComboBox.addItem("Away from obstacle");
		planePositionComboBox.addActionListener(e -> {
			CONTROLLER.setTakeoffDirection((String) planePositionComboBox.getSelectedItem());
			CONTROLLER.notify("Plane position selected : "+ (String) planePositionComboBox.getSelectedItem());
		});
		planePositionPanel.add(planePositionComboBox);
		add(planePositionPanel);
		
		
		//COLOUR SCHEME PANEL
		JLabel colourSchemeLabel = new JLabel("Colour Scheme");
		colourSchemeLabel.setFont(labelFont);
		colourSchemePanel.add(colourSchemeLabel);
		JComboBox<ColourScheme> colourSchemeComboBox = new JComboBox<ColourScheme>();
		colourSchemeComboBox.setFont(notLabelFont);
		for(ColourScheme cs : CONTROLLER.getColourSchemes()) {
			colourSchemeComboBox.addItem(cs);
		}
		colourSchemeComboBox.addActionListener(e -> {
			CONTROLLER.setColourScheme(colourSchemeComboBox.getItemAt(colourSchemeComboBox.getSelectedIndex()));
			CONTROLLER.notify("Colour scheme selected : "+ colourSchemeComboBox.getSelectedItem().toString());
		});
		colourSchemePanel.add(colourSchemeComboBox);
		add(colourSchemePanel);


		//todo add scale and rotation listeners to update labels and view
		//todo notify controller when scale/rotation changed?
		//todo change label when scale changed, only change picture when released
		//SCALE PANEL
		JLabel scaleLabel = new JLabel("View Scale: x1");
		scaleLabel.setFont(labelFont);
		scalePanel.add(scaleLabel);
		JSlider scaleSlider = new JSlider(0, 100, 1);
		scaleSlider.addChangeListener(l -> {
			try {
				float result = CONTROLLER.setViewZoom(scaleSlider.getValue());
				scaleLabel.setText(String.format("View Scale: x%.2f", result));
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(this, e1.getMessage());
			}
		});
		scalePanel.add(scaleSlider);
		add(scalePanel);

		//ROTATION PANEL
		JLabel rotationLabel = new JLabel("View Rotation: 90°"); //Alt + 248 = °
		rotationLabel.setFont(labelFont);
		rotationPanel.add(rotationLabel);
		JSlider rotationSlider = new JSlider(0, 359, 90);
		rotationPanel.add(rotationSlider);
		add(rotationPanel);

		//OBSTACLE PANEL
		JLabel obstacleLabel = new JLabel("Select obstacle");
		obstacleLabel.setFont(labelFont);
		obstaclePanel.add(obstacleLabel);
		JPanel obstacleSelectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		//Obstacle ComboBox
		JComboBox<String> obstacleComboBox = new JComboBox<String>();
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
		obstacleSelectionPanel.add(obstacleComboBox);
		obstacleSelectionPanel.add(obstacleComboBox);

		//Obstacle add, remove and edit
		JButton addObstacle = new JButton("+");
		JButton removeObstacle = new JButton("-");
		JButton editObstacle = new JButton("P");
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
		obstacleSelectionPanel.add(addObstacle);
		obstacleSelectionPanel.add(removeObstacle);
		obstacleSelectionPanel.add(editObstacle);

		obstaclePanel.add(obstacleSelectionPanel);
		add(obstaclePanel);


		//EMPTY PANEL
		add(emptyPanel2);


		//OBSTACLE CENTRELINE DISTANCE PANEL
		JLabel centrelineLabel = new JLabel("Distance from centreline");
		centrelineLabel.setFont(labelFont);
		obstacleCentrelineDistancePanel.add(centrelineLabel);
		JTextField centrelineDistanceTextField = new JTextField();
		centrelineDistanceTextField.addActionListener(e -> {
			if (!CONTROLLER.selectObstacleXPos(centrelineDistanceTextField.getText()))
				centrelineDistanceTextField.setText("0");
		});
		obstacleCentrelineDistancePanel.add(centrelineDistanceTextField);
		add(obstacleCentrelineDistancePanel);


		//OBSTACLE THRESHOLD DISTANCE PANEL
		JLabel thresholdLabel = new JLabel("Distance from threshold");
		thresholdLabel.setFont(labelFont);
		obstacleThresholdDistancePanel.add(thresholdLabel);
		JTextField thresholdDistanceTextField = new JTextField();
		thresholdDistanceTextField.addActionListener(e -> {
			if (!CONTROLLER.selectObstacleYPos(thresholdDistanceTextField.getText()))
				thresholdDistanceTextField.setText("0");
		});
		obstacleThresholdDistancePanel.add(thresholdDistanceTextField);
		add(obstacleThresholdDistancePanel);
	}
}
