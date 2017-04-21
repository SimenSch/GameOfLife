
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

/**
 *
 * @author Simen
 */
public class GolSSCG extends Application {

    GameOfLifeController glc;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("GameGUI.fxml"));
        glc = new GameOfLifeController();
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        //stage.setMaximized(true);
        stage.show();











    /*    glc.choosesize.selectedToggleProperty().addListener((p, ov, nv) -> {
            if(nv!= null){
                if(Objects.equals(nv.getUserData().toString(), "fullscreen")) {
                    stage.setMaximized(true);

                }
            }
        });
        */
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
