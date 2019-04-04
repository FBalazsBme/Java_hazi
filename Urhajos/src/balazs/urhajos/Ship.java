package balazs.urhajos;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;

import java.io.Serializable;

/**
 * A hajót adja meg.
 */
public class Ship extends Item implements Serializable {

    private int maxLives = 4;

    private transient IntegerProperty numOfLivesIP = new SimpleIntegerProperty(maxLives);

    private static Image spaceShipBase = new Image(FilePaths.SPACESHIP_BASE,
            Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT, false, false);

    private static Image spaceShipDest1 = new Image(FilePaths.SPACESHIP_DES1,
            Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT, false, false);

    private static Image spaceShipDest2 = new Image(FilePaths.SPACESHIP_DES2,
            Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT, false, false);

    private static Image spaceShipPropel = new Image(FilePaths.SPACESHIP_THRUST,
            Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT, false, false);

    protected double clockLength = 10.0;
    //clock length in ms, 1/clockLength = clockspeed
    private double movePerClockX = 0;
    private double movePerClockY = 0;
    private double rotatePerClock = 2.0;
    private double axisAngle = 0;

    private boolean setThrust = false;
    private boolean turnLeft = false;
    private boolean turnRight = false;

    public Ship(){
        this(FilePaths.SPACESHIP_BASE, Constants.PLAYER_WIDTH,
                Constants.PLAYER_HEIGHT, Constants.PLAYER_MAX_LIVES);
        setInitialPosition();
    }

    public Ship(String spriteName, double height, double width, int maxLives) {
        super(spriteName, height, width);
        this.maxLives = maxLives;
        numOfLivesIP.set(maxLives);
    }

    /**
     * szerializálás után állítja be az értékeket.
     */
    @Override
    public void initAfterSerialized(){
        super.initAfterSerialized();
        numOfLivesIP = new SimpleIntegerProperty(maxLives);
        numOfLivesIP.set(maxLives);
    }

    @Override
    public void destroyItem() {

    }

    /**
     * órajelnél mozog és változtatja a képét.
     */
    @Override
    public void actionAtClock() {
        steeringControl();
        movementControl();
        if(setThrust){
            setImage(spaceShipPropel);
        } else {
            setImage(spaceShipBase);
        }
    }

    /**
     * új játéknál középre állítja
     */
    private void setInitialPosition() {
        xLoc = (Constants.WINDOW_WIDTH - Constants.PLAYER_WIDTH)/2;
        yLoc = (Constants.WINDOW_HEIGHT - Constants.PLAYER_HEIGHT)/2;
        setX(xLoc);
        setY(yLoc);
        setRotate(axisAngle);
    }

    /**
     * figyeli a lenyomott gombokat.
     * @param dir
     */
    public void makeMovement(Constants.Directions dir) {
        switch (dir) {
            case THRUST:
                setThrust = true; break;
            case LEFT:
                turnRight = true; break;
            case RIGHT:
                turnLeft = true; break;
        }
    }

    /**
     * Megáll
     * @param dir
     */
    public void stopMovement(Constants.Directions dir) {
        switch (dir) {
            case THRUST:
                setThrust = false; break;
            case LEFT:
                turnRight = false; break;
            case RIGHT:
                turnLeft = false; break;
        }
    }

    /**
     * Lövés, létrehoz egy új missilet.
     */
    public void shoot() {
        double normAngle = normalizeAngle(axisAngle);
        double radians;
        radians = Math.tan(Math.toRadians(normAngle));
        boolean anglePosSign = true;

        if(normAngle < -90 || normAngle > 90){
            anglePosSign = false;
        }
        ((GamingPane)getParent()).getSetup().addMissile(
                new Missile(
                        getMiddleCoordinateX() - (Constants.MISSILE_WIDTH / 2),
                        getMiddleCoordinateY() - Constants.MISSILE_HEIGHT,
                        radians,
                        anglePosSign
                )
        );
    }

    /**
     * a szöget 0 és 360 közé állítja be.
     * @param angle
     * @return
     */
    private double normalizeAngle(double angle)
    {
        double newAngle = angle;
        while (newAngle <= -180) newAngle += 360;
        while (newAngle > 180) newAngle -= 360;
        return newAngle;
    }

