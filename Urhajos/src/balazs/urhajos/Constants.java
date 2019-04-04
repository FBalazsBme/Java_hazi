package balazs.urhajos;

/**
 * Ez az osztály tartalmazza az alkalmazás összes konstansát, kivéve a fájlok elérési útját
 */
public class Constants {

    public static final double WINDOW_WIDTH = 640;
    public static final double WINDOW_HEIGHT = 640;


    public static final double PLAYER_WIDTH = 50;
    public static final double PLAYER_HEIGHT = 60;
    public static final double SHIP_THRUST = 0.2;
    public static final double PLANET_SMALL_WIDTH = 32;
    public static final double PLANET_SMALL_HEIGHT = 32;
    public static final double PLANET_MID1_WIDTH = 48;
    public static final double PLANET_MID1_HEIGHT = 48;
    public static final double PLANET_MID2_WIDTH = 64;
    public static final double PLANET_MID2_HEIGHT = 64;
    public static final double PLANET_LARGE_WIDTH = 96;
    public static final double PLANET_LARGE_HEIGHT = 96;
    public static final double MISSILE_WIDTH = 8;
    public static final double MISSILE_HEIGHT = 8;
    public static final double PROJECTILE_ENEMY_SMALL_WIDTH = 6;
    public static final double PROJECTILE_ENEMY_SMALL_HEIGHT = 6;
    public static final double PROJECTILE_ENEMY_LARGE_WIDTH = 6;
    public static final double PROJECTILE_ENEMY_LARGE_HEIGHT = 14;

    //global clock interval [ms]
    public static final double CLOCK_LENGTH = 10;

    //speed of entities [px/clk_length]
    public static final double MISSILE_SPEED = 10;
    public static final double SHIP_SPEED = 2;
    public static final double GRAVITY_ACC = 2;

    public enum Directions {THRUST, LEFT, RIGHT};
    public static final double SCORE_SIZE = 32;
    public static final int PLAYER_MAX_LIVES = 4;

}
