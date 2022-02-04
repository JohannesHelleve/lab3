package balls;

import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Paint;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;

/**
 * A class to represent bouncing balls
 * Balls have a size and a color as well as a motion
 * Balls move according to speed and acceleration in both x and y directions.
 * One can set a limit to the movement (like floor ceiling or walls) such that
 * balls will not go outside these limits.
 */
public class Ball{

	/** Color of the ball's surface */
	private Paint color;
	/** The ball's position and speed in x direction. */
	private Motion xMotion;
	/** The ball's position and speed in y direction. */
	private Motion yMotion;
	/** Number of steps taken */
	private int steps = 0;

	/**
	 * Create a new ball with position and velocity (0,0)
	 * 
	 * @param color
	 *            The color of the ball
	 * @param radius
	 *            The radius of the ball
	 */
	public Ball(Paint color, double radius) {
		if (radius < 0)
			throw new IllegalArgumentException("Radius should not be negative");
		
	}

	/**
	 * @return Current X position of the Ball
	 */
	public double getX() {
		// TODO
	}

	/**
	 * @return Current Y position of the Ball
	 */
	public double getY() {
		// TODO
	}
	
	/**
	 * @return The ball's radius
	 */
	public double getRadius() {
		// TODO
	}

	/**
	 * @return The ball's width (normally 2x {@link #getRadius()})
	 */
	public double getWidth() {
		// TODO
	}

	/**
	 * @return The ball's height (normally 2x {@link #getRadius()})
	 */
	public double getHeight() {
		// TODO
	}

	/**
	 * @return Paint/color for the ball
	 */
	public Paint getColor() {
		// TODO
	}

	/**
	 * Number of steps is used to determine the behavior of the ball
	 * 
	 * @return
	 */
	public int getSteps() {
		return steps;
	}

	/**
	 * Move ball to a new position.
	 * 
	 * After calling {@link #moveTo(double, double)}, {@link #getX()} will return
	 * {@code newX} and {@link #getY()} will return {@code newY}.
	 * 
	 * @param newX
	 *            New X position
	 * @param newY
	 *            New Y position
	 */
	public void moveTo(double newX, double newY) {
		// TODO
	}
	
	/**
	 * Returns the speed of the ball which is measured in pixels/move
	 * @return Current X movement
	 */
	public double getDeltaX() {
		return this.xMotion.getSpeed();
	}

	/**
	 * Returns the speed of the ball which is measured in pixels/move
	 * @return Current Y movement
	 */
	public double getDeltaY() {
		return this.yMotion.getSpeed();
	}
	
	/**
	 * Perform one time step.
	 * 
	 * For each time step, the ball's (xPos,yPos) position should change by
	 * (deltaX,deltaY).
	 */
	public void move() {
		// TODO
		// Hint: examine which methods there are in the class Motion
		//      maybe you don't have to do as much as you think.
	}
	
	/**
	 * This method makes one ball explode into 8 smaller balls with half the radius
	 * The new balls may have different speed and direction
	 * @return the new balls after the explosion
	 */
	public Ball[] explode() {
		// TODO
		return null;
	}


	/**
	 * Acceleration changes the speed of this ball every time move is called.
	 * This method sets the acceleration in both x and y direction to a given value.
	 * This acceleration is then added every time the move method is called
	 * 
	 * @param xAcceleration The extra speed along the x-axis
	 * @param yAcceleration The extra speed along the y-axis
	 */
	public void setAcceleration(double xAcceleration, double yAcceleration) {
		// TODO
	}

	/**
	 * This method changes the speed of the ball, this is a one time boost to the speed
	 * and will only change the speed, not the acceleration of the ball.
	 * 
	 * @param xAcceleration
	 * @param yAcceleration
	 */
	public void accelerate(double xAcceleration, double yAcceleration) {
		// TODO
	}

	/**
	 * Stops the motion of this ball
	 * Both speed and acceleration will be sat to 0
	 */
	public void halt() {
		// TODO
	}

	/**
	 * Sets the speed of the ball
	 * Note: in BallDemo positive ySpeed is down and negative ySpeed is up
	 * 
	 * @param xSpeed - speed in x direction
	 * @param ySpeed - speed in y direction
	 */
	public void setSpeed(double xSpeed, double ySpeed) {
		xMotion.setSpeed(xSpeed);
		yMotion.setSpeed(ySpeed);
	}

	/**
	 * Sets the lower limit for X values this Ball can have.
	 * If the limit is set the ball will bounce once reaching that limit
	 * @param limit
	 */
	public void setLowerLimitX(double limit) {
		xMotion.setLowerLimit(limit);
	}

	/**
	 * Sets the lower limit for Y values this Ball can have.
	 * If the limit is set the ball will bounce once reaching that limit
	 * @param limit
	 */
	public void setLowerLimitY(double limit) {
		yMotion.setLowerLimit(limit);
	}

	/**
	 * Sets the upper limit for X values this Ball can have.
	 * If the limit is set the ball will bounce once reaching that limit
	 * @param limit
	 */
	public void setUpperLimitX(double limit) {
		xMotion.setUpperLimit(limit);
	}

	/**
	 * Sets the upper limit for Y values this Ball can have.
	 * If the limit is set the ball will bounce once reaching that limit
	 * @param limit
	 */
	public void setUpperLimitY(double limit) {
		yMotion.setUpperLimit(limit);
	}
}
