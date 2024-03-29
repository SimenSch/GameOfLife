

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.omg.DynamicAny.DynAnyOperations;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


/**
 * @author Simen & Snorre
 */
public class GameOfLifeController implements Initializable {
    @FXML
    Canvas canvas, canvasBack, fileCanvas;
    @FXML
    public AnchorPane aPane;
    @FXML
    public Pane menuFile, menu, nedeBtn;
    @FXML
    public Rectangle rec, blur;
    @FXML
    public MenuBar modBar;
    @FXML
    public MenuButton setSize, setTheme;
    @FXML
    public RadioMenuItem BW, Orange, NormalWindow, fullscreen, rule1, rule2;
    @FXML
    public MenuItem fullScreen, modItemFile;
    @FXML
    public RadioButton small, normal, large, large2, large3, large4;
    @FXML
    public ToggleGroup choosesize;
    @FXML
    Button clear, start, mPlay;
    @FXML
    public Slider zSlider;

    private Color[] colors = {Color.RED, Color.ALICEBLUE, Color.BISQUE, Color.MAROON, Color.FIREBRICK, Color.BURLYWOOD, Color.SEAGREEN, Color.CORNSILK,};
    private Color blue = Color.BLUEVIOLET;
    public int msTime;
    public boolean runner;
    public boolean musc;
    public boolean set;
    boolean soundIsMuted = false;
    public GraphicsContext gc;
    public GraphicsContext gc2;
    public GraphicsContext fileGc;
    public Filehandler fh;
    public int x;
    public int y;
    public int cellSize =10;
    // public byte[][] grid;
    public Filehandler saveAFile;
    public String background = "GameOfLife/src/Sounds/epic menu music.Wav";
    public Media backgroundSound = new Media(new File(background).toURI().toString());
    public MediaPlayer backgroundClick = new MediaPlayer(backgroundSound);
    GolSSCG main;
    GameOfLifeController glc;
    public DynamicBoard dynamicBoard;
    Timeline tLine;

    /**
     * Construktør
     */
    public GameOfLifeController() {
    }

