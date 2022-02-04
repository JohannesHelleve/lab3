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
	/** Radius of the ball. */
	private double radius;
	/** The ball's position and speed in x direction. */
	private Motion xMotion;
	/** The ball's position and speed in y direction. */
	private Motion yMotion;
	/** Number of steps taken */
	private int steps = 0;

	private Random random = new Random();

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
		this.color = color;
		this.radius = radius;

		xMotion = new Motion();
		yMotion = new Motion();
	}

	/**
	 * @return Current X position of the Ball
	 */
	public double getX() {
		return xMotion.getPosition();
	}

	/**
	 * @return Current Y position of the Ball
	 */
	public double getY() {
		return yMotion.getPosition();
	}
	
	/**
	 * @return The ball's radius
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * @return The ball's width (normally 2x {@link #getRadius()})
	 */
	public double getWidth() {
		return radius * 2;
	}

	/**
	 * @return The ball's height (normally 2x {@link #getRadius()})
	 */
	public double getHeight() {
		return radius * 2;
	}

	/**
	 * @return Paint/color for the ball
	 */
	public Paint getColor() {
		return color;
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
		xMotion.setPosition(newX);
		yMotion.setPosition(newY);
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
		xMotion.move();
		yMotion.move();
		steps++;
	}
	
	/**
	 * This method makes one ball explode into 8 smaller balls with half the radius
	 * The new balls may have different speed and direction
	 * @return the new balls after the explosion
	 */
	public Ball[] explode() {
		int nChildBalls = 8;
		Ball[] balls = new Ball[nChildBalls];
		for (int i = 0; i < nChildBalls; i++) {
			Ball childBall = createRandomBallChild(this);
			balls[i] = childBall;
		}
		return balls;
	}

	public Ball createRandomBallChild(Ball parent) {
		double childRadius = getRadius() / 2.0;
		Paint childColor = getRandomColor();

		double newSpeedX = random.nextDouble() + this.getDeltaX();
		double newSpeedY = random.nextDouble() + this.getDeltaY();
		double newAccelerationX = random.nextDouble() + xMotion.getAcceleration();
		double newAccelerationY = random.nextDouble() + yMotion.getAcceleration();
		
		Ball childBall = new Ball(childColor, childRadius);
		childBall.setAcceleration(newAccelerationX, newAccelerationY);
		childBall.setSpeed(newSpeedX, newSpeedY);
		childBall.moveTo(parent.getX(), parent.getY());
		return childBall;
	}

	/**
	 * Returns a random color
	 * @return color
	 */
	private Paint getRandomColor() {
		Color color = Color.RED.deriveColor(64 * random.nextDouble() - 32.0, 1.0, 1.0, .7);
		Color white = color.deriveColor(0.0, .33, 3.0, 2.0);
		Paint paint = new RadialGradient(0.0, 0.0, 0.3, 0.3, .6, true, CycleMethod.NO_CYCLE, new Stop(0.0, white),
				new Stop(1.0, (Color) color));
		return paint;
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
		xMotion.setAcceleration(xAcceleration);
		yMotion.setAcceleration(yAcceleration);
	}

	/**
	 * This method changes the speed of the ball, this is a one time boost to the speed
	 * and will only change the speed, not the acceleration of the ball.
	 * 
	 * @param xAcceleration
	 * @param yAcceleration
	 */
	public void accelerate(double xAcceleration, double yAcceleration) {
		xMotion.accelerate(xAcceleration);
		yMotion.accelerate(yAcceleration);
	}

	/**
	 * Stops the motion of this ball
	 * Both speed and acceleration will be sat to 0
	 */
	public void halt() {
		setAcceleration(0, 0);
		setSpeed(0, 0);
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
