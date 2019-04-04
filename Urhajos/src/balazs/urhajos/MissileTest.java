package balazs.urhajos;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MissileTest {
    Missile m;

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
        m =                 new Missile(
                50 - (Constants.MISSILE_WIDTH / 2),
                50 - Constants.MISSILE_HEIGHT,
                2,
                true
        );
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void destroyItem() {
        boolean b = m.isExists();
        Assert.assertEquals(true, b);
        m.destroyItem();
        b = m.isExists();
        Assert.assertEquals(false, b);
    }

    @Test
    public void actionAtClock() {
        double original = 50 - Constants.MISSILE_HEIGHT;
        m.actionAtClock();
        double value = m.getY();
        Assert.assertNotEquals(original, value);
    }

    @Test
    public void setMovePerClockX() {
        double x = 20;
        m.setMovePerClockX(x);
        double result = m.getMovePerClockX();
        Assert.assertEquals(x,result);
    }
}