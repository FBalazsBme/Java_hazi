package balazs.urhajos;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ShipTest {
    Ship ship;

    @Before
    public void setUp() throws Exception {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                new JFXPanel(); // Initializes the JavaFx Platform
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            new Main().start(new Stage()); // Create and

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        // initialize
                        // your app.

                    }
                });
            }
        });
        thread.start();// Initialize the thread
        Thread.sleep(10000);
        ship = new Ship();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void makeMovement() {
        double move1 = ship.getMovePerClockX();
        ship.makeMovement(Constants.Directions.THRUST);
        ship.movementControl();
        double move2 = ship.getMovePerClockX();
        Assert.assertNotEquals(move1, move2);
    }

    @Test
    public void stopMovement() {
        double move1 = ship.getMovePerClockX();
        ship.stopMovement(Constants.Directions.THRUST);
        ship.movementControl();
        double move2 = ship.getMovePerClockX();
        Assert.assertEquals(move1, move2);
    }

    @Test
    public void shoot() {
        int start = ((GamingPane)ship.getParent()).getSetup().getMissiles().size();
        ship.shoot();
        int end = ((GamingPane)ship.getParent()).getSetup().getMissiles().size();

        Assert.assertEquals(start+1, end);
    }

    @Test
    public void steeringControl() {

    }

    @Test
    public void movementControl() {

    }
}