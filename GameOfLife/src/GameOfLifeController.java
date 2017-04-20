

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.FileHandler;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.canvas.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;

/**
 *
 * @author Simen
 */
public class GameOfLifeController implements Initializable {
    @FXML
    Canvas canvas;
    @FXML
    Canvas canvasBack;
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
    private Color[] colors = {Color.RED, Color.ALICEBLUE, Color.BISQUE, Color.MAROON, Color.FIREBRICK, Color.BURLYWOOD, Color.SEAGREEN, Color.CORNSILK,};
    private Color blue = Color.BLUEVIOLET;
    public int intervalPeriod;
    public boolean runner;
    public boolean set;
    public Rules rule = new Rules(this);
    public GraphicsContext gc;
    public GraphicsContext gc2;
    public Filehandler fh;
    public int x = 1000;
    public int y = 1000;
    public int cellSize = 10;
    public int[][] grid = new int[x][y];
    Timer timer;




    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gc = canvas.getGraphicsContext2D();
        gc2 = canvasBack.getGraphicsContext2D();
        menu.setVisible(false);
        menu.setManaged(false);
        menuFile.setVisible(false);
        menuFile.setManaged(false);
        drawGrid();
        draw();

        canvasBack.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                gc.setFill(Color.ORANGE);
                int x1 = (int) event.getX() / cellSize;
                int y1 = (int) event.getY() / cellSize;
                grid[x1][y1] = 1;

                gc.fillRect(x1 * cellSize, y1 * cellSize, cellSize, cellSize);
            }
        });
        canvasBack.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                gc.setFill(Color.ORANGE);
                int x1 = (int) event.getX() / cellSize;
                int y1 = (int) event.getY() / cellSize;

                //gc.setFill(Color.CRIMSON);

                if (y1 < y && x1 < x && y1 >= 0 && x1 >= 0) {
                    gc.fillRect(x1 * cellSize, y1 * cellSize, cellSize, cellSize);

                    if(grid[x1][y1] ==0){
                        grid[x1][y1] = 1;
                    }
                    else{

                    }
                } else {
                }
            }
        });
    }

    public void drawGrid() {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                gc2.strokeRect(i * cellSize, j * cellSize, cellSize, cellSize);
            }
        }
    }

    public void reset() {
        clearCanvas();
        grid = new int[x][y];

    }

    public void clearGrid(){
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                gc2.clearRect(i * cellSize, j * cellSize, cellSize, cellSize);
            }
        }
    }

    @FXML
    public void start() {
        if ("Start".equals(start.getText())) {
        if (timer != null){
            timer.cancel();
            timer.purge();
        }
            runner = true;
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    if (runner) {
                        // task to run goes here
                        rule.nextGeneration();
                        clearCanvas();
                        draw();
                    } else {
                        timer.cancel();
                        timer.purge();
                    }
                }
            };
            int waitSecounds = 0;
            intervalPeriod = 100;
            timer.scheduleAtFixedRate(task, waitSecounds, intervalPeriod);
            start.setText("Pause");
        } else {
            start.setText("Start");
            runner = false;
        }
    }


    public void clearCanvas() {
        gc.clearRect(0,0, canvas.getWidth(),canvas.getHeight());
        //gc.clearRect(0, 0, x * (cellSize), y * (cellSize));


    }

    public void draw() {

            gc.setFill(Color.ORANGE);
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {

                        if (grid[i][j] == 1) {


                            gc.fillRect(i * (cellSize), j * (cellSize), cellSize, cellSize);
                        }

                }
            }

    }

    @FXML
    public void fileImport(ActionEvent event){
            clearGrid();
            fh.fileGridRead();
            x = fh.counterX;
            y = fh.counterY;
            clearCanvas();
            drawGrid();
            doubleFor(fh.counterX, fh.counterY);
            draw();
    }

    @FXML
    public void openFile(ActionEvent event){
            menuFile.setVisible(true);
            menuFile.setManaged(true);
            System.out.println("Synelig filbehandler");
    }
    @FXML
    public void closeFile(ActionEvent event){
            menuFile.setVisible(false);
            menuFile.setManaged(false);
            System.out.println("Usynelig filbehandler");
    }

    @FXML
    public void openMenu(ActionEvent event) {
            menu.setVisible(true);
            menu.setManaged(true);
            System.out.println("Synelig");

    }

    @FXML
    public void RadioButtons(ActionEvent event){
            if(small.isSelected()) {
                setCellSize(5);
                clearGrid();
                drawGrid();
            }
            else if(normal.isSelected()) {
                setCellSize(10);
                clearGrid();
                drawGrid();
            }
            else if (large.isSelected()) {
                setCellSize(20);
                clearGrid();
                drawGrid();
            }
            else if (large2.isSelected()) {
                setCellSize(30);
                clearGrid();
                drawGrid();
            }
            else if (large3.isSelected()) {
                setCellSize(40);
                clearGrid();
                drawGrid();
            }
            else {
                setCellSize(50);
                clearGrid();
                drawGrid();
            }
        }

    @FXML
    public void Apply(ActionEvent event) {
            menu.setVisible(false);
            menu.setManaged(false);
            System.out.println("Usynelig");

        }

    @FXML
    public void BW() {
        gc.setFill(Color.BLACK);
    }

    @FXML
    public void setScreenSize (String preset) {
        switch (preset) {
            case "Normal":

                break;

            case "Large":

                break;

            case "FullScreen":

                break;
        }
    }


}
