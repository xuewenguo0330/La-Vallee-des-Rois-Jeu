package games.kingsvalley;

import iialib.games.algs.IHeuristic;

public class KVHeuristique {

    //  Nombre d’occupation sur les 8 cases qui encerclent la case centre de ce joueur.
//    public static IHeuristic<KVBoard, KVRole> heuristicBlue = (board, role) -> board.getNbAutourSoleil(KVRole.BLUE);
//    public static IHeuristic<KVBoard, KVRole> heuristicWhite = (board, role) -> board.getNbAutourSoleil(KVRole.WHITE);


    // Nombre de coups possibles pour le roi de ce joueur.
//    public static IHeuristic<KVBoard, KVRole> heuristicBlue = (board, role) -> - board.getNbRoiPossibleMove(KVRole.WHITE);
//    public static IHeuristic<KVBoard, KVRole> heuristicWhite = (board, role) -> - board.getNbRoiPossibleMove(KVRole.BLUE);

    //Moins de nombre de coups possibles adversaires.
//    public static IHeuristic<KVBoard, KVRole> heuristicBlue = (board, role) ->
//            -board.possibleMoves(KVRole.WHITE).size();
//    public static IHeuristic<KVBoard, KVRole> heuristicWhite = (board, role) ->
//            -board.possibleMoves(KVRole.BLUE).size();

    //Distance du roi à la case centrale
//    public static IHeuristic<KVBoard, KVRole> heuristicBlue = (board, role) -> {
//        if (board.centreRoi(KVRole.BLUE)) {
//            return 9999;
//        } else if (board.roiBlock(KVRole.WHITE)) {
//            return 8888;
//        } else if (board.roiBlock(KVRole.BLUE) || board.centreRoi(KVRole.WHITE)) {
//            return -9999;
//        }  else {
//            return (int)-board.distanceRoiCentre(KVRole.BLUE) - board.nbEmptyAutourRoi(KVRole.WHITE) * 3;
//        }
//    };
//
//    public static IHeuristic<KVBoard, KVRole> heuristicWhite = (board, role) -> {
//        if (board.roiBlock(KVRole.WHITE) || board.centreRoi(KVRole.BLUE)) {
//            return -9999;
//        } else if (board.centreRoi(KVRole.WHITE)) {
//            return 9999;
//        } else if (board.roiBlock(KVRole.BLUE)) {
//            return 8888;
//        } else {
//            return (int)-board.distanceRoiCentre(KVRole.WHITE) - board.nbEmptyAutourRoi(KVRole.BLUE) * 3;
//        }
//    };
}
