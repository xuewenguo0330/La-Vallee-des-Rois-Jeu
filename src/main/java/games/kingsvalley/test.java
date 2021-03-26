package games.kingsvalley;


import javax.swing.plaf.synth.SynthLookAndFeel;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        String fileName = "Exple_plateau_1.txt";
        File text = new File(fileName);
        Scanner myReader = null;
        try {
            myReader = new Scanner(text);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert myReader != null;

//        KVBoard.PIECE[][] boardGrid = new KVBoard.PIECE[KVBoard.DEFAULT_GRID_SIZE][KVBoard.DEFAULT_GRID_SIZE];
        myReader.nextLine();
        myReader.nextLine();
        int lines = 0;
        while (myReader.hasNextLine() && lines < 8) {
            String line = myReader.nextLine();
            String[] strings = line.split(" ");
            int collone = 0;

            for (String c : strings) {
                collone ++;
                switch (c) {
                    case "o" -> System.out.println("o");
                    case "x" -> System.out.println("x");
                    case "X" -> System.out.println("X");
                    case "O" -> System.out.println("O");
                    case "-" -> {}
                    default -> collone--;
                }
            }
            System.out.println();
            lines++;
        }


    }
}
