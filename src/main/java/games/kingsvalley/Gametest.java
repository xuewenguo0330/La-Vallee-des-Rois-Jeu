package games.kingsvalley;


import java.util.ArrayList;
import java.util.Set;

import iialib.games.algs.AIPlayer;
import iialib.games.algs.AbstractGame;
import iialib.games.algs.GameAlgorithm;
import iialib.games.algs.algorithms.MiniMax;
import iialib.games.algs.algorithms.AlphaBeta;
import iialib.games.algs.algorithms.MiniMax;

public class Gametest extends AbstractGame<KVMove, KVRole, KVBoard> {

        Gametest(ArrayList<AIPlayer<KVMove, KVRole, KVBoard>> players, KVBoard board) {
        super(players, board);
        }

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


        KVRole bleu = KVRole.BLUE;
        KVRole white = KVRole.WHITE;

//            GameAlgorithm<KVMove, KVRole, KVBoard> algBleu = new MiniMax<KVMove, KVRole, KVBoard>(
//                    bleu, white, KVHeuristique.heuristicBlue, 4); // Minimax depth 4
//
//            GameAlgorithm<KVMove, KVRole, KVBoard> algWhite = new MiniMax<KVMove, KVRole, KVBoard>(
//                    bleu, white, KVHeuristique.heuristicWhite, 2); // Minimax depth 2
//
        GameAlgorithm<KVMove, KVRole, KVBoard> algBleu = new AlphaBeta<KVMove, KVRole, KVBoard>(
                    bleu, white, KVHeuristique.heuristicBlue, 4); // alphabeta depth 4

            GameAlgorithm<KVMove, KVRole, KVBoard> algWhite = new AlphaBeta<KVMove, KVRole, KVBoard>(
                    bleu, white, KVHeuristique.heuristicWhite, 2); // alphabeta depth 2

            AIPlayer<KVMove, KVRole, KVBoard> playerBleu = new AIPlayer<KVMove, KVRole, KVBoard>(
                    bleu, algBleu);

            AIPlayer<KVMove, KVRole, KVBoard> playerWhite = new AIPlayer<KVMove, KVRole, KVBoard>(
            white, algWhite);

            ArrayList<AIPlayer<KVMove, KVRole, KVBoard>> players = new ArrayList<AIPlayer<KVMove, KVRole, KVBoard>>();

            players.add(playerBleu); // First Player
            players.add(playerWhite); // Second Player

            // Setting the initial Board
            KVBoard initialBoard = new KVBoard();

            Gametest game = new Gametest(players, initialBoard);
            game.runGame();
        }
    }