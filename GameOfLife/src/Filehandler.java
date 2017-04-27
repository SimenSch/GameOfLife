/**
 * Created by Simen on 21.03.2017.
 */


import java.io.*;


public class Filehandler {


    public Rules rule;
    public GameOfLifeController glc;

    public byte[][] parseFile(FileReader file) throws IOException {
        glc = new GameOfLifeController();
        char[] charArray;
        int offsetX = 20;
        int offsetY = 20;
        byte[][] arrayFromFile = new byte[glc.x][glc.y];
        int lineNumber = 0;
        try (BufferedReader reader = new BufferedReader(file)) {
            String line = reader.readLine();
            while (line != null) {
                charArray = line.toCharArray();
                if (charArray[0] == '#') {
                } else {
                    for (int i = 0; i < charArray.length; i++) {
                        if (charArray[i] == 'O') {
                            arrayFromFile[lineNumber + offsetY][i + offsetX] = 1;
                        } else if (charArray[i] == '.') {
                            arrayFromFile[lineNumber + offsetY][i + offsetX] = 0;
                        }
                    }
                    lineNumber++;
                    line = reader.readLine();
                }
            }
        } catch (IOException e) {
            System.out.println("fil kunne ikke leses.");
        }
        System.out.println("correctly recieved array from file!");
        for (int i = 0; i < glc.x; i++) {
            for (int j = 0; j < glc.y; j++) {
                if (arrayFromFile[i][j] == 1) {
                    glc.grid[i][j] = 1;
                } else if (arrayFromFile[i][j] == 0) {
                    glc.grid[i][j] = 0;
                }

            }
        }
        return arrayFromFile;

    }


}








