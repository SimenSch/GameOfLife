

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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Simen & Snorre
 */
public class GameOfLifeController implements Initializable {
    @FXML
    Canvas canvas;
    @FXML
    Canvas canvasBack;
    @FXML
    Canvas fileCanvas;
    @FXML
    Button clear;
    @FXML
    Button start;
    @FXML
    public Pane menuFile;
    @FXML
    public Pane menu;
    @FXML
    public RadioButton small;
    @FXML
    public RadioButton normal;
    @FXML
    public RadioButton large;
    @FXML
    public RadioButton large2;
    @FXML
    public RadioButton large3;
    @FXML
    public RadioButton large4;
    @FXML
    public MenuItem fullScreen;
    @FXML
    public ToggleGroup choosesize;
    @FXML
    public RadioMenuItem BW;
    @FXML
    public RadioMenuItem Orange;
    @FXML
    public RadioMenuItem NormalWindow;
    @FXML
    public RadioMenuItem fullscreen;
    @FXML
    public AnchorPane aPane;
    @FXML
    public MenuBar modBar;
    @FXML
    public Pane nedeBtn;
    @FXML
    public Rectangle rec, blur;
    @FXML
    public Slider zSlider;
    private Color[] colors = {Color.RED, Color.ALICEBLUE, Color.BISQUE, Color.MAROON, Color.FIREBRICK, Color.BURLYWOOD, Color.SEAGREEN, Color.CORNSILK,};
    private Color blue = Color.BLUEVIOLET;
    public int intervalPeriod;
    public boolean runner;
    public boolean set;
    public Rules rule;
    public GraphicsContext gc;
    public GraphicsContext gc2;
    public GraphicsContext fileGc;
    public Filehandler fh;
    public int x;
    public int y;
    public int cellSize;
    public byte[][] grid;
    public Filehandler saveAFile;
    Timer timer;
    GolSSCG main;
    GameOfLifeController glc;

    public GameOfLifeController() {
    }


    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rule = new Rules(this);
        glc = new GameOfLifeController();
        main = new GolSSCG();
        fh = new Filehandler();
        cellSize = 10;
        x = 5 + (int) canvas.getWidth() / cellSize;
        y = 5 + (int) canvas.getHeight() / cellSize;
        grid = new byte[x][y];
        gc = canvas.getGraphicsContext2D();
        gc2 = canvasBack.getGraphicsContext2D();
        fileGc = fileCanvas.getGraphicsContext2D();
        menu.setVisible(false);
        menu.setManaged(false);
        menuFile.setVisible(false);
        menuFile.setManaged(false);
        gc.setFill(Color.ORANGE);
        intervalPeriod = 100;
        drawGrid();
        draw();

        System.out.println("Working Directory = " +
                System.getProperty("user.dir"));

        fullScreen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) aPane.getScene().getWindow();
                stage.setMaximized(true);
                canvas.setWidth(1980);
                canvas.setHeight(1080);
                canvasBack.setWidth(1980);
                canvasBack.setHeight(1080);
                clearGrid();
                drawGrid();
                newArray();
            }
        });

        NormalWindow.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) aPane.getScene().getWindow();
                stage.setMaximized(false);
            }
        });
                /* Color Menu */
        Orange.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
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
            intervalPeriod = new_val.intValue();
        });


        canvasBack.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int x1 = (int) event.getX() / cellSize;
                int y1 = (int) event.getY() / cellSize;
                grid[x1][y1] = 1;

                gc.fillRect(x1 * cellSize, y1 * cellSize, cellSize, cellSize);
            }
        });
        canvasBack.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int x1 = (int) event.getX() / cellSize;
                int y1 = (int) event.getY() / cellSize;
                //gc.setFill(Color.CRIMSON);
                if (y1 < y && x1 < x && y1 >= 0 && x1 >= 0) {
                    gc.fillRect(x1 * cellSize, y1 * cellSize, cellSize, cellSize);
                    if (grid[x1][y1] == 0) {
                        grid[x1][y1] = 1;
                    } else {
                    }
                } else {
                }
            }
        });
    }

    @FXML
    public void start() {
        if ("Start".equals(start.getText())) {
            if (timer != null) {
                timer.cancel();
                timer.purge();
            }
            runner = true;
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    if (runner) {
                        long start = System.currentTimeMillis();
                        rule.nextGeneration();
                        clearCanvas();
                        draw();
                        long elapsed = System.currentTimeMillis() - start;
                        System.out.println("Time between frames (ms): " + elapsed); //it changes alot, this it's because the sound?
                    } else {
                        timer.cancel();
                        timer.purge();
                    }
                }
            };
            int waitSecounds = 0;
            timer.scheduleAtFixedRate(task, waitSecounds, intervalPeriod);
            start.setText("Pause");
        } else {
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
        for (int i = 1; i < grid.length; i++) {
            for (int j = 1; j < grid[i].length; j++) {
                if (grid[i][j] == 1) {//kan hende importert array er større enn grid[][]
                    gc.fillRect(i * (cellSize), j * (cellSize), cellSize, cellSize);
                } else {

                }

            }
        }
    }

    public void newArray() {
        x = 5 + (int) canvas.getWidth() / cellSize;
        y = 5 + (int) canvas.getHeight() / cellSize;
        grid = new byte[x][y];
    }

    public void reset() {
        clearCanvas();
        grid = new byte[x][y];

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

    public void showPreviewPattern(byte[][] previewArray) {
        fileGc.clearRect(0, 0, fileCanvas.getWidth(), fileCanvas.getHeight());
        showPreviewLines();
        for (int i = 1; i < previewArray.length; i++) {
            for (int j = 1; j < previewArray[i].length; j++) {
                if (grid[i][j] == 1) {//kan hende importert array er større enn grid[][]
                    fileGc.fillRect(i * (cellSize), j * (cellSize), cellSize, cellSize);
                } else {

                }

            }
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
            grid = fh.parseFile(fileReader, grid);
            showPreviewPattern(grid);
            clearCanvas();
            draw();
        }
    }

    @FXML
    public void openFile(ActionEvent event) {
        menuFile.setVisible(true);
        menuFile.setManaged(true);
        System.out.println("Synelig filbehandler");
        blur.setVisible(true);
        showPreviewLines();

    }

    @FXML
    public void closeFile(ActionEvent event) {
        menuFile.setVisible(false);
        menuFile.setManaged(false);
        blur.setVisible(false);
        System.out.println("Usynelig filbehandler");
    }

    @FXML
    public void openMenu(ActionEvent event) {
        menu.setVisible(true);
        menu.setManaged(true);
        System.out.println("Synelig");
    }

    @FXML
    public void RadioButtons(ActionEvent event) {
        if (small.isSelected()) {
            setCellSize(5);
        } else if (normal.isSelected()) {
            setCellSize(10);

        } else if (large.isSelected()) {
            setCellSize(20);

        } else if (large2.isSelected()) {
            setCellSize(30);

        } else if (large3.isSelected()) {
            setCellSize(40);
        } else {
            setCellSize(50);

        }
        clearGrid();
        drawGrid();
        clearCanvas();
        draw();
        newArray();
    }

    @FXML
    public void Apply(ActionEvent event) {
        menu.setVisible(false);
        menu.setManaged(false);
        System.out.println("Usynelig");

    }

    @FXML
    public void Save() throws IOException {
        fh.saveAFile(grid);
    }
}
