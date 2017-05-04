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
    public void readFile() throws  IOException{
        String fileName = "c://Users//Teritry//Downloads//pulsar.rle";
        nName = "";
        oAuthor = "";
        cComment = "";
        StringBuilder comment = new StringBuilder();
        comment.append(System.getProperty("line.separator"));
        StringBuilder cellplacing = new StringBuilder();


        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach((line) -> {
                if(line.startsWith("#N ")){
                    setnName(line);
                }
                else if (line.startsWith("#O ")){
                    setoAuthor(line);
                }
                else if (line.startsWith("#C ")){
                    comment.append(line);
                    System.out.println(comment);
                }
                else if (line.startsWith("x")){
                    String[] parts = line.split(",");
                    for(String element : parts){
                        if(element.startsWith("x")){
                            setX(Integer.valueOf(element.replaceAll("[^\\d]", ""))) ;
                        } else if(element.contains("y")){
                            setY(Integer.valueOf(element.replaceAll("[^\\d]", ""))) ;
                        } else if (element.contains("B") && element.contains("S")){
                            String[] rules = element.split("/");
                            setB(Integer.valueOf(rules[0].replaceAll("[^\\d]", "")));
                            setS(Integer.valueOf(rules[1].replaceAll("[^\\d]", "")));
                        }

                    }
                }
                else if (line.matches("^[1-9bo].*")){
                    cellplacing.append(line);

                }

            });
            setcellPos(cellplacing.toString());
            drawCells();
        }
    }
    public void drawCells(){
        gamecontroller.cellGrid.drawCell(500, 500, gamecontroller.cellSize);

    }

    public void setnName(String name){
        name.replaceAll("#N ", "");
        nName = name;
        System.out.println("Setting variable to " + name);
    }
    public void setoAuthor(String author){
        author.replaceAll("#O ", "");
        nName = author;
        System.out.println("Setting variable to " + author);
    }
    public void setX(int xcord){
        x = xcord;
        System.out.println("Setting x: " + x);
    }
    public void setY(int ycord){
        y = ycord;
        System.out.println("Setting y: " + y);
    }
    public void setB(int b){
        ruleB = b;
        System.out.println("Setting B: " + b);
    }
    public void setS(int s){
        ruleS = s;
        System.out.println("Setting S: " + s);
    }
    public void setcellPos(String cellpos){
        cellPos = cellpos;
        System.out.println("Setting cellPos: " + cellpos);
    }
}

}












