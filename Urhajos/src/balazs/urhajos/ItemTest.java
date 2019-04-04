package balazs.urhajos;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ItemTest {
    Item item;

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
        item =                 new Missile(
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
    public void initAfterSerialized() {
        item.initAfterSerialized();
        double d = item.getWidth();
        Assert.assertEquals(Constants.MISSILE_WIDTH, d);
    }

    @Test
    public void setxLoc() {
        item.setxLoc(10);
        double x = item.getxLoc();
        Assert.assertEquals(10, x);
    }

    @Test
    public void setyLoc() {
        item.setyLoc(10);
        double y = item.getyLoc();
        Assert.assertEquals(10, y);
    }

    @Test
    public void destroyItem() {
        item.destroyItem();
        boolean exists = item.isExists();
        Assert.assertEquals(false, exists);
    }
}