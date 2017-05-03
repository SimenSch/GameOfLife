/**
 * Created by Simen on 21.03.2017.
 */


import java.io.*;


public class Filehandler {


    public Rules rule;
    public GameOfLifeController glc;
    private String Filename, Filetype, FilePath, FilePattern;


    public byte[][] goThroughFile(FileReader file, byte[][] FileArray) throws IOException {
        glc = new GameOfLifeController();
        System.out.println(glc.x + "," + glc.y);
        char[] charreadLine;
        int plussX = 10;
        int plussY = 10;
        int Yplace = 0;
        try (BufferedReader reader = new BufferedReader(file)) {
            String readLine = reader.readLine();
            while (readLine != null) {
                charreadLine = readLine.toCharArray();
                if (charreadLine[0] == '#') {
                } else {
                    for (int i = 0; i < charreadLine.length; i++) {
                        if (charreadLine[i] == 'O') {
                            if (i + plussX < FileArray.length && Yplace + plussY < FileArray[i].length) {
                                FileArray[i + plussX][Yplace + plussY] = 1;
                            }
                        } else if (charreadLine[i] == '.') {
                        }
                    }
                    Yplace++;
                }
                readLine = reader.readLine();
            }
        } catch (IOException e) {
            System.out.println("fil kunne ikke leses.");
        }
        System.out.println("Velykket");
        return FileArray;

    }

    public void saveAFile(byte[][] saveFile) throws IOException {
        FilePath = "GameOfLife/src/files/";
        Filename = "test";
        Filetype = ".txt";
        FilePattern = "";
        int filesInFolder = new File("GameOfLife/src/files/").list().length;
        String content = "!" + Filename;
        System.out.println(content);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FilePath + Filename + Filetype))) {
            bw.write(content);
            bw.newLine();
            for (int i = 0; i < saveFile[1].length; i++) {
                FilePattern = "";
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












