

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

    public GraphicsContext gc;
    public GraphicsContext gc2;
    public GraphicsContext fileGc;
    public Filehandler fh;
    public int x;
    public int y;
    public int cellSize;
    // public byte[][] grid;
    public Filehandler saveAFile;
    public String background = "GameOfLife/src/Sounds/epic menu music.Wav";
    public Media backgroundSound = new Media(new File(background).toURI().toString());
    public MediaPlayer backgroundClick = new MediaPlayer(backgroundSound);
    GolSSCG main;
    GameOfLifeController glc;
    public DynamicBoard dynamicBoard;
    Timeline tLine;

    public GameOfLifeController() {
    }


    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cellSize = 10;
        x = (int) canvas.getWidth() / cellSize;
        y = (int) canvas.getHeight() / cellSize;
        dynamicBoard = new DynamicBoard(x, y);
        System.out.println(dynamicBoard.dynoBoard.get(1).get(1));
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
            public void handle(ActionEvent event) {
                regButtonClick();
                dynamicBoard.setRuleSet("regular");
            }
        });

        rule2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                regButtonClick();
                dynamicBoard.setRuleSet("special");
            }
        });

        fullScreen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                regButtonClick();
                Stage stage = (Stage) aPane.getScene().getWindow();
                stage.setMaximized(true);
                canvas.setWidth(2560);
                canvas.setHeight(1440);
                canvasBack.setWidth(2560);
                canvasBack.setHeight(1440);
                clearGrid();
                drawGrid();
                newArray();
            }
        });

        NormalWindow.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                regButtonClick();
                Stage stage = (Stage) aPane.getScene().getWindow();
                stage.setMaximized(false);
            }
        });
                /* Color Menu */
        Orange.setOnAction(new EventHandler<ActionEvent>() {
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


        canvasBack.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int x1 = (int) event.getX() / cellSize;
                int y1 = (int) event.getY() / cellSize;
                if (x1 < dynamicBoard.dynoBoard.size() && y1 < dynamicBoard.dynoBoard.get(1).size()) {
                    dynamicBoard.dynoBoard.get(x1).set(y1, 1);
                }

                gc.fillRect(x1 * cellSize, y1 * cellSize, cellSize, cellSize);
            }
        });
        canvasBack.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int x1 = (int) event.getX() / cellSize;
                int y1 = (int) event.getY() / cellSize;
                if (y1 < dynamicBoard.dynoBoard.get(1).size() && x1 < dynamicBoard.dynoBoard.size() && y1 >= 0 && x1 >= 0) {
                    if (dynamicBoard.dynoBoard.get(x1).get(y1) == 0) {
                        dynamicBoard.dynoBoard.get(x1).set(y1, 1);
                        gc.fillRect(x1 * cellSize, y1 * cellSize, cellSize, cellSize);
                    }
                }
            }
        });
    }

    @FXML
    public void start() {
        if ("Start".equals(start.getText())) {
            startButtonClick();
            runner = true;
            if (tLine != null) {
                tLine.stop();
            }
            tLine = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
                if (runner) {
                    backgroundClick.play();
                    long start = System.currentTimeMillis();
                    dynamicBoard.nextDynoGeneration(x, y);
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


    public void drawGrid() {
        for (double i = 0; i < canvas.getWidth(); i++) {
            for (double j = 0; j < canvas.getHeight(); j++) {
                gc2.strokeRect(i * cellSize, j * cellSize, cellSize, cellSize);
            }
        }
    }

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

    public void newArray() {
        x = 5 + (int) canvas.getWidth() / cellSize;
        y = 5 + (int) canvas.getHeight() / cellSize;
        dynamicBoard = new DynamicBoard(x, y);
    }

    public void reset() {
        clearButtonClick();
        clearCanvas();
        dynamicBoard.dynoBoard = new ArrayList<>(dynamicBoard.dynoBoard);

    }

    public void clearGrid() {
        gc2.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }


    public void clearCanvas() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }


    public void showPreviewLines() {
        for (double i = 0; i < fileCanvas.getWidth(); i++) {
            for (double j = 0; j < fileCanvas.getHeight(); j++) {
                fileGc.strokeRect(i * 10, j * 10, 10, 10);
            }
        }
    }

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

    public void clearButtonClick() {
        String standarButton = "GameOfLife/src/Sounds/clearClick2.mp3";
        Media buttonSound = new Media(new File(standarButton).toURI().toString());
        MediaPlayer buttonClick = new MediaPlayer(buttonSound);
        buttonClick.play();
    }

    public void menuButtonClick() {
        String standarButton = "GameOfLife/src/Sounds/clickSound.mp3";
        Media buttonSound = new Media(new File(standarButton).toURI().toString());
        MediaPlayer buttonClick = new MediaPlayer(buttonSound);
        buttonClick.play();
    }

    public void tinyButtonClick() {
        String standarButton = "GameOfLife/src/Sounds/tinyButton.mp3";
        Media buttonSound = new Media(new File(standarButton).toURI().toString());
        MediaPlayer buttonClick = new MediaPlayer(buttonSound);
        buttonClick.play();
    }

    public void pauseButtonClick() {
        String standarButton = "GameOfLife/src/Sounds/clickOff.mp3";
        Media buttonSound = new Media(new File(standarButton).toURI().toString());
        MediaPlayer buttonClick = new MediaPlayer(buttonSound);
        buttonClick.play();

    }

    public void regButtonClick() {
        String standarButton = "GameOfLife/src/Sounds/StandarButton.mp3";
        Media buttonSound = new Media(new File(standarButton).toURI().toString());
        MediaPlayer buttonClick = new MediaPlayer(buttonSound);
        buttonClick.play();
    }

    public void startButtonClick() {
        String standarButton = "GameOfLife/src/Sounds/clickOn.mp3";

        Media buttonSound = new Media(new File(standarButton).toURI().toString());
        MediaPlayer buttonClick = new MediaPlayer(buttonSound);

        buttonClick.play();


    }

    public void playSound() { //Prototype
        musc = true;
        String musicFile = "GameOfLife/src/Sounds/Mario.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        if ("Play".equals(mPlay.getText())) {
            startButtonClick();
            mPlay.setText("Mute");
            mediaPlayer.play();
        } else if ("Mute".equals(mPlay.getText())) {
            pauseButtonClick();
            mPlay.setText("Play");
            mediaPlayer.stop();
        } else {
            mediaPlayer.stop();
        }
    }


    @FXML
    protected void fileImport(ActionEvent event) throws NullPointerException, IOException, NumberFormatException, InvocationTargetException {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        File file = chooser.showOpenDialog(new Stage());
        if (file != null) {
            FileReader fileReader = new FileReader(file);
            newArray();
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
    public void openFile(ActionEvent event) {
        regButtonClick();
        menuFile.setVisible(true);
        menuFile.setManaged(true);
        System.out.println("Synelig filbehandler");
        blur.setVisible(true);
        showPreviewLines();

    }

    @FXML
    public void closeFile(ActionEvent event) {
        regButtonClick();
        menuFile.setVisible(false);
        menuFile.setManaged(false);
        blur.setVisible(false);
        draw();
        System.out.println("Usynelig filbehandler");
    }

    @FXML
    public void openMenu(ActionEvent event) {
        regButtonClick();
        menu.setVisible(true);
        menu.setManaged(true);
        System.out.println("Synelig");

    }

    @FXML
    public void RadioButtons(ActionEvent event) {
        if (small.isSelected()) {
            setCellSize(5);
            tinyButtonClick();
        } else if (normal.isSelected()) {
            setCellSize(10);
            tinyButtonClick();

        } else if (large.isSelected()) {
            setCellSize(20);
            tinyButtonClick();

        } else if (large2.isSelected()) {
            setCellSize(30);
            tinyButtonClick();

        } else if (large3.isSelected()) {
            setCellSize(40);
            tinyButtonClick();
        } else {
            setCellSize(50);
            tinyButtonClick();

        }
        clearGrid();
        drawGrid();
        clearCanvas();
        draw();
        newArray();
    }

    @FXML
    public void Apply(ActionEvent event) {
        regButtonClick();
        menu.setVisible(false);
        menu.setManaged(false);
        System.out.println("Usynelig");

    }

    @FXML
    public void Save() throws IOException {
        regButtonClick();
        fh.saveAFile(dynamicBoard.dynoBoard);
    }
}
