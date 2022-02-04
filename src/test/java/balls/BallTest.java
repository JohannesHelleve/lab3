package balls;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Random;
import org.junit.jupiter.api.Test;
import javafx.scene.paint.Color;

public class BallTest {
	public static final double DELTA = 1e-10;
	private static final int N = 10000;
	private Random random = new Random();

	private Ball generateBall() {
		Ball b = new Ball(Color.WHITE, 100 * random.nextDouble());
		for (int i = 0; i < random.nextInt(10); i++)
			b.accelerate(random.nextDouble(), random.nextDouble());
		for (int i = 0; i < random.nextInt(100); i++)
			b.move();
		return b;
	}

	@Test
	public void testDimensions() {
		for (int i = 0; i < N; i++) {
			dimensionsProperty(200 * random.nextDouble());
			badDimensionsProperty(-200 * random.nextDouble());
		}
	}

	public void dimensionsProperty(double radius) {
		radius = Math.abs(radius);
		Ball b = new Ball(Color.WHITE, radius);
		assertEquals(radius, b.getRadius());
		assertEquals(2 * radius, b.getHeight(), DELTA);
		assertEquals(2 * radius, b.getWidth(), DELTA);
	}

	public void badDimensionsProperty(double radius) {
		radius = -Math.abs(radius);
		try {
			new Ball(Color.WHITE, radius);
			fail("Should throw exception on negative radius");
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	@Test
	public void testAaccelerate() {
		for (int i = 0; i < N; i++) {
			accelerateProperty(generateBall(), 10 * random.nextDouble(), 10 * random.nextDouble());
		}
	}

	public void accelerateProperty(Ball b, double ddx, double ddy) {
		double dx = b.getDeltaX();
		double dy = b.getDeltaY();
		b.accelerate(ddx, ddy);
		assertEquals(dx + ddx, b.getDeltaX(), DELTA);
		assertEquals(dy + ddy, b.getDeltaY(), DELTA);
	}

	@Test
	public void testMoveTo() {
		for (int i = 0; i < N; i++) {
			moveToProperty(generateBall(), 10 * random.nextDouble(), 10 * random.nextDouble());
		}
	}

	public void moveToProperty(Ball b, double x, double y) {
		b.moveTo(x, y);
		assertEquals(x, b.getX(), DELTA);
		assertEquals(y, b.getY(), DELTA);
	}

	@Test
	public void testStep() {
		for (int i = 0; i < N; i++) {
			stepProperty(generateBall());
		}
	}

	public void stepProperty(Ball b) {
		double x = b.getX();
		double y = b.getY();
		double dx = b.getDeltaX();
		double dy = b.getDeltaY();
		b.move();
		assertEquals(x + dx, b.getX(), DELTA);
		assertEquals(y + dy, b.getY(), DELTA);
	}

	@Test
	public void testMoveStep() {
		for (int i = 0; i < N; i++) {
			moveStepProperty(generateBall());
		}
	}

	public void moveStepProperty(Ball b) {
		int steps = b.getSteps();
		b.move();
		assertEquals(steps + 1, b.getSteps());
	}

	@Test
	public void testHalt() {
		for (int i = 0; i < N; i++) {
			haltProperty(generateBall());
		}
	}

	public void haltProperty(Ball b) {
		b.halt();
		assertEquals(0.0, b.getDeltaX());
		assertEquals(0.0, b.getDeltaY());
	}

	@Test
	public void testGetRadius() {
		int radius = 10;
		assertEquals(radius, new Ball(Color.WHITE, radius).getRadius());
		radius = 20;
		assertEquals(radius, new Ball(Color.WHITE, radius).getRadius());
	}

	@Test
	public void testHaltStopsAccelerate() {
		Ball b = new Ball(Color.WHITE, 10);
		double startX = b.getX(), startY = b.getY();
		double x = 15, y = 20;
		b.accelerate(x, y);
		b.halt();
		b.move();
		assertEquals(startX, b.getX());
		assertEquals(startY, b.getY());
	}

	@Test
	public void testAccelerateMove() {
		Ball b = new Ball(Color.WHITE, 10);
		double x = 15, y = 20;
		b.accelerate(x, y);
		b.move();
		assertEquals(x, b.getX());
		assertEquals(y, b.getY());
	}

	/**
	 * Checks the size of the balls created from the explosion.
	 */
	@Test
	void testExplodeSize() {
		Ball ball = new Ball(Color.BISQUE, 24);
		Ball[] newBalls = ball.explode();
		assertEquals(8, newBalls.length);
		for (Ball b : newBalls) {
			assertEquals(12, b.getRadius());
		}
	}

	/**
	 * Checks that the new balls have the same position as the original
	 * at the time of the explosion.
	 */
	@Test
	void testExplodePosition() {
		Ball ball = new Ball(Color.BISQUE, 24);
		ball.moveTo(5.0, 5.0);
		Ball[] newBalls = ball.explode();
		assertEquals(8, newBalls.length);
		for (Ball b : newBalls) {
			assertEquals(ball.getX(), b.getX());
			assertEquals(ball.getY(), b.getY());
		}
	}

	/**
	 * Checks that the speed of the ball is not zero in either
	 * direction after the explosion.
	 */
	@Test
	void testExplodeSpeedNotZero() {
		Ball ball = new Ball(Color.BISQUE, 24);
		Ball[] newBalls = ball.explode();
		assertEquals(8, newBalls.length);
		for (Ball b : newBalls) {
			assertFalse(b.getDeltaX() == 0.0);
			assertFalse(b.getDeltaY() == 0.0);
		}
	}

}
