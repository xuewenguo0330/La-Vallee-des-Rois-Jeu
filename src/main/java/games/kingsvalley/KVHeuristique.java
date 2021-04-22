package games.kingsvalley;

import iialib.games.algs.IHeuristic;

public class KVHeuristique {

    //  Nombre d’occupation sur les 8 cases qui encerclent la case centre de ce joueur.
//    public static IHeuristic<KVBoard, KVRole> heuristicBlue = (board, role) -> board.getNbAutourSoleil(KVRole.BLUE);
//    public static IHeuristic<KVBoard, KVRole> heuristicWhite = (board, role) -> board.getNbAutourSoleil(KVRole.WHITE);


    // Nombre de coups possibles pour le roi de ce joueur.
//    public static IHeuristic<KVBoard, KVRole> heuristicBlue = KVBoard::getNbRoiPossibleMove;
//    public static IHeuristic<KVBoard, KVRole> heuristicWhite = KVBoard::getNbRoiPossibleMove;

    //Moins de nombre de coups possibles adversaires.
//    public static IHeuristic<KVBoard, KVRole> heuristicBlue = (board, role) ->
//            -board.possibleMoves(KVRole.WHITE).size() + board.nbEmptyAutourRoi(KVRole.BLUE) * 2;
//    public static IHeuristic<KVBoard, KVRole> heuristicWhite = (board, role) ->
//            -board.possibleMoves(KVRole.BLUE).size() + board.nbEmptyAutourRoi(KVRole.WHITE) * 2;

    //Distance du roi à la case centrale
    public static IHeuristic<KVBoard, KVRole> heuristicBlue = (board, role) -> {
        if (board.roiBlock(KVRole.WHITE) || board.centreRoi(KVRole.BLUE)) {
            return Integer.MAX_VALUE;
        } else {
            return (int)-board.distanceRoiCentre(role);
        }
    };

    public static IHeuristic<KVBoard, KVRole> heuristicWhite = (board, role) -> {
        if (board.roiBlock(KVRole.BLUE) || board.centreRoi(KVRole.WHITE)) {
            return Integer.MAX_VALUE;
        } else {
            return (int)-board.distanceRoiCentre(role);
        }
    };
}
