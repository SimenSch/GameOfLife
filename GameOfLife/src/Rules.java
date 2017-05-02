
import javafx.scene.paint.Color;

import java.util.Arrays;

/**
 * Created by Simen & Snorre on 16.03.2017.
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
        byte[][] nextGrid = new byte[glc.x][glc.y];
        for (int i = 1; i < glc.x; i++) {
            for (int j = 1; j < glc.y; j++) {
                if (rules(glc.grid[i][j], countNeightbours(i, j)) == 1) {

                    nextGrid[i][j] = 1;
                }
                else;
            }
        }
        glc.grid = nextGrid;
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


    protected int countNeightbours(int i, int j) {
        connector = 0;
        if (i > 1 && j > 1 && i < glc.x - 1 && j < glc.y - 1) {

            if (glc.grid[i - 1][j] == 1) {
                connector++;
            }
            if (glc.grid[i - 1][j + 1] == 1) {
                connector++;
            }
            if (glc.grid[i - 1][j - 1] == 1) {
                connector++;
            }
            if (glc.grid[i][j + 1] == 1) {
                connector++;
            }
            if (glc.grid[i][j - 1] == 1) {
                connector++;
            }
            if (glc.grid[i + 1][j] == 1) {
                connector++;
            }
            if (glc.grid[i + 1][j + 1] == 1) {
                connector++;
            }
            if (glc.grid[i + 1][j - 1] == 1) {
                connector++;
            }
        }
        return connector;
    }
}


//alt i midten
        /*
        if (i < glc.x - 1 && i > 0 && j < glc.y - 1 && j > 0) {
            for (int k = -1; k <= 1; k++) {
                for (int l = -1; l <= 1; l++) {

                    if (glc.grid[i + k][j + l] == 1) {
                        connector++;
                    }
                }
            }
        }

        //øver langside(ikke hjørnene)done
        else if (i < glc.x && i > 0 && j == 0) {
            for (int k = -1; k <= 0; k++) {
                for (int l = 0; l <= 1; l++) {
                    if (glc.grid[i + k][j + l] == 1) {
                        connector++;
                    }

                }
            }
        }
        //venstre langside(ikke hjørnene)done
        else if (i == 0 && j < glc.y - 1 && j > 0) {
            for (int k = 0; k <= 1; k++) {
                for (int l = -1; l <= 1; l++) {

                    if (glc.grid[i + k][j + l] == 1) {
                        connector++;
                    }

                }
            }
        }

        //høyre langside(ikke hjørnene)done
        else if (i == glc.x - 1 && j > 0 && j < glc.y - 1) {
            for (int k = -1; k <= 0; k++) {
                for (int l = -1; l <= 1; l++) {

                    if (glc.grid[i + k][j + l] == 1) {
                        connector++;
                    }

                }
            }
        }
        //nedre langside(ikke hjørnene)
        else if (i > 0 && i < glc.x - 1 && j > 0 && j == glc.y -1) {
            for (int k = -1; k <= 1; k++) {
                for (int l = -1; l <= 0; l++) {

                    if (glc.grid[i + k][j + l] == 1) {
                        connector++;
                    }
                }
            }
        }
        //venstre øvre hjørne
        else if (i == 0 && j == 0) {
            for (int k = 0; k <= 1; k++) {
                for (int l = 0; l <= 1; l++) {
                    if (glc.grid[i + k][j + l] == 1) {
                        connector++;

                    }

                }
            }
        }
        //høyre øvre hjørne
        else if (i == glc.x - 1 && j == 0) {
            for (int k = -1; k <= 0; k++) {
                for (int l = -1; l <= 0; l++) {

                    if (glc.grid[i + k][j + l] == 1) {
                        connector++;
                    }
                }
            }
        }

        //venstre nedre hjørne
        else if (i == 0 && j == glc.y -1) {
            for (int k = 0; k <= 1; k++) {
                for (int l = -1; l <= 0; l++) {
                    // if (!(i + k == -1 && i + k == glc.x && i + l == -1 && i + l == glc.y)) {
                    if (glc.grid[i + k][j + l] == 1) {
                        connector++;
                    }
                }
            }
        }

        //høyre nedre hjørne
        else if (i==glc.x) {
            for (int k = -1; k <= 0; k++) {
                for (int l = -1; l <= 0; l++) {
                    if (glc.grid[i + k][j + l] == 1) {
                        connector++;
                    }
                }
            }*/
