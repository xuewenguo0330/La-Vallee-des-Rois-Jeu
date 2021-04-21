package games.kingsvalley;

import iialib.games.algs.IHeuristic;

public class KVHeuristique {

    //  Nombre d’occupation sur les 8 cases qui encerclent la case centre de ce joueur.
    public static IHeuristic<KVBoard, KVRole> heuristicBlue = (board, role) -> {

        return board.getNbAutourSoleil();
    };

    public static IHeuristic<KVBoard, KVRole> heuristicWhite = (board, role) -> {
        return board.getNbAutourSoleil();
    };


/*
    // Nombre de coups possibles pour le roi de ce joueur.
    public static IHeuristic<KVBoard, KVRole> heuristicBlue = (board, role) -> {
        return board.getNbRoiPossibleMove(role);
    };

    public static IHeuristic<KVBoard, KVRole> heuristicWhite = (board, role) -> {
        return board.getNbRoiPossibleMove(role);
    };
 */
/*
    //Moins de nombre de coups possibles adversaires.
    public static IHeuristic<KVBoard, KVRole> heuristicBlue = (board, role) -> {
        return board.possibleMoves(KVRole.WHITE).size();
    };

    public static IHeuristic<KVBoard, KVRole> heuristicWhite = (board, role) -> {
        return board.possibleMoves(KVRole.BLUE).size();
    };

 */
/*
    //Distance du roi à la case centrale
    public static IHeuristic<KVBoard, KVRole> heuristicBlue = (board, role) -> {
        return (int)-board.distanceRoiCentre(role);
    };

    public static IHeuristic<KVBoard, KVRole> heuristicWhite = (board, role) -> {
        return (int)-board.distanceRoiCentre(role);
    };

 */


}
