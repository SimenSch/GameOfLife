/**
 * Created by Simen on 21.03.2017.
 */


import java.io.*;
import java.util.ArrayList;


public class Filehandler {


    public Rules rule;
    public GameOfLifeController glc;
    private String Filename, Filetype, FilePath, FilePattern;


    public ArrayList<ArrayList<Integer>> goThroughFile(FileReader file, ArrayList<ArrayList<Integer>> FileArray) throws IOException {
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

    public int[][] readRleFile(FileReader file, int[][] FileArray) throws IOException {
        glc = new GameOfLifeController();
        char[] charreadLine;
        char[] numbers = new char[3];
        int numberOfNumbers = 0;
        int numberToUse;
        int plussX = 10;
        int plussY = 10;
        int Yplace = 0;
        try (BufferedReader reader = new BufferedReader(file)) {
            String readLine = reader.readLine();
            charreadLine = readLine.toCharArray();
            while (readLine != null) {
                if (readLine.startsWith("#N")) {
                } else if (readLine.startsWith("#O")) {
                } else if (readLine.startsWith("#C")) {
                } else if (readLine.startsWith("x")) {
                } else {
                    if (readLine.startsWith("^[1-9bo].*")) {
                        for (int i = 0; i < charreadLine.length; i++) {
                            if (charreadLine[i] == 'o') {
                                numberToUse = Integer.parseInt(String.valueOf(numbers));
                                if (numberToUse > 0) {
                                    while (numberToUse < 0) {
                                        if (i + plussX + numberToUse< FileArray.length && Yplace + plussY < FileArray[i].length) {
                                            FileArray[i + plussX + numberToUse][Yplace + plussY] = 1;
                                        }
                                        numberToUse--;
                                    }
                                }
                                else{
                                    if (i + plussX < FileArray.length && Yplace + plussY < FileArray[i].length) {
                                        FileArray[i + plussX ][Yplace + plussY] = 1;
                                    }
                                }
                            } else if (charreadLine[i] == 'b') {
                                numberToUse = Integer.parseInt(String.valueOf(numbers));
                                if (numberToUse > 0) {
                                    while (numberToUse < 0) {
                                        if (i + plussX + numberToUse < FileArray.length && Yplace + plussY < FileArray[i].length) {
                                            FileArray[i + plussX + numberToUse][Yplace + plussY] = 0;
                                        }
                                        numberToUse--;
                                    }
                                }
                                else{
                                    if (i + plussX < FileArray.length && Yplace + plussY < FileArray[i].length) {
                                        FileArray[i + plussX][Yplace + plussY] = 0;
                                    }
                                }
                            } else if (charreadLine[i] == ('$')) {
                                Yplace++;
                            } else {
                                numbers[numberOfNumbers] = charreadLine[i];
                                numberOfNumbers++;
                            }
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


    public void saveAFile(int[][] saveFile) throws IOException {
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












