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

    /**
     *
     * @param x Construktør for brett dynamisk eller ei.
     * @param y Construktør for brett dynamisk eller ei
     */
    public DynamicBoard(int x, int y) {

        for (int i = 0; i < x; i++) {
            ArrayList<Integer> temp = new ArrayList<>();
            for (int j = 0; j < y; j++) {
                temp.add(0);
            }
            dynoBoard.add(temp);
        }
    }

    /**
     *  Gjør slik at brettet øker i trinn med bildet til bruker.
     * @param x henter Canvas sin størrelse
     * @param y henter canvas sin størrelse
     * @throws NullPointerException
     */
        public void fullscreenExpand(int x,int y) throws NullPointerException {


            for (int i = 0; i < x; i++) {

                ArrayList<Integer> temp = new ArrayList<>();
                for (int j = 0; j < y; j++) {

                    temp.add(0);

                    dynoBoard.get(dynoBoard.size() - 1).add(j, 0);
                }
                dynoBoard.get(i).add(0);
                dynoBoard.add(temp);
            }
            for (int i = 0; i < x; i++) {

                ArrayList<Integer> temp = new ArrayList<>();
                for (int j = 0; j < y; j++) {
                    temp.add(0);
                    dynoBoard.get(dynoBoard.size() - 1).add(j, 0);
                }
            }
            for (int i = 0; i < y; i++) {

                    ArrayList<Integer> newRow = new ArrayList<>();


                    for (int j = 0; j < y; j++) {

                        newRow.add(0);
                    }
                    dynoBoard.add(newRow);


                }

        }


    /**
     * Dette velger om vi skal ha conways eller life without death rulesett, se Rules
     * @param ruleSet
     */
    public void setRuleSet(String ruleSet) {
        this.ruleSet = ruleSet;
    }

    String ruleSet;


    /**
     * Neste generasjon med dynamisk brett
     * @throws IndexOutOfBoundsException
     */
    protected void nextDynoGeneration() throws IndexOutOfBoundsException{
         int x = dynoBoard.size();
         int y = dynoBoard.get(0).size();
        ArrayList<ArrayList<Integer>> nextDynoBoard = new ArrayList<>();
        for (int i = 0; i < x; i++) {
            nextDynoBoard.add(new ArrayList(x));
            for (int j = 0; j < y; j++) {
                // add cell even if dead
                 int val = rules(dynoBoard.get(i).get(j) , countNeightbours(i, j), ruleSet);
                   nextDynoBoard.get(i).add(val);
                }
            }
             dynoBoard = nextDynoBoard;
    }


    /**
     *
     * @param alive setter nabo til i live
     * @param connector tester nabo
     * @param ruleSet hvilke regelsett vi skal bruke conways eller life without death
     * @return
     * @throws IndexOutOfBoundsException
     */
    private int rules(int alive, int connector, String ruleSet) throws IndexOutOfBoundsException{

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

    /**
     * teller naboer
     * @param i forløkke 1
     * @param j forløkke 2
     * @return
     * int Connector
     * @throws IndexOutOfBoundsException
     */
    protected int countNeightbours(int i, int j) throws IndexOutOfBoundsException{
        connector = 0;

        if (i > 1 && j > 1 && i < dynoBoard.size() -1 && j < dynoBoard.get(0).size() -1) {

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

}
