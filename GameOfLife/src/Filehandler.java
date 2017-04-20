/**
 * Created by Simen on 21.03.2017.
 */



    import java.io.*;
import java.util.HashMap;


    /**
     * @author 4 contributor 3(javadoc by 3)
     */
    public class Filehandler {
        int counterX;
        int counterY;

        public Filehandler() {
        }

        public GameOfLifeController glc;
        public int[][] gridfile = new int[glc.x][glc.y];
    public char[] charGrid;
        public void doubleFor(int x, int y) {
            for (int i = 0; i < y; i++) {
                for (int j = 0; j < x; j++) {
                    if (charGrid[j]=='.'){

                        glc.grid[i][j]=0;
                    }
                    else if(charGrid[j]=='O'){
                        glc.grid[i][j]=1;
                    }
                }
            }
        }

        public void fileGridRead() throws IOException {

            BufferedReader read = new BufferedReader(new FileReader("plaintext.txt"));
            String line = read.readLine();
            String name = "";


            //counter for x og y
            while (line != null) {
                if (line.matches("!Name") || line.matches("!")) {

                } else if (line.matches(".") || line.matches("O")) {
                    int i = line.indexOf(".");
                    int k = line.indexOf("O");
                    charGrid = line.toCharArray();

                    counterX = i + k;
                    counterY++;
                }

            }

        }


    }





