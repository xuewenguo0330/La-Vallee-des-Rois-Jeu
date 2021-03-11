package iialib.games.model;

import java.util.Set;

public interface IChallenger {
	
	// Attention :
	// Il n'y a pas forcément besoin de constructeurs dans la classe MyChallenger;
	// toutefois si vous décidez d'en écrire, il faudra obligatoirement qu'une version
	// sans argument soit présente :
	// public MyChallenger()
	
	// ---------------------- Fonctions pour le tournoi ---------------------

    /**
     * L'arbitre vous demande le nom de votre équipe.
     * @return le nom de votre équipe sous la forme "Nom1 - Nom2 - Nom3"
     */
    String teamName();

    /**
     * L'arbitre vous signale votre rôle au début de la partie.
     * Vous pouvez préparer votre représentation interne du plateau à ce moment.
     * @param role le rôle qui vous est assigné ("BLACK" ou "WHITE")
     */
    void setRole(String role);

    /**
     * L'arbitre vous signale qu'il valide le dernier coup que vous lui avez communiqué.
     * Vous pouvez jouer ce coup dans votre représentation interne du plateau à ce moment.
     * @param move le coup que vous venez de jouer, sous la forme "B7-B2"
     */
    void iPlay(String move);

    /**
     * L'arbitre vous signale qu'il valide le dernier coup que l'adversaire lui avez communiqué.
     * Vous pouvez jouer ce coup dans votre représentation interne du plateau à ce moment.
     * @param move le coup que l'adversaire viens de jouer, sous la forme "B7-B2"
     */
    void otherPlay(String move);

    // La fonction bestMove n'est à implémenter que pour la partie 3 du devoir.
    // Pour la partie 2, vous pouvez la laisser sous la forme :
    // public String bestMove() {
    //     return null;
    // }
    /**
     * L'arbitre vous demande le coup que vous souhaitez jouer.
     * Choisissez bien.
     * @return le coup que vous comptez jouer (et que vous soumettez à la validation de l'arbitre), sous la forme "B7-B2"
     */
    String bestMove();

    /**
     * L'arbitre vous signale que vous avez gagné.
     * @return un petit message ou une bannière de victoire.
     */
    String victory();

    /**
     * L'arbitre vous signale que vous avez perdu.
     * @return un petit message ou une bannière de défaite.
     */
    String defeat();

    /**
     * L'arbitre vous signale que la partie s'est soldée par une égalité.
     * @return un petit message ou une bannière de partie nulle.
     */
    String tie();
    
    // ---------------------- Fonctions pour les tests ---------------------
    
    /**
     * Vous devez renvoyer une chaîne de caractères décrivant l'état du plateau.
     * @return la chaîne représentant le plateau (voir le sujet pour le format utilisé)
     */
    String getBoard();
    
    /** 
     * Vous devez mettre à jour votre représentation interne selon l'état du plateau décrit dans un fichier texte.
     * @param fileName le nom du fichier à lire (voir le sujet pour le format utilisé)
     */
    void setBoardFromFile(String fileName);
    
    /**
     * Vous devez renvoyer l'ensemble des coups possibles pour vous (en l'état actuel de votre représentation interne).
     * @return l'ensemble de coups possibles pour votre joueur (sous la forme "B7-B2")
     */
    Set<String> possibleMoves();
    
}