    /**
     * mozgatja grafikusan jobbra és balra.
     */
    public void steeringControl() {
        if((turnLeft == true || turnRight == true) && turnRight != turnLeft){
            if(turnLeft){
                axisAngle = getRotate() + rotatePerClock;
                setRotate(axisAngle);
            }
            if(turnRight){
                axisAngle = getRotate() - rotatePerClock;
                setRotate(axisAngle);
            }
        }
        if(setThrust){
            increaseMovePerClockX(
                    Math.cos(Math.toRadians(axisAngle)) * Constants.SHIP_THRUST);
            increaseMovePerClockY(
                    Math.sin(Math.toRadians(axisAngle)) * Constants.SHIP_THRUST);
        }
    }

    /**
     * Előre és hátra mozgatja.
     */
    public void movementControl(){
        System.out.println(("X:" + getMovePerClockX()));
        System.out.println("Y:" + getMovePerClockY());
        if (getMovePerClockX() < 0) {
            if (getX() >= getMovePerClockX()) {
                setX(getX() + getMovePerClockX());
                xLoc = getX();
            }
            else {
                setMovePerClockX(0);
                setX(0);//pálya bal szélét érte el, stop
                xLoc = 0;
            }
        }else if (getMovePerClockX() > 0) {
            if (getX() < (Constants.WINDOW_WIDTH - width - getMovePerClockX())){

                setX(getX() + getMovePerClockX());
                xLoc = getX();
            }
            else {
                setX(Constants.WINDOW_WIDTH - width - getMovePerClockX());
                setMovePerClockX(0);
                xLoc = getX();
            }
        }
        if (getMovePerClockY() < 0) {
            if (getY() >= (getMovePerClockY() + height)) {
                setY(getY() + getMovePerClockY());
                yLoc = getY();
            }
            else {
                setMovePerClockY(0);
                setY(height);
                yLoc = getY();
            }
        } else if (getMovePerClockY() > 0) {
            if (getY() < (Constants.WINDOW_HEIGHT - height - getMovePerClockY()))
                setY(getY() + getMovePerClockY());
            else {
                setY(Constants.WINDOW_HEIGHT - height - getMovePerClockY());
                setMovePerClockY(0);
            }
        }
        yLoc = getY();
        xLoc = getX();
    }

    /**
     * A gravitáciít szimulálja.
     * @param p
     */
    public void accByGravity(Planet p){
        double xSquare = (Math.pow((p.getX()-getX()),2));
        double ySquare = (Math.pow((p.getY()-getY()),2));
        double absDist = Math.sqrt( xSquare + ySquare);
        increaseMovePerClockX((1/(p.getX()-getX()))*Constants.GRAVITY_ACC * (1/absDist));
        increaseMovePerClockY((1/(p.getY()-getY()))*Constants.GRAVITY_ACC * (1/absDist));
    }

    public double getMovePerClockX() {
        return movePerClockX;
    }

    public void setMovePerClockX(double movePerClockX) {
        this.movePerClockX = movePerClockX;
    }

    public void increaseMovePerClockX(double addMove){
        setMovePerClockX(getMovePerClockX() + addMove);
    }

    public double getMovePerClockY() {
        return movePerClockY;
    }

    public void setMovePerClockY(double movePerClockY) {
        this.movePerClockY = movePerClockY;
    }

    public void increaseMovePerClockY(double addMove){
        setMovePerClockY(getMovePerClockY() + addMove);
    }

    public int getMaxLives() {
        return maxLives;
    }

    public void setMaxLives(int maxLives) {
        this.maxLives = maxLives;
    }

    public int getNumOfLivesIP() {
        return numOfLivesIP.get();
    }

    public IntegerProperty numOfLivesIPProperty() {
        return numOfLivesIP;
    }

    public void setNumOfLivesIP(int numOfLivesIP) {
        this.numOfLivesIP.set(numOfLivesIP);
    }

    public void decreaseLives(){
        numOfLivesIP.set(numOfLivesIP.get() - 1);
    }
}
