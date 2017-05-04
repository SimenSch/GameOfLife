import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Snorre on 03.05.2017.
 */
public class DynamicBoard {

    public int connector;
    public GameOfLifeController glc =new GameOfLifeController();
    private List<ArrayList<Byte>> dynoBoard = new ArrayList<>();

    public DynamicBoard(int x, int y) {

        for (int i = 0; i < x; i++) {
            ArrayList<Byte> temp = new ArrayList<Byte>();

            for (int j = 0; j < y; j++) {
                temp.add((byte) 0);
            }

            dynoBoard.add(temp);
        }

//        dynoBoard.get(10).get(5) == 1;
//        board[10][5] == 1
//
//
//        dynoBoard.get(40).set(30, (byte) 1 );
//        board[40][30] = 1;
        public void expand() {
            for (int i = 0; i < x; i++) {
                if (dynoBoard.get(i).get(0) == 1) {

                    for (int j = 0; j < x; j++) {
                        dynoBoard.get(x).add(0, (byte) 0);
                    }
                }
                if (dynoBoard.get(i).get(y - 1) == 1) {
                    for (int j = 0; j < x; j++) {
                        dynoBoard.get(x).add((byte) 0);
                    }
                }
            }

        }



    public void setRuleSet(String ruleSet) {
        this.ruleSet = ruleSet;
    }

    String ruleSet;

    public Rules(GameOfLifeController glc) {
        this.glc = glc;
    }

    protected void nextGeneration() {
        byte[][] nextGrid = new byte[glc.x][glc.y];
        for (int i = 1; i < glc.x; i++) {
            for (int j = 1; j < glc.y; j++) {
                if (rules(glc.grid[i][j], countNeightbours(i, j), ruleSet) == 1) {

                    nextGrid[i][j] = 1;
                } else ;
            }
        }
        glc.grid = nextGrid;
    }

    private int rules(int alive, int connector, String ruleSet) {

        switch (ruleSet) {
            case "regular":
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
                break;
            case "special":
                if (alive == 1) {
                    return 1;
                } else {
                    if (connector == 3) {
                        return 1;
                    }
                }
                break;
        }
        return 0;
    }


    protected int countNeightbours(int i, int j) {
        connector = 0;

        if(i==0 && j==0){
            if (glc.grid[i][j + 1] == 1) {
                connector++;
            }

            if (glc.grid[i + 1][j] == 1) {
                connector++;
            }
            if (glc.grid[i + 1][j + 1] == 1) {
                connector++;
            }

        }
        if(i==0 && j>1&&j<glc.y-1){
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

        if (i > 0 && j > 0 && i < glc.x -1 && j < glc.y -1) {

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
    public void DynamicGeneration(){

    }

}