    /**
     * Setter cellestørrelsen på brettet
     * @param cellSize
     */
    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
    }

    /**
     * setter skjermen til normal størrelse inkludert Brettstørrelsen
     */
    public void setNormalScreen(){
        canvas.setWidth(957.0);
        canvas.setHeight(757.0);
        canvasBack.setWidth(957.0);
        canvasBack.setHeight(757.0);

        clearGrid();
        drawGrid();
        dynamicBoard.fullscreenExpand((int)canvas.getHeight()/cellSize,(int)canvas.getWidth()/cellSize);
        //newArray();
    }

    /**
     * Setter brettet til full størrelse #FEIL i denne, vi fikk ikke til å sette rader riktig under.
     */
    public void setFullscreen(){
        Stage stage = (Stage) aPane.getScene().getWindow();
        double canvasHeight = stage.getHeight();
        double canvasWidth = stage.getWidth();
        canvas.setWidth((canvasWidth));
        canvas.setHeight(canvasHeight);
        canvasBack.setWidth(canvasWidth);
        canvasBack.setHeight(canvasHeight);

        clearGrid();
        drawGrid();
        dynamicBoard.fullscreenExpand((int)canvas.getHeight()/cellSize,(int)canvas.getWidth()/cellSize);

    }


    @Override
    /**
     * Initializeren
     */
    public void initialize(URL url, ResourceBundle rb) {
        x = (int) canvas.getWidth() / cellSize;
        y = (int) canvas.getHeight() / cellSize;
        dynamicBoard = new DynamicBoard((int)canvas.getWidth()/cellSize,(int)canvas.getHeight()/cellSize);
        //rule = new Rules(this);
        glc = new GameOfLifeController();
        main = new GolSSCG();
        fh = new Filehandler();
        dynamicBoard.setRuleSet("regular");
        //grid = new byte[x][y];
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.ORANGE);
        gc2 = canvasBack.getGraphicsContext2D();
        fileGc = fileCanvas.getGraphicsContext2D();
        menu.setVisible(false);
        menu.setManaged(false);
        menuFile.setVisible(false);
        menuFile.setManaged(false);
        msTime = 100;
        drawGrid();
        draw();


        System.out.println("Working Directory = " +
                System.getProperty("user.dir"));

        rule1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            /**
             * Setter regler
             */
            public void handle(ActionEvent event) {
                regButtonClick();
                dynamicBoard.setRuleSet("regular");
            }
        });

        rule2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            /**
             * setter regler
             */
            public void handle(ActionEvent event) {
                regButtonClick();
                dynamicBoard.setRuleSet("special");
            }
        });

        fullScreen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            /**
             * setter fullescreen
             */
            public void handle(ActionEvent event) throws NullPointerException{
                regButtonClick();
                Stage stage = (Stage) aPane.getScene().getWindow();
                stage.setMaximized(true);
                setFullscreen();

            }
        });

        NormalWindow.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            /**
             * setter normalScreen
             */
            public void handle(ActionEvent event) {
                regButtonClick();
                Stage stage = (Stage) aPane.getScene().getWindow();
                stage.setMaximized(false);
                setNormalScreen();
            }
        });
                /* Color Menu */
        Orange.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * fargevelger
             * @param t
             */
            public void handle(ActionEvent t) {
                regButtonClick();
                clearGrid();
                gc2.setStroke(Color.BLACK);
                drawGrid();
                gc.setFill(Color.ORANGE);
                aPane.setStyle("-fx-background-color: #617073");
                modBar.setStyle("-fx-background-color: #1E2425");
                nedeBtn.setStyle("-fx-background-color: #1E2425");
                rec.setStyle("-fx-background-color: #1E2425");

            }
        });
        BW.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * Svart hvitt velger
             * @param t
             */
            public void handle(ActionEvent t) {
                regButtonClick();
                clearGrid();
                gc2.setStroke(Color.DARKGRAY);
                drawGrid();
                gc.setFill(Color.BLACK);
                aPane.setStyle("-fx-background-color: white");
                modBar.setStyle("-fx-background-color: dimgrey");
                nedeBtn.setStyle("-fx-background-color: dimgrey");
                rec.setStyle("-fx-background-color: dimgrey");

            }
        });

        zSlider.valueProperty().addListener((ov, old_val, new_val) -> {
            msTime = new_val.intValue();
        });


        canvasBack.setOnMouseClicked(new EventHandler<MouseEvent>()  {
            @Override
            /**
             * setter celle i live ved klikk
             */
            public void handle(MouseEvent event) throws IndexOutOfBoundsException, NullPointerException{
                int x1 = (int) event.getX() / cellSize;
                int y1 = (int) event.getY() / cellSize;

                if (x1 < dynamicBoard.dynoBoard.get(0).size() && y1 < dynamicBoard.dynoBoard.size()) {
                    System.out.println("inside board");
                    dynamicBoard.dynoBoard.get(x1).set(y1, 1);
                    gc.fillRect(x1 * cellSize, y1 * cellSize, cellSize, cellSize);
                }

                else if(x1 == dynamicBoard.dynoBoard.size() || y1 == dynamicBoard.dynoBoard.get(0).size()){
                    System.out.println("expand board");

                    System.out.println(dynamicBoard.dynoBoard.get(x1).get(y1));
                    gc.fillRect(x1 * cellSize, y1 * cellSize, cellSize, cellSize);
                }
             }
        });
        canvasBack.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            /**
             * tegnemetode kan brukes under spillets gang
             */
            public void handle(MouseEvent event) {
                int x1 = (int) event.getX() / cellSize;
                int y1 = (int) event.getY() / cellSize;
                if (y1 < dynamicBoard.dynoBoard.get(0).size() && x1 < dynamicBoard.dynoBoard.size() && y1 >= 0 && x1 >= 0) {
                    if (dynamicBoard.dynoBoard.get(x1).get(y1) == 0) {
                        dynamicBoard.dynoBoard.get(x1).set(y1, 1);
                        gc.fillRect(x1 * cellSize, y1 * cellSize, cellSize, cellSize);
                    }
                }
            }
        });
    }

    @FXML
    /**
     * startmetoden
     */
    public void start() throws IndexOutOfBoundsException{
        if ("Start".equals(start.getText())) {
            startButtonClick();
            runner = true;
            if (tLine != null) {
                tLine.stop();
            }
            tLine = new Timeline(new KeyFrame(Duration.millis(msTime), event -> {
                if (runner) {
                    backgroundClick.play();
                    long start = System.currentTimeMillis();
                    dynamicBoard.nextDynoGeneration();
                    clearCanvas();
                    draw();
                    long elapsed = System.currentTimeMillis() - start;
                    System.out.println("Time between frames (ms): " + elapsed);
                } else {
                    tLine.stop();
                    backgroundClick.stop();
                }
            }));

            tLine.setCycleCount(Animation.INDEFINITE);
            tLine.play();
            start.setText("Pause");
        }
        else{
        pauseButtonClick();
        start.setText("Start");
        runner = false;
        }

    }

    /**
     * tegner griddet slik at man ikke må gjøre det hver iterasjon
     */
    public void drawGrid() {
        for (double i = 0; i < canvas.getWidth(); i++) {
            for (double j = 0; j < canvas.getHeight(); j++) {
                gc2.strokeRect(i * cellSize, j * cellSize, cellSize, cellSize);
            }
        }
    }

    /**
     * sjekker om en celle er levenede eller død, fargelegger
     */
    public void draw() {
        for (int i = 1; i < dynamicBoard.dynoBoard.size(); i++) {
            for (int j = 1; j < dynamicBoard.dynoBoard.get(i).size(); j++) {
                if (dynamicBoard.dynoBoard.get(i).get(j) == 1) {//kan hende importert array er større enn grid[][]
                    gc.fillRect(i * (cellSize), j * (cellSize), cellSize, cellSize);
                } else {

                }

            }
        }
    }


    /**
     * Resetter arraylisten
     */
    public void reset() {
        clearButtonClick();
        clearCanvas();
        dynamicBoard.dynoBoard = new ArrayList<>(dynamicBoard.dynoBoard);

    }

    /**
     * fjerner alle farger i grid
     */
    public void clearGrid() {
        gc2.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    /**
     * fjerner alt i canvas av farger
     */
    public void clearCanvas() {
        gc.clearRect(0, 0, canvasBack.getWidth(), canvasBack.getHeight());

    }

    /**
     * Viser griddet i preview
     */
    public void showPreviewLines() {
        for (double i = 0; i < fileCanvas.getWidth(); i++) {
            for (double j = 0; j < fileCanvas.getHeight(); j++) {
                fileGc.strokeRect(i * 10, j * 10, 10, 10);
            }
        }
    }

    /**
     * denne viser fargeleggingen over Griddet i showPreviewlines
     * @param previewArray
     */
    public void showPreviewPattern(ArrayList<ArrayList<Integer>> previewArray) {
        fileGc.clearRect(0, 0, fileCanvas.getWidth(), fileCanvas.getHeight());
        showPreviewLines();
        for (int i = 1; i < previewArray.size(); i++) {
            for (int j = 1; j < previewArray.get(i).size(); j++) {
                if (dynamicBoard.dynoBoard.get(i).get(j) == 1) {//kan hende importert array er større enn grid[][]
                    fileGc.fillRect(i * (cellSize), j * (cellSize), cellSize, cellSize);
                } else {

                }

            }
        }
    }

    /**
     * Musikk til knapp
     */
    public void clearButtonClick() {
        String standarButton = "GameOfLife/src/Sounds/clearClick2.mp3";
        Media buttonSound = new Media(new File(standarButton).toURI().toString());
        MediaPlayer buttonClick = new MediaPlayer(buttonSound);
        buttonClick.play();
    }
    /**
     * Musikk til knapp
     */
    public void tinyButtonClick() {
        String standarButton = "GameOfLife/src/Sounds/tinyButton.mp3";
        Media buttonSound = new Media(new File(standarButton).toURI().toString());
        MediaPlayer buttonClick = new MediaPlayer(buttonSound);
        buttonClick.play();
    }
    /**
     * Musikk til knapp
     */
    public void pauseButtonClick() {
        String standarButton = "GameOfLife/src/Sounds/clickOff.mp3";
        Media buttonSound = new Media(new File(standarButton).toURI().toString());
        MediaPlayer buttonClick = new MediaPlayer(buttonSound);
        buttonClick.play();

    }
    /**
     * Musikk til knapp
     */
    public void regButtonClick() {
        String standarButton = "GameOfLife/src/Sounds/StandarButton.mp3";
        Media buttonSound = new Media(new File(standarButton).toURI().toString());
        MediaPlayer buttonClick = new MediaPlayer(buttonSound);
        buttonClick.play();
    }
    /**
     * Musikk til knapp
     */
    public void startButtonClick() {
        String standarButton = "GameOfLife/src/Sounds/clickOn.mp3";

        Media buttonSound = new Media(new File(standarButton).toURI().toString());
        MediaPlayer buttonClick = new MediaPlayer(buttonSound);

        buttonClick.play();


    }
    /**
     * muter musikk
     */
    public void playSound() {
        if(soundIsMuted) {
            backgroundClick.setVolume(1);
        }else{
            backgroundClick.setVolume(0);
        }
        soundIsMuted =! soundIsMuted;
    }




    @FXML
    /**
     * knapp til å velge fil å importere
     */
    protected void fileImport(ActionEvent event) throws NullPointerException, IOException, NumberFormatException, InvocationTargetException {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        File file = chooser.showOpenDialog(new Stage());
        if (file != null) {
            FileReader fileReader = new FileReader(file);

            if (file.getName().endsWith(".txt")) {
                dynamicBoard.dynoBoard = fh.goThroughFile(fileReader, dynamicBoard.dynoBoard);
                showPreviewPattern(dynamicBoard.dynoBoard);
                clearCanvas();
            } else if (file.getName().endsWith(".rle")) {
                dynamicBoard.dynoBoard = fh.readRleFile(fileReader, dynamicBoard.dynoBoard);
                showPreviewPattern(dynamicBoard.dynoBoard);
                clearCanvas();
            }
        }
    }

    @FXML
    /**
     * åpner fil
     */
    public void openFile(ActionEvent event) {
        regButtonClick();
        menuFile.setVisible(true);
        menuFile.setManaged(true);
        System.out.println("Synelig filbehandler");
        blur.setVisible(true);
        showPreviewLines();

    }

    @FXML
    /**
     * lukker filbehandler
     */
    public void closeFile(ActionEvent event) {
        regButtonClick();
        menuFile.setVisible(false);
        menuFile.setManaged(false);
        blur.setVisible(false);
        draw();
        System.out.println("Usynelig filbehandler");
    }

    @FXML
    /**
     * Åpner menyen
     */
    public void openMenu(ActionEvent event) {
        regButtonClick();
        menu.setVisible(true);
        menu.setManaged(true);
        System.out.println("Synelig");

    }

    @FXML
    /**
     * velgere av cellestørrelse
     */
    public void RadioButtons(ActionEvent event) {
        if (small.isSelected()) {
            setCellSize(3);
            tinyButtonClick();
            dynamicBoard.fullscreenExpand((int)canvas.getHeight()/cellSize,(int)canvas.getWidth()/cellSize);

        } else if (normal.isSelected()) {
            setCellSize(5);
            tinyButtonClick();
            dynamicBoard.fullscreenExpand((int)canvas.getHeight()/cellSize,(int)canvas.getWidth()/cellSize);

        } else if (large.isSelected()) {
            setCellSize(10);
            tinyButtonClick();
            dynamicBoard.fullscreenExpand((int)canvas.getHeight()/cellSize,(int)canvas.getWidth()/cellSize);

        } else if (large2.isSelected()) {
            setCellSize(20);
            tinyButtonClick();
            dynamicBoard.fullscreenExpand((int)canvas.getHeight()/cellSize,(int)canvas.getWidth()/cellSize);

        } else if (large3.isSelected()) {
            setCellSize(30);
            tinyButtonClick();
            dynamicBoard.fullscreenExpand((int)canvas.getHeight()/cellSize,(int)canvas.getWidth()/cellSize);

        } else {
            setCellSize(40);
            tinyButtonClick();
            dynamicBoard.fullscreenExpand((int)canvas.getHeight()/cellSize,(int)canvas.getWidth()/cellSize);


        }
        clearGrid();
        drawGrid();
        clearCanvas();
        draw();

    }

    @FXML
    /**
     * appilerer det man har valgt
     */
    public void Apply(ActionEvent event) {
        regButtonClick();
        menu.setVisible(false);
        menu.setManaged(false);
        System.out.println("Usynelig");

    }

    @FXML
    /**
     * lagrer filen du skal exportere
     */
    public void Save() throws IOException {
        regButtonClick();
        fh.saveAFile(dynamicBoard.dynoBoard);
    }
}
