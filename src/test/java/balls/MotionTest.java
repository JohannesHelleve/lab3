package balls;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MotionTest {

	double position;
	double speed;
	double acceleration;
	Motion accelerating;
	Motion moving;
	Motion reversing;
	Motion stationary;

	@BeforeEach
	void setUp() throws Exception {
		position = 2.5;
		speed = 1.3;
		acceleration = 0.4;
		accelerating = new Motion(position, speed, acceleration);
		moving = new Motion(position, speed, 0);
		reversing = new Motion(position, -speed, 0);
		stationary = new Motion(position, 0, 0);
	}

	@Test
	void testConstructor() {
		assertEquals(position, accelerating.getPosition());
		assertEquals(speed, accelerating.getSpeed());
		assertEquals(acceleration, accelerating.getAcceleration());
	}

	@Test
	void testSetPosition() {
		assertEquals(position, accelerating.getPosition());
		double newPos = 7.3;
		accelerating.setPosition(newPos);
		assertEquals(newPos, accelerating.getPosition());
	}

	@Test
	void testSetSpeed() {
		assertEquals(speed, accelerating.getSpeed());
		double newSpeed = 3.2;
		accelerating.setPosition(newSpeed);
		assertEquals(newSpeed, accelerating.getPosition());
	}

	@Test
	void testMove() {
		accelerating.move();
		assertEquals(position + speed, accelerating.getPosition());
		assertEquals(speed + acceleration, accelerating.getSpeed());
		assertEquals(acceleration, accelerating.getAcceleration());
	}

	@Test
	void testBounceUpperLimitEqual() {
		moving.setUpperLimit(position + speed);
		assertEquals(moving.getAcceleration(), 0);
		moving.move();
		assertEquals(position + speed, moving.getPosition());
		moving.move();
		assertEquals(position, moving.getPosition());
	}

	@Test
	void testBounceUpperLimitNotReached() {
		moving.setUpperLimit(1000);
		assertEquals(moving.getAcceleration(), 0);
		moving.move();
		assertEquals(position + speed, moving.getPosition());
		moving.move();
		assertEquals(position + 2 * speed, moving.getPosition());
	}

	@Test
	void testBounceUpperLimitReached() {
		moving.setUpperLimit(position + 1);
		assertEquals(moving.getAcceleration(), 0);
		moving.move();
		assertEquals(position + 1 - (speed - 1), moving.getPosition());
	}

	@Test
	void testBounceLowerLimitEqual() {
		reversing.setLowerLimit(position - speed);
		assertEquals(reversing.getAcceleration(), 0);
		reversing.move();
		assertEquals(position - speed, reversing.getPosition());
		reversing.move();
		assertEquals(position, reversing.getPosition());
	}

	@Test
	void testBounceLowerLimitNotReached() {
		reversing.setLowerLimit(-1000);
		assertEquals(reversing.getAcceleration(), 0);
		reversing.move();
		assertEquals(position - speed, reversing.getPosition());
		reversing.move();
		assertEquals(position - 2 * speed, reversing.getPosition());
	}

	@Test
	void testBounceLowerLimitReached() {
		reversing.setLowerLimit(position - 1);
		assertEquals(reversing.getAcceleration(), 0);
		reversing.move();
		assertEquals(position - 1 + (speed - 1), reversing.getPosition());
	}
}
