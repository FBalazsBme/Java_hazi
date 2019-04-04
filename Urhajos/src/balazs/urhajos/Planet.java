package balazs.urhajos;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.util.TempState;

import java.io.Serializable;

/**
 * A bolygókat reprezentálja.
 */
public class Planet extends Item implements Serializable {

    public Planet(double height, double width) {
        super(height, width);
    }

    public Planet(String spriteName, double height, double width) {
        super(spriteName, height, width);
        this.height = height;
        this.width = width;
    }

    /**
     * Ha szétlőtték, akkor elhalványítja.
     */
    @Override
    public void destroyItem() {
        super.setExists(false);
        super.setOpacity(0.25);
    }

    @Override
    public void actionAtClock() {

    }
}
