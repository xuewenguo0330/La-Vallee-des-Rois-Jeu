package iialib.games.algs.algorithms;

import iialib.games.algs.GameAlgorithm;
import iialib.games.algs.IHeuristic;
import iialib.games.model.IBoard;
import iialib.games.model.IMove;
import iialib.games.model.IRole;

import java.util.ArrayList;

public class AlphaBeta<Move extends IMove, Role extends IRole, Board extends IBoard<Move, Role, Board>> implements GameAlgorithm<Move, Role, Board> {
    private final static int DEPTH_MAX_DEFAUT = 4;
    private final Role playerMaxRole;
    private final Role playerMinRole;
    private final IHeuristic<Board, Role> h;
    private int depthMax = DEPTH_MAX_DEFAUT;
    private int nbNodes;
    private int nbLeaves;


    public AlphaBeta(Role playerMaxRole, Role playerMinRole, IHeuristic<Board, Role> h) {
        this.playerMaxRole = playerMaxRole;
        this.playerMinRole = playerMinRole;
        this.h = h;
        this.nbNodes = 0;
        this.nbLeaves = 0;
    }

    public AlphaBeta(Role playerMaxRole, Role playerMinRole, IHeuristic<Board, Role> h, int depthMax) {
        this(playerMaxRole, playerMinRole, h);
        this.depthMax = depthMax;
    }

    private int alphabeta(Board board, int depth, int alpha, int beta, Role player) {
        // System.out.println("Depth: " + depth + ", alpha: " + alpha + ", beta: " + beta);
        ArrayList<Move> moves = board.possibleMoves(player);
        if (depth == depthMax || moves.isEmpty()) {
            this.nbLeaves++;
            return h.eval(board, player);
        }

        if (player == playerMaxRole) {
            int value = IHeuristic.MIN_VALUE;
            for (Move move : moves) {
                this.nbNodes++;
                value = Math.max(value, alphabeta(board.play(move, playerMaxRole), depth + 1, alpha, beta, playerMinRole));
                alpha = Math.max(alpha, value);
                if (alpha >= beta) return beta;
            }
            return alpha;
        } else {
            int value = IHeuristic.MAX_VALUE;
            for (Move move : moves) {
                this.nbNodes++;
                value = Math.min(value, alphabeta(board.play(move, playerMinRole), depth + 1, alpha, beta, playerMaxRole));
                beta = Math.min(beta, value);
                if (alpha >= beta) return alpha;
            }
            return beta;
        }
    }

    @Override
    public Move bestMove(Board board, Role playerRole) {
        System.out.println("[AlphaBeta]");

        int alpha = IHeuristic.MIN_VALUE;
        ArrayList<Move> moves = board.possibleMoves(playerRole);
        Move bestMove = moves.get(0);

        for (Move move : moves) {
            Board played = board.play(move, playerRole);
            int value = alphabeta(played, 1, alpha, IHeuristic.MAX_VALUE, playerMinRole);
            if (value > alpha) {
                alpha = value;
                // System.out.println("CHANGED");
                bestMove = move;
            }
        }
        System.out.println("Le nombre de feuille est " + this.nbLeaves);
        System.out.println("Le nombre de noeud est " + this.nbNodes);

        return bestMove;
    }
}
