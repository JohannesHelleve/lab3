package balls;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Paint;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

/**
 * This class draws balls on a 2D plane.
 * All balls move and are affected by gravity.
 * When balls hit the floor they bounce back up.
 * New balls are generated at regular time intervals.
 *
 * @author Martin Vatshelle and Anya
 *
 */
public class BallDemo extends Application {

	private AnimationTimer timer;
	private Canvas canvas;
	private List<Ball> balls = new ArrayList<Ball>();
	private Random random = new Random();
	private int stepCount = 0;
	private int maxNumberOfBalls = 2000;

	@Override
	public void start(Stage stage) throws Exception {
		// create a canvas to draw balls on
		double width = 640;
		double height = 480;
		Group root = new Group();
		Scene scene = new Scene(root, width, height, Color.BLACK);
		stage.setScene(scene);
		canvas = new Canvas(width, height);
		canvas.widthProperty().bind(scene.widthProperty());
		canvas.heightProperty().bind(scene.heightProperty());
		// canvas.setEffect(new BoxBlur());
		root.getChildren().add(canvas);

		// creates 10 initial balls
		setup();

		// create an timer used to refresh the canvas
		timer = new BallDemoTimer(this);
		timer.start();

		// turn on the graphics
		// stage.setFullScreen(true);
		stage.show();

	}

	/**
	 * add 10 initial balls to start off the simulation
	 */
	private void setup() {
		for (int i = 0; i < 10; i++) {
			newBall();
		}
	}

	/**
	 * Add a new ball to the canvas
	 * All balls are affected by gravity and they bounce against the bottom.
	 * 
	 * @param ball
	 */
	public void addBall(Ball ball) {
		ball.setAcceleration(0, 0.098f);
		ball.setUpperLimitY(canvas.getHeight() - ball.getRadius());
		balls.add(ball);
	}

	/**
	 * Remove a ball from the canvas
	 * 
	 * @param ball
	 */
	public void removeBall(Ball ball) {
		balls.remove(ball);
	}

	/**
	 * Makes a ball explode into many small balls
	 * 
	 * @param ball
	 */
	public void addExplosion(Ball ball) {
		balls.remove(ball);
		Ball[] newBalls = ball.explode();
		if (balls.size() < maxNumberOfBalls) { // avoid too many balls to be on the screen
			for (Ball b : newBalls) {
				if (ball.getRadius() > 4)
					addBall(b);
			}
		}
	}

	/**
	 * Create a new ball of size 32 and adds it to the BallDemo
	 * The color will be random
	 * The ball is centrally located on the lower part of the canvas
	 * The speed will be random in both x and y direction
	 */
	private void newBall() {
		newBall(canvas.getWidth() / 2, canvas.getHeight() - 32, 32);
	}

	/**
	 * Creates a new ball and adds it to the BallDemo
	 * The speed will be random in both x and y direction
	 * The color will be random
	 * 
	 * @param x    - x position of the ball
	 * @param y    - y position of the ball
	 * @param size - radius of the ball
	 */
	private void newBall(double x, double y, double size) {
		Paint paint = getRandomColor();
		Ball b = new Ball(paint, size);
		b.moveTo(x, y);
		b.setSpeed((128 / size) * (random.nextDouble() - 0.5), -(256 / size) * random.nextDouble() - 5);
		addBall(b);
	}

	/**
	 * Returns a random color
	 * 
	 * @return
	 */
	private Paint getRandomColor() {
		Color color = Color.RED.deriveColor(64 * random.nextDouble() - 32.0, 1.0, 1.0, .7);
		Color white = color.deriveColor(0.0, .33, 3.0, 2.0);
		Paint paint = new RadialGradient(0.0, 0.0, 0.3, 0.3, .6, true, CycleMethod.NO_CYCLE, new Stop(0.0, white),
				new Stop(1.0, (Color) color));
		return paint;
	}

	/**
	 * Moves all balls one step forward
	 * Generates new balls at regular intervals
	 */
	protected void step() {
		for (Ball b : new ArrayList<>(balls)) {
			// b.accelerate(0, 0.098f); // all balls are affected by gravity
			b.move();

			// after a 200 steps a ball will explode
			if (b.getSteps() >= 200)
				addExplosion(b);

		}
		// every 16th step a new ball is added
		if (stepCount % 16 == 0) {
			newBall();
		}

		// every 100th step number of balls are printed
		if (stepCount % 100 == 0) {
			System.out.println("Number of balls: " + balls.size());
		}

		stepCount++;
	}

	/**
	 * Re-draws every ball on the canvas
	 */
	protected void draw() {
		GraphicsContext context = canvas.getGraphicsContext2D();
		context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for (int i = balls.size() - 1; i >= 0; i--) {
			Ball b = balls.get(i);
			double w = b.getWidth();
			double h = b.getHeight();
			double xPos = b.getX() - w / 2.0;
			double yPos = b.getY() - h / 2.0;
			context.save();
			context.setFill(b.getColor());
			context.fillOval(xPos, yPos, w, h);
			context.restore();
		}
	}
}

/**
 * Class for calling the {@link #BallDemo.draw()} method 60 times per second
 */
class BallDemoTimer extends AnimationTimer {
	private long nanosPerStep = 1000_000_000L / 60L;
	private long timeBudget = nanosPerStep;
	private long lastUpdateTime = 0L;
	private BallDemo balldemo;

	public BallDemoTimer(BallDemo balldemo) {
		this.balldemo = balldemo;
	}

	@Override
	public void handle(long now) {
		if (lastUpdateTime > 0) {
			timeBudget = Math.min(timeBudget + (now - lastUpdateTime), 10 * nanosPerStep);
		}
		lastUpdateTime = now;

		while (timeBudget >= nanosPerStep) {
			timeBudget = timeBudget - nanosPerStep;
			balldemo.step();
		}
		balldemo.draw();
	}
}
