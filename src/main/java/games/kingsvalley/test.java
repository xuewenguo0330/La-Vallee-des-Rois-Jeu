package games.kingsvalley;

import games.dominos.DominosBoard;
import games.dominos.DominosHeuristics;
import games.dominos.DominosMove;
import games.dominos.DominosRole;
import iialib.games.algs.GameAlgorithm;
import iialib.games.algs.algorithms.MiniMax;

import java.util.Set;

public class test {
    public static void main(String[] args) {
        String fileName = "Exple_plateau_1.txt";
        MyChallenger myChallenger = new MyChallenger();
        myChallenger.teamName();
        myChallenger.setBoardFromFile(fileName);
        System.out.println(myChallenger.getBoard());
        myChallenger.setRole("BLUE");

        Set<String> set = myChallenger.possibleMoves();
        for (String s : set) {
            System.out.println(s);
        }

        myChallenger.iPlay("A7-B7");
        System.out.println(myChallenger.getBoard());

        myChallenger.iPlay("F4-C5");
        System.out.println(myChallenger.getBoard());

        myChallenger.iPlay("F4-C4");
        System.out.println(myChallenger.getBoard());
    }
}
