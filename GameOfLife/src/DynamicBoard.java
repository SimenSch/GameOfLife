import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Snorre on 03.05.2017.
 */
public class DynamicBoard {

    public int connector;
    public GameOfLifeController glc =new GameOfLifeController();

    public ArrayList<ArrayList<Integer>> dynoBoard = new ArrayList<>();


    public DynamicBoard(int x, int y) {

        for (int i = 0; i < x; i++) {
            ArrayList<Integer> temp = new ArrayList<>();

            for (int j = 0; j < y; j++) {
                temp.add(0);
            }

            dynoBoard.add(temp);
        }
    }




        public void expand(int x, int y) {
            for (int i = 0; i < x; i++) {
                if (dynoBoard.get(i).get(0) == 1) {

                    for (int j = 0; j < x; j++) {
                        dynoBoard.get(x).add(0, 0);
                    }
                }
                if (dynoBoard.get(i).get(y - 1) == 1) {
                    for (int j = 0; j < x; j++) {
                        dynoBoard.get(x).add(0);
                    }
                }
            }

        }



    public void setRuleSet(String ruleSet) {
        this.ruleSet = ruleSet;
    }

    String ruleSet;



    protected void nextDynoGeneration(int x, int y) {
         ArrayList<ArrayList<Integer>> nextDynoBoard = new ArrayList<>();
        for (int i = 0; i < x; i++) {
            ArrayList<Integer> temp = new ArrayList<>();

            for (int j = 0; j < y; j++) {
                temp.add(0);
            }

            nextDynoBoard.add(temp);
        }
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (rules(dynoBoard.get(i).get(j) , countNeightbours(i, j), ruleSet) == 1) {

                    nextDynoBoard.get(i).set(j, 1);

                }

            }

        }
        dynoBoard = nextDynoBoard;
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

     /*   if(i==0 && j==0){
            if (dynoBoard.get(i).get(j+1) == 1) {
                connector++;
            }

            if (dynoBoard.get(i + 1).get(j) == 1) {
                connector++;
            }
            if (dynoBoard.get(i + 1).get(j+ 1) == 1) {
                connector++;
            }

        }
        if(i==0 && j>1&&j<glc.y-1){
            if (dynoBoard.get(i).get(j +1) == 1) {
                connector++;
            }
            if (dynoBoard.get(i).get(j -1) == 1) {
                connector++;
            }
            if (dynoBoard.get(i + 1).get(j) == 1) {
                connector++;
            }
            if (dynoBoard.get(i + 1).get(j + 1) == 1) {
                connector++;
            }
            if (dynoBoard.get(i + 1).get(j - 1) == 1) {
                connector++;
            }
        }
*/
        if (i > 1 && j > 1 && i < glc.x -1 && j < glc.y -1) {

            if (dynoBoard.get(i - 1).get(j) == 1) {
                connector++;
            }
            if (dynoBoard.get(i - 1).get(j + 1) == 1) {
                connector++;
            }
            if (dynoBoard.get(i - 1).get(j - 1) == 1) {
                connector++;
            }
            if (dynoBoard.get(i).get(j +1) == 1) {
                connector++;
            }
            if (dynoBoard.get(i).get(j-1) == 1) {
                connector++;
            }
            if (dynoBoard.get(i + 1).get(j) == 1) {
                connector++;
            }
            if (dynoBoard.get(i + 1).get(j + 1) == 1) {
                connector++;
            }
            if (dynoBoard.get(i + 1).get(j - 1) == 1) {
                connector++;
            }
        }
        return connector;

    }
    public void DynamicGeneration(){

    }
}
