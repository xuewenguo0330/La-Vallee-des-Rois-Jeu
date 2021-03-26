package games.kingsvalley;

import java.util.Set;

public class test {
    public static void main(String[] args) {
        String fileName = "Exple_plateau_1.txt";
        MyChallenger myChallenger = new MyChallenger();
        myChallenger.setBoardFromFile(fileName);
        System.out.println(myChallenger.getBoard());
        myChallenger.setRole("BLUE");

        Set<String> set = myChallenger.possibleMoves();
        for (String s : set) {
            System.out.println(s);
        }

        myChallenger.iPlay("A7-B7");
        System.out.println(myChallenger.getBoard());
    }
}
