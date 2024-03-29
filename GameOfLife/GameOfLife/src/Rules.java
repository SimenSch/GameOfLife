import javafx.scene.paint.Color;

/**
 * Created by Simen on 16.03.2017.
 */

public class Rules {
    /**
     * Levende celle
     */
    public GameOfLifeController glc;
    public int connector;

    public Rules(GameOfLifeController glc) {
        this.glc = glc;
    }

    protected void nextGeneration() {
        if (glc.y >= 0 && glc.x >= 0) {
            int[][] nextGrid = new int[glc.x][glc.y];

            //grid test
            for (int i = 0; i < glc.x; i++) {
                for (int j = 0; j < glc.y; j++) {

                    nextGrid[i][j] = rules(glc.grid[i][j], countNeightbours(i, j));
                }

            }


            glc.grid = nextGrid;
        }
    }

    private int rules(int alive, int connector) {


        if (alive == 1) {

            if (connector <= 1) {
                return 0;
            } else if (connector >= 4) {
                return 0;
            } else {
                return 1;
            }
        } else {

            if (connector == 3) {
                return 1;
            }


        }
        return 0;
    }


    protected int countNeightbours(int i , int j) {
        connector = 0;

        /*for (int k = -1; k <= 1; k++) {
            for (int l = -1; l <= 1; l++) {
                if (!(i+k == -1 && i+k == glc.x && i+l == -1 && i+l == glc.y)){
                    if (k==0 && l==0){
                        continue;
                    }
                    if (glc.grid[i+k][j+l]==1){
                        connector++;
                    }
                }
            }
        }
*/
/*
    if (i - 1 !=-1 && (j-1)!=-1 && (i+1)!=glc.x && (j+1)!=glc.y){

            if (glc.grid[i - 1][j + 1] == 1 && (i - 1) != -1 && (j + 1) != glc.y) {
                connector++;
            }
            if (glc.grid[i + 1][j + 1] == 1 && (i + 1) != glc.x && (j + 1) != glc.y) {
                connector++;
            }
            if (glc.grid[i + 1][j - 1] == 1 && (i + 1) != glc.x && (j - 1) != -1) {
                connector++;
            }
            if (glc.grid[i][j + 1] == 1 && (j + 1) != glc.y && i!=-1 && i!=glc.x) {
                connector++;
            }
            if (glc.grid[i][j - 1] == 1 && (j - 1) != -1 && i!=-1 && i!=glc.x) {
                connector++;
            }
            if (glc.grid[i + 1][j] == 1 && (i + 1) != glc.x && j!=-1 && j!=glc.y) {
                connector++;
            }
            if (glc.grid[i - 1][j] == 1 && (i - 1) != -1 && j!=-1 && j!=glc.y) {
                connector++;
            }
        }
*/




            return connector;

    }
}
