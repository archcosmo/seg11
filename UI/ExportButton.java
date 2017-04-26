package UI;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import Application.Controller;

@SuppressWarnings("serial")
public class ExportButton extends JButton {
	
	public ExportButton(Controller CONTROLLER) {
		super("Export view");
		addActionListener(e -> {
			JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fc.setDialogTitle("Select directory to save graphs in...");
			
			if(fc.showDialog(this, "Export") == JFileChooser.APPROVE_OPTION) {
				try {
					CONTROLLER.exportGraphs(fc.getSelectedFile());
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(this, "Error: " + e1.getMessage());
				}
			}
		});
	}
}
