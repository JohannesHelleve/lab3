package balls;

import javafx.application.Application;

/**
 * Launcher class for BallDemo
 * 
 * Separate Main class due to JavaFX - Eclipse bug
 * 
 * For more information see https://openjfx.io/openjfx-docs/#install-javafx
 * (search for "Error: JavaFX runtime components are missing, and are required
 * to run this application")
 * 
 * @author Anna Eilertsen - anna.eilertsen@uib.no
 *
 */
public class Main {

	public static void main(String[] args) {
		Application.launch(BallDemo.class, args);
	}

}
