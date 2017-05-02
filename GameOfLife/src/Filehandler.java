/**
 * Created by Simen on 21.03.2017.
 */


import java.io.*;


public class Filehandler {


    public Rules rule;
    public GameOfLifeController glc;
    private String Filename, Filetype, FilePath, FilePattern;


    public byte[][] parseFile(FileReader file) throws IOException {
        glc = new GameOfLifeController();
        System.out.println(glc.x+","+glc.y);
        char[] charArray;
        int offsetX = 10;
        int offsetY = 10;
        byte[][] arrayFromFile = new byte[100][80];
        int lineNumber = 0;
        try (BufferedReader reader = new BufferedReader(file)) {
            String line = reader.readLine();
            while (line != null) {
                charArray = line.toCharArray();
                if (charArray[0] == '#') {
                } else {
                    for (int i = 0; i < charArray.length; i++) {
                        if (charArray[i] == 'O' || charArray[i] == 'o' || charArray[i] == '0') {
                                arrayFromFile[i + offsetX][lineNumber + offsetY] = 1;
                                System.out.println("O with out of bounds catching");
                        } else if (charArray[i] == '.') {
                        }
                    }
                    lineNumber++;

                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            System.out.println("fil kunne ikke leses.");
        }
        System.out.println("correctly recieved array from file!");
        return arrayFromFile;

    }

    public void saveAFile(byte[][] saveFile) throws IOException {
        FilePath = "GameOfLife/src/files/";
        Filename = "test";
        Filetype = ".txt";
        FilePattern = "";

        String content = "!" + Filename;
        System.out.println(content);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FilePath+Filename+Filetype))) {
            bw.write(content);
            bw.newLine();
            for (int i = 0; i < saveFile[1].length; i++) {
                FilePattern ="";
                for (int j = 0; j < saveFile.length; j++) {
                    if (saveFile[j][i] == 1) {
                        FilePattern += "0";
                    } else {
                        FilePattern += ".";
                    }
                }
                bw.write(FilePattern);
                bw.newLine();
            }
        }
        System.out.println("Done");
        System.out.println(FilePattern);
    }

}












