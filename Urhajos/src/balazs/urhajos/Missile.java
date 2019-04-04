package balazs.urhajos;

import java.io.Serializable;

/**
 * Missile, azaz a lövedék értékeit kezeli.
 */
public class Missile extends Item implements Serializable {

    protected double movePerClockX = 0;
    protected double movePerClockY = 0;



    public Missile(double startX, double startY, double ratioXperY, boolean isAnglePositive){
        super(FilePaths.PROJECTILE, Constants.MISSILE_HEIGHT, Constants.MISSILE_WIDTH);
        xLoc = startX;
        yLoc = startY;
        setX(startX);
        setY(startY);
        setMovesWithRatio(ratioXperY, isAnglePositive);
    }

    /**
     * Beállítja a lövedék gyorsaságát.
     * @param ratioXperY: a hajó orrának iránya radianban
     * @param isAnglePositive: beállítja a szög elöjelét.
     */
    private void setMovesWithRatio(double ratioXperY, boolean isAnglePositive){
        double radians = Math.atan(ratioXperY);
        this.movePerClockY = isAnglePositive ? Math.sin(radians) * Constants.MISSILE_SPEED :
                Math.sin(radians) * Constants.MISSILE_SPEED * -1;
        this.movePerClockX = isAnglePositive ? Math.cos(radians) * Constants.MISSILE_SPEED :
                Math.cos(radians) * Constants.MISSILE_SPEED * -1;
    }

    /**
     * "destruktor"
     */
    @Override
    public void destroyItem() {
        super.setExists(false);
        super.setOpacity(0.25);
    }

    /**
     * mozog a clock-nál.
     */
    @Override
    public void actionAtClock() {
        movementControl();
    }

    /**
     * Beállítja, hogy egyenesen haladjon tovább, valamint hogy ne menjen ki a táblából
     */
    private void movementControl() {
        if (getMovePerClockX() < 0) {
            if (getX() >= getMovePerClockX()) setX(getX() + getMovePerClockX());
            else {
                setMovePerClockX(0);
                setX(0);//pálya bal szélét érte el, stop
            }
        }else if (getMovePerClockX() > 0) {
            if (getX() < (Constants.WINDOW_WIDTH - width - getMovePerClockX()))
                setX(getX() + getMovePerClockX());
            else {
                setX(Constants.WINDOW_WIDTH - width - getMovePerClockX());
                setMovePerClockX(0);
            }
        }
        if (getMovePerClockY() < 0) {
            if (getY() >= (getMovePerClockY() + height)) setY(getY() + getMovePerClockY());
            else {
                setMovePerClockY(0);
                setY(height);
            }
        } else if (getMovePerClockY() > 0) {
            if (getY() < (Constants.WINDOW_HEIGHT - height - getMovePerClockY()))
                setY(getY() + getMovePerClockY());
            else {
                setY(Constants.WINDOW_HEIGHT - height - getMovePerClockY());
                setMovePerClockY(0);
            }
        }
        xLoc = getX();
        yLoc = getY();
    }

    public double getMovePerClockX() {
        return movePerClockX;
    }

    public void setMovePerClockX(double movePerClockX) {
        this.movePerClockX = movePerClockX;
    }

    public double getMovePerClockY() {
        return movePerClockY;
    }

    public void setMovePerClockY(double movePerClockY) {
        this.movePerClockY = movePerClockY;
    }
}
