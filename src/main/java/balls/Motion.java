package balls;

/**
 * This class keeps track of motion in one dimension.
 * The position determines current position
 * The speed of this motion determines how much this position changes each move
 * The acceleration determines how much the speed changes each move
 * lower and upper limits determines how this object
 * 
 * @author Martin Vatshelle
 *
 */
public class Motion {

	private double position;
	private double speed;
	private double acceleration;
	private boolean hasLowerLimit;
	private double lowerLimit;
	private boolean hasUpperLimit;
	private double upperLimit;
	private double bounceFactor;

	/**
	 * Default constructor makes new Motion where both speed and position is 0;
	 */
	public Motion() {
		this(0, 0, 0);
	}

	/**
	 * Constructor with specified position, speed and acceleration.
	 * 
	 * @param position - initial position
	 * @param speed    - initial speed
	 */
	public Motion(double position, double speed, double acceleration) {
		this.position = position;
		this.speed = speed;
		this.acceleration = acceleration;
		hasUpperLimit = false;
		hasLowerLimit = false;
		bounceFactor = 1;
	}

	/**
	 * Returns the position along this axis
	 * 
	 * @return position
	 */
	public double getPosition() {
		return position;
	}

	/**
	 * Sets the position along this axis
	 * 
	 * @param position
	 */
	public void setPosition(double position) {
		this.position = position;
	}

	/**
	 * Gets the speed along this axis
	 * 
	 * @return speed
	 */
	public double getSpeed() {
		return speed;
	}

	/**
	 * Sets the speed along this axis
	 * 
	 * @param speed
	 */
	public void setSpeed(double speed) {
		this.speed = speed;
	}

	/**
	 * Changes the position according to the speed
	 * If the motion hits a boundary a bounce is performed.
	 * Bouncing only works on boundaries, not on other objects with motion.
	 */
	public void move() {
		if (mustBounce()) {
			double distanceToMove = speed;
			// if speed is large compared to upper and lower limits we might need
			// more than one bounce, therefore a while loop
			while (distanceToMove != 0.0) {
				distanceToMove = doBounce(distanceToMove);
			}
		} else {
			position += speed;
		}
		speed += acceleration;

		if ((hasLowerLimit && position < lowerLimit) || (hasUpperLimit && position > upperLimit))
			throw new IllegalStateException("Motion has moved out of bounds.");

	}

	/**
	 * Sets the bounce factor for this motion
	 * Bounce factor describes how much of the speed is retained after the bounce.
	 * A bounce factor of 0 means no bouncing, the motion sticks to the limit like
	 * if you throw a sticky object on a wall
	 * A bounce factor of 1 means perfect bounce with no energy lost.
	 * Although it is physically impossible in the real world, we do allow bounce
	 * factors
	 * larger than 1.
	 * 
	 * @param bounceFactor
	 */
	public void setBounceFactor(double bounceFactor) {
		if (bounceFactor < 0)
			throw new IllegalArgumentException("Bounce factor must > 0");

		this.bounceFactor = bounceFactor;
	}

	/**
	 * Checks if this motion is such that a bounce is needed at next move
	 */
	private boolean mustBounce() {
		if (speed > 0 && hasUpperLimit && position + speed > upperLimit) {
			return true;
		}
		if (speed < 0 && hasLowerLimit && position + speed < lowerLimit) {
			return true;
		}
		return false;
	}

	/**
	 * Performs a move with bounce
	 */
	private double doBounce(double distanceToMove) {
		// bounce at upper limit
		if (hasUpperLimit && position + distanceToMove > upperLimit) {
			distanceToMove -= upperLimit - position;
			position = upperLimit;
		} else {
			// bounce at lower limit
			if (hasLowerLimit && position + distanceToMove < lowerLimit) {
				distanceToMove += position - lowerLimit;
				position = lowerLimit;
			} else { // no bounce
				position += distanceToMove;
				return 0;
			}
		}
		speed *= -bounceFactor;
		distanceToMove *= -bounceFactor;

		return distanceToMove;
	}

	/**
	 * Changes the speed
	 * 
	 * @param acceleration
	 */
	public void accelerate(double acceleration) {
		speed += acceleration;
	}

	public void setAcceleration(double acceleration) {
		this.acceleration = acceleration;
	}

	public void setLowerLimit(double limit) {
		this.lowerLimit = limit;
		this.hasLowerLimit = true;
	}

	public void setUpperLimit(double limit) {
		this.upperLimit = limit;
		this.hasUpperLimit = true;
	}

	public double getAcceleration() {
		return acceleration;
	}

}
