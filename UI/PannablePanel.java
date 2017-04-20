package UI;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import Model.Draw;

public abstract class PannablePanel extends JPanel /*a PANel...geddit?*/ {

	Draw drawingModule;
	boolean pannable;
	boolean panning;
	int clickPointX, clickPointY;
	
	public PannablePanel(Draw drawingModule) {
		this.drawingModule = drawingModule;
		
		updateCursor();
		initListeners();
	}

	protected abstract void diffFunction(int diffX, int diffY);
	
	private void initListeners() {
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				PannablePanel.this.panning = false;
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if(PannablePanel.this.pannable)
					PannablePanel.this.panning = true;
				PannablePanel.this.clickPointX = e.getX();
				PannablePanel.this.clickPointY = e.getY();
			}

			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {}
		});

		this.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				if(PannablePanel.this.pannable && PannablePanel.this.panning) {
					int diffX = e.getX() - PannablePanel.this.clickPointX;
					int diffY = e.getY() - PannablePanel.this.clickPointY;
					PannablePanel.this.clickPointX = e.getX();
					PannablePanel.this.clickPointY = e.getY();
					PannablePanel.this.diffFunction(diffX, diffY);
				}
			}

			public void mouseMoved(MouseEvent e) {}
		});
	}
	
	private void updateCursor() {
		this.pannable = drawingModule.getZoomFactor() > 1.0F ? true : false;
		Cursor cursor = new Cursor(pannable ? Cursor.HAND_CURSOR : Cursor.DEFAULT_CURSOR);
		this.setCursor(cursor);
	}
	
	@Override
	public void paintComponent(Graphics g) 
	{
		updateCursor();
	}
}
