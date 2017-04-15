package UI;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class NotificationsPanel extends JPanel 
{
	
	JTextArea notifications;
	
	public NotificationsPanel()
	{
		notifications = new JTextArea();
		setLayout(new BorderLayout());
		JScrollPane scroll = new JScrollPane (notifications, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		notifications.setEditable(false);
		scroll.setPreferredSize(new Dimension(600, 100));
		notifications.setText("Application Started");
	
		add(scroll);
	}
	
	public void notify(String s)
	{
		notifications.setText(notifications.getText() + "\n" + s);
	}
}
