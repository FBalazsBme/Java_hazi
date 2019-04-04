package balazs.urhajos;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.Serializable;

/**
 * Mindhárom grafikus elem, azaz missile, ship, planet ősosztálya.
 */
public abstract class Item extends ImageView implements Serializable {

    protected double height = 24;

    protected double width = 24;

    protected String spriteName;

    protected double xLoc = 0;
    protected double yLoc = 0;



    protected transient Image sprite = new Image(FilePaths.SPACESHIP_BASE,
            width, height, false, false);

    protected boolean exists = true;


    public Item(double height, double width) {
        this.height = height;
        this.width = width;
        super.setImage(sprite);
        super.setFitHeight(height);
        super.setFitWidth(width);

    }

    public Item(String spriteName, double height, double width) {
        this.spriteName = spriteName;
        this.height = height;
        this.width = width;
        sprite = new Image(spriteName, this.width, this.height, false, false);
        super.setImage(sprite);
        super.setFitHeight(height);
        super.setFitWidth(width);
    }

    /**
     * Beállítja az értékeket szerializálás után.
     */
    public void initAfterSerialized(){
        sprite = new Image(this.spriteName, this.width, this.height, false, false);
        setX(xLoc);
        setY(yLoc);
        super.setImage(sprite);
        super.setFitHeight(height);
        super.setFitWidth(width);
    }

    public double getxLoc() {
        return xLoc;
    }

    public void setxLoc(double xLoc) {
        this.xLoc = xLoc;
        setX(xLoc);
    }

    public double getyLoc() {
        return yLoc;
    }

    public void setyLoc(double yLoc) {
        this.yLoc = yLoc;
        setY(yLoc);
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getMiddleCoordinateX(){
        return getX() + (width / 2);
    }

    public double getMiddleCoordinateY(){
        return getY() + (width / 2);
    }

    public void setExists(boolean exists) { this.exists = exists;}

    public boolean isExists() {
        return exists;
    }

    /**
     * "destruktor", minden itemnek lennie kell
     */
    public abstract void destroyItem();

    /**
     * mit tegyen a clock-nál?
     */
    public abstract void actionAtClock();
}
