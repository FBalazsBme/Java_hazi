package balazs.urhajos;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;


/**
 * Kezeli a GameSetup serializálását.
 */
public class GameSetupWrapper implements Serializable {

    public static GameSetup gameSetup;
    public static Stage pStage;



    public GameSetupWrapper(Stage primaryStage){
        pStage = primaryStage;
        gameSetup = new GameSetup();
    }

    /**
     * Elmenti a GameSetupot
     */
    public static void saveState(){
        try {
            File outputFile = new File(FilePaths.FILESROOT, "saveGame.txt");
            ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream(outputFile));

            out.writeObject(gameSetup);
            out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Betölti a GameSetupot.
     */
    public static void loadState(){
        ObjectInputStream in= null;
        try {
            in = new ObjectInputStream(new FileInputStream(new File(FilePaths.FILESROOT, "saveGame.txt")));
            //GameSetup gameSetup2 = new GameSetup();
            GameSetup gameSetup2 = (GameSetup)in.readObject();
            gameSetup2.initAfterSerialized();
            gameSetup = gameSetup2;
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }
}
