

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.canvas.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    public Pane menu;
    private Color[] colors = {Color.RED, Color.ALICEBLUE, Color.BISQUE, Color.MAROON, Color.FIREBRICK, Color.BURLYWOOD, Color.SEAGREEN, Color.CORNSILK,};
    private Color blue = Color.BLUEVIOLET;
    public int intervalPeriod;
    public boolean runner;
    public boolean set;
    public Rules rule = new Rules(this);
    public GraphicsContext gc;
    public GraphicsContext gc2;
    protected int x = 1000;
    protected int y = 1000;
    protected boolean run = true;
    protected int cellSize = 3;
    protected int[][] grid = new int[x][y];

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gc = canvas.getGraphicsContext2D();
        gc2 = canvasBack.getGraphicsContext2D();
        menu.setVisible(false);
        menu.setManaged(false);
        drawGrid();
        draw();

        canvasBack.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //gc.setFill(Color.CRIMSON);

                gc.setFill(new Color(Math.random(), Math.random(), Math.random(), 1));
                int x1 = (int) event.getX() / cellSize;
                int y1 = (int) event.getY() / cellSize;
                grid[x1][y1] = 1;

                gc.fillRect(x1 * cellSize, y1 * cellSize, cellSize, cellSize);
            }
        });
        canvasBack.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                gc.setFill(Color.BLACK);
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

    @FXML
    public void start() {
        if ("Start".equals(start.getText())) {

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

                    }
                }
            };


            int delay = 0;
            intervalPeriod = 100;
            timer.scheduleAtFixedRate(task, delay, intervalPeriod);
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

            gc.setFill(Color.BLACK);
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {

                        if (grid[i][j] == 1) {


                            gc.fillRect(i * (cellSize), j * (cellSize), cellSize, cellSize);
                        }

                }
            }

    }

    @FXML
    public void openMenu(ActionEvent event) {
        menu.setVisible(true);
        menu.setManaged(true);
        System.out.println("Synelig");

    }

    @FXML
    public void Apply(ActionEvent event) {
        menu.setVisible(false);
        menu.setManaged(false);
        System.out.println("Usynelig");

    }


}
