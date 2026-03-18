package it.unibo.minigOOlf.view;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * here the mouse listener that catches the shot made by the user
 * @author fede
 *
 */
public class ShotListener extends MouseAdapter implements ShotInput {

	private final ShotVisualizer visualizer; 
	private Point startingPoint = null;
	//private Point currentPoint = null;
	private boolean enable = false;
	
	/**
	 * @param visualizer
	 */
	public ShotListener(ShotVisualizer visualizer) {
		this.visualizer = visualizer;
	}
	
	@Override
	public synchronized void mouseDragged(MouseEvent e) {
		if (this.enable && this.startingPoint != null) {
			//this.currentPoint = e.getPoint();
			//this.visualizer.updateShotIntent(new Vector2D(this.startingPoint, this.currentPoint));
            //TODO quando sarà pronta la classe vettore
		}
	}

	@Override
	public synchronized void mousePressed(MouseEvent e) {
		if (this.enable) {
			this.startingPoint = e.getPoint();
		}
	}

	@Override
	public synchronized void mouseReleased(MouseEvent e) {
		if (this.enable && this.startingPoint != null) {
			this.visualizer.shoot();
			this.startingPoint = null;
			//this.currentPoint = null;
		}
	}

	@Override
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
}

