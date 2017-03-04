package UI;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MenuItem extends JPanel 
{
	public MenuItem(String s) 
	{
		setOpaque(false);
		JLabel string = new JLabel(s);
		add(string);
	}
	
	public void addComponent(JComponent component)
	{
		add(component);
	}
}
