package UI;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;

import Application.Controller;
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
		this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		addComponents();
	}

	/* Create menu */
	private void addComponents() 
	{
		Font labelFont = this.getFont().deriveFont(20.0f);

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
		exportViewPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
		runwayPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
		designatorPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
		planePositionPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
		colourSchemePanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
		scalePanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
		rotationPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
		obstaclePanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
		obstacleThresholdDistancePanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
		obstacleCentrelineDistancePanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));



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
		JPanel runwaySelectionPanel = new JPanel();

		//Runway ComboBox
		JComboBox<String> runwayComboBox = new JComboBox<String>();
		for (Runway r: CONTROLLER.getRunways())
		{
			runwayComboBox.addItem(r.getName());
		}
		runwayComboBox.addActionListener(e -> {
			CONTROLLER.selectRunway((String) runwayComboBox.getSelectedItem());
			CONTROLLER.notify("Runway selected : "+ (String) runwayComboBox.getSelectedItem());
		});
		runwaySelectionPanel.add(runwayComboBox);

		//Runway add, remove and edit
		JButton addRunway = new JButton("+");
		JButton removeRunway = new JButton("-");
		JButton editRunway = new JButton("P");
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
		designatorComboBox.addItem("Small Angle");//todo: change to 09R and 27L ...
		designatorComboBox.addItem("Large Angle");
		designatorComboBox.addActionListener(e -> {
			CONTROLLER.setRunwayAngle((String) designatorComboBox.getSelectedItem());
			CONTROLLER.notify("Designator selected : "+ (String) designatorComboBox.getSelectedItem());
		});
		designatorPanel.add(designatorComboBox);
		add(designatorPanel);

		
		//PLANE POSITION PANEL
		JLabel planePositionLabel = new JLabel("Plane Position");
		planePositionLabel.setFont(labelFont);
		planePositionPanel.add(planePositionLabel);
		JComboBox<String> planePositionComboBox = new JComboBox<String>();
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
		JComboBox<String> colourSchemeDropdown = new JComboBox<String>();
		colourSchemeDropdown.addItem("Wiked");
		colourSchemeDropdown.addItem("Colour");
		colourSchemeDropdown.addItem("Schemes");
		colourSchemeDropdown.addActionListener(e -> {
			CONTROLLER.setTakeoffDirection((String) colourSchemeDropdown.getSelectedItem());
			CONTROLLER.notify("Colour scheme selected : "+ (String) colourSchemeDropdown.getSelectedItem());
		});
		colourSchemePanel.add(colourSchemeDropdown);
		add(colourSchemePanel);


		//todo add scale and rotation listeners to update labels and view
		//todo notify controller when scale/rotation changed?
		//todo change label when scale changed, only change picture when released
		//SCALE PANEL
		JLabel scaleLabel = new JLabel("View Scale: x1");
		scaleLabel.setFont(labelFont);
		scalePanel.add(scaleLabel);
		JSlider scaleSlider = new JSlider(1, 10, 1);
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
		JPanel obstacleSelectionPanel = new JPanel();

		//Obstacle ComboBox
		JComboBox<String> obstacleComboBox = new JComboBox<String>();
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
		obstacleThresholdDistancePanel.add(new JLabel("Distance from threshold"));
		JTextField thresholdDistanceTextField = new JTextField();
		thresholdDistanceTextField.addActionListener(e -> {
			if (!CONTROLLER.selectObstacleYPos(thresholdDistanceTextField.getText()))
				thresholdDistanceTextField.setText("0");
		});
		obstacleThresholdDistancePanel.add(thresholdDistanceTextField);
		add(obstacleThresholdDistancePanel);
	}
}
