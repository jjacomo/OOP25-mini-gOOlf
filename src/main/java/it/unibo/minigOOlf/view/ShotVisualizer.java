package it.unibo.minigOOlf.view;

import it.unibo.minigOOlf.util.Vector2D;

/**
 * an interface used to get the input
 * @author fede
 *
 */

public interface ShotVisualizer {

	/**
	 * updates the line that shows the direction and the power of a shot
	 * @param direction
	 */
	void updateShotIntent(Vector2D direction);


	
	/**
	 *  manages a new shot by the user
	 */
	void shoot();
}
