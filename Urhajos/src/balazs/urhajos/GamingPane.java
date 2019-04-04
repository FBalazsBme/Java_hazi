package balazs.urhajos;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.io.Serializable;

/**
 * A játék grafikus felületéért felel.
 */
public class GamingPane extends BorderPane implements Serializable {

    private GameSetup setup;

    private Label actualScore;
    private Label numOfLives;


    /**
     * Konstruktor, elmenti a Gamesatupot, hogy késöbb felhasználja.
     * @param setup
     */
    public GamingPane(GameSetup setup) {
        this.setup = setup;

        this.setBackground(new Background(new BackgroundFill(Color.rgb(setup.getRed(), setup.getGreen(), setup.getBlue()), null, null)));

        actualScore = new Label();
        actualScore.setMinWidth(Constants.WINDOW_WIDTH);
        actualScore.setFont(Font.loadFont(ClassLoader.getSystemResource(FilePaths.FONT).toExternalForm(),
                14));
        actualScore.setTextFill(Color.WHITE);
        actualScore.setAlignment(Pos.TOP_RIGHT);
        actualScore.setTextAlignment(TextAlignment.RIGHT);
        actualScore.setTranslateY(Constants.SCORE_SIZE + 2);

        numOfLives = new Label();
        numOfLives.setMinWidth(Constants.WINDOW_WIDTH);
        numOfLives.setFont(Font.loadFont(ClassLoader.getSystemResource(FilePaths.FONT).toExternalForm(),
                14));
        numOfLives.setTextFill(Color.WHITE);
        numOfLives.setAlignment(Pos.TOP_RIGHT);
        numOfLives.setTextAlignment(TextAlignment.RIGHT);
        numOfLives.setTranslateY(Constants.SCORE_SIZE + 2);

        this.getChildren().add(actualScore);
    }


    public void bindActualScore (ReadOnlyIntegerProperty score){
        actualScore.textProperty().bind(score.asString());
    }

    public void bindNumOfLives (ReadOnlyIntegerProperty lives){
        numOfLives.textProperty().bind(lives.asString());
    }

    public GameSetup getSetup() {
        return setup;
    }

    public void setSetup(GameSetup setup) {
        this.setup = setup;
    }

}
