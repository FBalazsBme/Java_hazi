package balazs.urhajos;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Az osztály a grafikus felület és az azzal kapcsolatos számításokért felel.
 */
public class GameSetup implements Serializable {


    private transient GamingPane myView = new GamingPane(this);//Pane

    private transient Scene myScene = new Scene(myView, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
    private transient ComboBox ComboBox1 = new ComboBox();
    private transient ComboBox ComboBox2 = new ComboBox();
    private transient ComboBox ComboBox3 = new ComboBox();
    private transient Menu fileMenu = new Menu("Options");

    private transient Timeline boardTimeline;

    private transient IntegerProperty score = new SimpleIntegerProperty(0);
    private transient MenuBar menuBar;

    private Ship ship = new Ship();
    private Planet planet = new Planet(FilePaths.MARS, Constants.PLANET_LARGE_HEIGHT,
            Constants.PLANET_LARGE_HEIGHT);
    private ArrayList<Planet> planets = new ArrayList<>();
    private ArrayList<Missile> missiles = new ArrayList<>();

    private int red = 8;
    private int green = 8;
    private int blue = 8;

    //hogy ne kelljen keresés közben elemeket elvenni vagy hozzáadni,
    //a létrejövő, vagy a megsemmisített és törlendő objektumokat egy
    //külön listában tárolom.
    public ArrayList<Item> objectsToDelete = new ArrayList<>();

    public GameSetup() {
        initClass1();
    }

    /**
     * A metódust csak a konstruktorban használom, gyakorlatilag beállít minden paramétert,
     * kivéve azokat, amiket a fenti változódeklaráció nem állít be.
     */
    private void initClass1(){

        myView = new GamingPane(this);
        myScene = new Scene(myView, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        setPane(myView);
        setControlKeys();
        myView.getChildren().add(ship);
        addPlanets();
        for(Planet p : planets){
            myView.getChildren().add(p);
        }
        initTimelines();
        HBox hBox = new HBox();

        menuBar = new MenuBar();
        menuBar.prefWidth(180);

        MenuItem newMenuItem = new MenuItem("New");
        MenuItem saveMenuItem = new MenuItem("Save");
        MenuItem loadMenuItem = new MenuItem("Load");
        MenuItem themeMenuItem = new MenuItem("ToggleTheme");
        MenuItem exitMenuItem = new MenuItem("Exit");

        newMenuItem.setOnAction(e -> {try{         boardTimeline.stop();newGame();} catch (NullPointerException ex) {
            System.err.println("Ship does not exist!");
        }});
        saveMenuItem.setOnAction(e -> {try{GameSetupWrapper.saveState();} catch (NullPointerException ex) {
            System.err.println("Ship does not exist!");
        }});
        loadMenuItem.setOnAction(e -> {try{GameSetupWrapper.loadState();} catch (NullPointerException ex) {
            System.err.println("Ship does not exist!");
        }});
        themeMenuItem.setOnAction(e -> {try{toggleTheme();} catch (NullPointerException ex) {
            System.err.println("Ship does not exist!");
        }});
        exitMenuItem.setOnAction(actionEvent -> close());




        for(int i = 0; i< 250 ; i=i+10){
            ComboBox1.getItems().add(i);
            ComboBox2.getItems().add(i);
            ComboBox3.getItems().add(i);
        }

        fileMenu.getItems().addAll(newMenuItem, saveMenuItem, loadMenuItem, themeMenuItem,
                new SeparatorMenuItem(), exitMenuItem);

        menuBar.getMenus().add(fileMenu);
        GridPane grid = new GridPane();
        grid.setVgap(4);
        grid.setHgap(10);
        grid.setPadding(new Insets(5, 5, 5, 5));
        grid.add(new Label("To: "), 0, 0);
        grid.add(ComboBox1, 1, 0);
        grid.add(ComboBox2, 2, 0);
        grid.add(ComboBox3, 3, 0);


        ComboBox1.prefWidth(160);
        ComboBox2.prefWidth(160);
        ComboBox3.prefWidth(160);
        ComboBox1.getSelectionModel().selectFirst();
        ComboBox2.getSelectionModel().selectFirst();
        ComboBox3.getSelectionModel().selectFirst();

        hBox.getChildren().addAll(menuBar, ComboBox1, ComboBox2, ComboBox3);


        myView.setTop(hBox);
        GameSetupWrapper.pStage.setTitle("Urhajos");
        GameSetupWrapper.pStage.setScene(this.getMyScene());
        GameSetupWrapper.pStage.show();
    }

    /**
     * Hasonló az initclass1-hez, azzal a különbséggel, hogy egyes grafikus elemek paramátereit nem állítja be.
     * Ilyen pl. a Combobox, amihez nem adja újra hozzá a mezőket.
     */
    private void initClass2(){

        myView = new GamingPane(this);
        myScene = new Scene(myView, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        setPane(myView);
        setControlKeys();
        myView.getChildren().add(ship);
        addPlanets();
        for(Planet p : planets){
            myView.getChildren().add(p);
        }
        initTimelines();
        HBox hBox = new HBox();

        menuBar = new MenuBar();
        menuBar.prefWidth(180);

        Menu fileMenu = new Menu("Options");
        MenuItem newMenuItem = new MenuItem("New");
        MenuItem saveMenuItem = new MenuItem("Save");
        MenuItem loadMenuItem = new MenuItem("Load");
        MenuItem themeMenuItem = new MenuItem("ToggleTheme");
        MenuItem exitMenuItem = new MenuItem("Exit");

        newMenuItem.setOnAction(e -> {try{         boardTimeline.stop();newGame();} catch (NullPointerException ex) {
            System.err.println("Ship does not exist!");
        }});
        saveMenuItem.setOnAction(e -> {try{GameSetupWrapper.saveState();} catch (NullPointerException ex) {
            System.err.println("Ship does not exist!");
        }});
        loadMenuItem.setOnAction(e -> {try{GameSetupWrapper.loadState();} catch (NullPointerException ex) {
            System.err.println("Ship does not exist!");
        }});
        themeMenuItem.setOnAction(e -> {try{toggleTheme();} catch (NullPointerException ex) {
            System.err.println("Ship does not exist!");
        }});
        exitMenuItem.setOnAction(actionEvent -> close());



        fileMenu.getItems().addAll(newMenuItem, saveMenuItem, loadMenuItem, themeMenuItem,
                new SeparatorMenuItem(), exitMenuItem);

        menuBar.getMenus().add(fileMenu);
        GridPane grid = new GridPane();
        grid.setVgap(4);
        grid.setHgap(10);
        grid.setPadding(new Insets(5, 5, 5, 5));
        grid.add(new Label("To: "), 0, 0);
        grid.add(ComboBox1, 1, 0);
        grid.add(ComboBox2, 2, 0);
        grid.add(ComboBox3, 3, 0);


        ComboBox1.prefWidth(160);
        ComboBox2.prefWidth(160);
        ComboBox3.prefWidth(160);
        ComboBox1.getSelectionModel().selectFirst();
        ComboBox2.getSelectionModel().selectFirst();
        ComboBox3.getSelectionModel().selectFirst();

        hBox.getChildren().addAll(menuBar, ComboBox1, ComboBox2, ComboBox3);


        myView.setTop(hBox);
        GameSetupWrapper.pStage.setTitle("Urhajos");
        GameSetupWrapper.pStage.setScene(this.getMyScene());
        GameSetupWrapper.pStage.show();
    }

    /**
     * Elmenti a Comboboxba beírt értékeket.
     */
    private void toggleTheme() {
            red = (int)ComboBox1.getValue();
            blue = (int)ComboBox2.getValue();
            green = (int)ComboBox3.getValue();
    }

    /**
     * Bezárja az alkalmazást.
     */
    public void close(){
        Platform.exit();
    }

    /**
     * Felállítja az alkalmazást, miután betöltötte a fájlból, ezért a szerializált változókat nem kell újra beállítani.
     */
    public void initAfterSerialized(){
        planet = new Planet(FilePaths.MARS, Constants.PLANET_LARGE_HEIGHT,
                Constants.PLANET_LARGE_HEIGHT);
        score = new SimpleIntegerProperty(0);
        myView = new GamingPane(this);
        myScene = new Scene(myView, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        setPane(myView);
        setControlKeys();
        ship.initAfterSerialized();
        myView.getChildren().add(ship);
        for(Planet p : planets){
            p.initAfterSerialized();
            myView.getChildren().add(p);
        }
        initTimelines();

        HBox hBox = new HBox();

        menuBar = new MenuBar();
        menuBar.prefWidth(180);

        Menu fileMenu = new Menu("Options");
        MenuItem newMenuItem = new MenuItem("New");
        MenuItem saveMenuItem = new MenuItem("Save");
        MenuItem loadMenuItem = new MenuItem("Load");
        MenuItem themeMenuItem = new MenuItem("ToggleTheme");
        MenuItem exitMenuItem = new MenuItem("Exit");

        newMenuItem.setOnAction(e -> {try{         boardTimeline.stop();newGame();} catch (NullPointerException ex) {
            System.err.println("Ship does not exist!");
        }});
        saveMenuItem.setOnAction(e -> {try{GameSetupWrapper.saveState();} catch (NullPointerException ex) {
            System.err.println("Ship does not exist!");
        }});
        loadMenuItem.setOnAction(e -> {try{GameSetupWrapper.loadState();} catch (NullPointerException ex) {
            System.err.println("Ship does not exist!");
        }});
        themeMenuItem.setOnAction(e -> {try{toggleTheme();} catch (NullPointerException ex) {
            System.err.println("Ship does not exist!");
        }});
        exitMenuItem.setOnAction(actionEvent -> close());


        ComboBox1 = new ComboBox();
        ComboBox2 = new ComboBox();
        ComboBox3 = new ComboBox();

        for(int i = 0; i< 250 ; i=i+10){
            ComboBox1.getItems().add(i);
            ComboBox2.getItems().add(i);
            ComboBox3.getItems().add(i);
        }

        fileMenu.getItems().addAll(newMenuItem, saveMenuItem, loadMenuItem, themeMenuItem,
                new SeparatorMenuItem(), exitMenuItem);

        menuBar.getMenus().add(fileMenu);
        GridPane grid = new GridPane();
        grid.setVgap(4);
        grid.setHgap(10);
        grid.setPadding(new Insets(5, 5, 5, 5));
        grid.add(new Label("To: "), 0, 0);
        grid.add(ComboBox1, 1, 0);
        grid.add(ComboBox2, 2, 0);
        grid.add(ComboBox3, 3, 0);


        ComboBox1.prefWidth(160);
        ComboBox2.prefWidth(160);
        ComboBox3.prefWidth(160);
        ComboBox1.getSelectionModel().selectFirst();
        ComboBox2.getSelectionModel().selectFirst();
        ComboBox3.getSelectionModel().selectFirst();

        hBox.getChildren().addAll(menuBar, ComboBox1, ComboBox2, ComboBox3);


        myView.setTop(hBox);
        GameSetupWrapper.pStage.setTitle("Urhajos");
        GameSetupWrapper.pStage.setScene(this.getMyScene());
        GameSetupWrapper.pStage.show();

    }

    /**
     * Hozzáadja a bolygókat random sorrendben, random helyre, minden bolygót új típussal hoz létre.
     * Arra figyel, hogy ne generálja egymásra a bolygókat.
     */
    private void addPlanets() {
        int i = 0;
        Random rand = new Random();
        boolean placeReserved = false;

        while(i<3){

            int x = rand.nextInt(500) + 50;
            int y = rand.nextInt(500) + 50;
            int type = rand.nextInt(10);

            for(Planet p : planets){
                if(Math.abs(x - p.getX()) < Constants.PLANET_LARGE_HEIGHT ||
                        Math.abs(y - p.getY()) < Constants.PLANET_LARGE_HEIGHT){
                    placeReserved = true;
                }
            }

            if(!placeReserved){
                String spriteName = FilePaths.MARS;
                switch (i){
                    case 0:
                        spriteName = FilePaths.ASTEROID; break;
                    case 1:
                        spriteName = FilePaths.JUPITER; break;
                    case 2:
                        spriteName = FilePaths.MARS; break;
                    case 3:
                        spriteName = FilePaths.MERCURY; break;
                    case 4:
                        spriteName = FilePaths.NEPTUNE; break;
                    case 5:
                        spriteName = FilePaths.PLUTO; break;
                    case 6:
                        spriteName = FilePaths.SATURN; break;
                    case 7:
                        spriteName = FilePaths.SUN; break;
                    case 8:
                        spriteName = FilePaths.URANUS; break;
                    case 9:
                        spriteName = FilePaths.VENUS; break;
                }
                planet = new Planet(spriteName, Constants.PLANET_LARGE_HEIGHT,
                        Constants.PLANET_LARGE_HEIGHT);
                planet.setxLoc(x);
                planet.setyLoc(y);
                planets.add(planet);
                i++;
            }
            placeReserved = false;


        }

    }


    /**
     * Beállítja a Pane-t.
     * @param inputView
     */
    private void setPane(GamingPane inputView) {
        this.myView = inputView;
        myView.bindActualScore(score);
        myView.setSetup(this);
        myScene.setRoot(myView);
    }

    /**
     * Beállítja a játékot irányító gombokat.
     */
    private void setControlKeys() {
        myScene.setOnKeyPressed(e -> {
            try {
                switch (e.getCode()) {
                    case LEFT:
                    case A:
                        ship.makeMovement(Constants.Directions.LEFT); break;
                    case RIGHT:
                    case D:
                        ship.makeMovement(Constants.Directions.RIGHT); break;
                    case UP:
                    case W:
                        ship.makeMovement(Constants.Directions.THRUST); break;
                    case M:
                        ship.shoot(); break;
                    case ESCAPE:
                        pause(); break;
                }
            } catch (NullPointerException ex) {
                System.err.println("Ship does not exist!");
            }
        });

        myScene.setOnKeyReleased(e -> {
            try {
                switch (e.getCode()) {
                    case LEFT:
                    case A:
                        ship.stopMovement(Constants.Directions.LEFT); break;
                    case RIGHT:
                    case D:
                        ship.stopMovement(Constants.Directions.RIGHT); break;
                    case UP:
                    case W:
                        ship.stopMovement(Constants.Directions.THRUST); break;
                }
            } catch (NullPointerException ex) {
                System.err.println("Player does not exist.");
            }
        });
    }

    /**
     * A játék felfüggesztéséhez állítja be a paramétereket.
     */
    private void pause() {
        boardTimeline.pause();

        Label pauseLabel = new Label("PAUSED");
        pauseLabel.setFont(Font.loadFont(ClassLoader.getSystemResource(FilePaths.FONT).toExternalForm(),
                14));
        pauseLabel.setMinSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        pauseLabel.setAlignment(Pos.CENTER);
        pauseLabel.setTranslateY(-2);
        pauseLabel.setTextAlignment(TextAlignment.CENTER);
        pauseLabel.setTextFill(Color.WHITE);

        Rectangle background = new Rectangle(Constants.WINDOW_WIDTH, 30,
                new Color(0, 0, 0, 0.6));
        background.setX(0);
        background.setY((Constants.WINDOW_HEIGHT / 2) - 12);

        myView.getChildren().add( pauseLabel);

        background.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case M:
                    myView.getChildren().remove(pauseLabel);
                    play(); break;
                case ESCAPE:
                    Platform.exit(); break;
            }
        });
        background.requestFocus();
    }

    private void play() {
        boardTimeline.play();
    }

    /**
     * A játékban az objektumokat ütemező Timeline-t paraméterezi fel, és
     * beállítja mit tegyen a ciklusokban.
     */
    private void initTimelines(){

        boardTimeline = new Timeline(new KeyFrame(Duration.millis(Constants.CLOCK_LENGTH), e -> {
            for(Missile missile: missiles){
                missile.actionAtClock();

                if(missile.getY() < 0 || missile.getY() > Constants.WINDOW_HEIGHT ||
                missile.getX() < 0 || missile.getX() > Constants.WINDOW_WIDTH){
                    objectsToDelete.add(missile);
                }
                for(Planet planet: planets){
                    if(missile.intersects(planet.getX(), planet.getY(),
                            planet.getWidth(), planet.getHeight())){
                        planet.destroyItem();
                        missile.destroyItem();
                        objectsToDelete.add(planet);
                        objectsToDelete.add(missile);
                        score.setValue(score.getValue() + 1);
                    }
                    if(ship.intersects(planet.getX(), planet.getY(),
                            planet.getWidth(), planet.getHeight())){
                        finishGame();
                    }
                }
            }

            for(Planet planet: planets){
                if(ship.intersects(planet.getX(), planet.getY(),
                        planet.getWidth(), planet.getHeight())){
                    finishGame();
                }
                ship.accByGravity(planet);
            }

            for(Item item: objectsToDelete){
                //mindegy hogy missile vagy planet, ha nem abból
                //távolítom el, amiben van, akkor nincs semmi
                missiles.remove(item);
                planets.remove(item);
            }
            objectsToDelete.clear();

            ship.actionAtClock();

            if(ship.getNumOfLivesIP() < 1){
                finishGame();
            }

        }));

        boardTimeline.setCycleCount(Timeline.INDEFINITE);
        boardTimeline.play();

    }

    /**
     * Megjeleníti a képernyőt a játék elvesztésénél, valamint beállítja az akkor szükséges változókat.
     */
    public void finishGame() {
        boardTimeline.stop();
        Label gameOverLabel = new Label("GAME OVER, PRESS N FOR NEW GAME");
        gameOverLabel.setFont(Font.loadFont(ClassLoader.getSystemResource(FilePaths.FONT).toExternalForm(),
                18));
        gameOverLabel.setMinSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        gameOverLabel.setAlignment(Pos.CENTER);
        gameOverLabel.setTranslateY(-2);
        gameOverLabel.setTextAlignment(TextAlignment.CENTER);
        gameOverLabel.setTextFill(Color.WHITE);


        Rectangle background = new Rectangle(Constants.WINDOW_WIDTH, 30,
                new Color(0, 0, 0, 0.6));
        background.setX(0);
        background.setY((Constants.WINDOW_HEIGHT / 2) - 12);


        myView.setCenter(gameOverLabel);
        gameOverLabel.requestFocus();

        //myView.getChildren().addAll(background, gameOverLabel);
        //restart, N-et nyom
        gameOverLabel.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case N:
                    newGame(); break;
                case ESCAPE:
                    Platform.exit(); break;
            }
        });
        background.requestFocus();

    }

    /**
     * Új játék esetén üríti a listákat, és újrainicializűlja.
     */
    private void newGame() {

        planets.clear();
        missiles.clear();
        objectsToDelete.clear();
        setControlKeys();
        myView.getChildren().clear();

        ship = new Ship();
        score = new SimpleIntegerProperty(0);
        planets = new ArrayList<>();
        missiles = new ArrayList<>();
        objectsToDelete = new ArrayList<>();

        initClass2();

    }


    /**
     * Új lövedéket ad hozzá
     * @param missile
     */
    public void addMissile(Missile missile) {
        missiles.add(missile);
        myView.getChildren().add(missile);
    }

    /**
     * Ezután jönnek a getter és setter metódusok.
     * @return
     */
    public ArrayList<Missile> getMissiles() {
        return missiles;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public GamingPane getMyView() {
        return myView;
    }

    public void setMyView(GamingPane myView) {
        this.myView = myView;
    }

    public Scene getMyScene() {
        return myScene;
    }

    public void setMyScene(Scene myScene) {
        this.myScene = myScene;
    }
}
