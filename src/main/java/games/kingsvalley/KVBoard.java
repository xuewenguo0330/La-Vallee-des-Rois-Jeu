package games.kingsvalley;

import iialib.games.algs.GameAlgorithm;
import iialib.games.algs.algorithms.AlphaBeta;
import iialib.games.algs.algorithms.MiniMax;
import iialib.games.model.IBoard;
import iialib.games.model.Score;

import java.awt.*;
import java.util.ArrayList;

public class KVBoard implements IBoard<KVMove, KVRole, KVBoard> {
	enum PIECE {EMPTY, SOLDATBLEU, SOLDATWHITE, ROIBLEU, ROIWHITE}

	public static final int DEFAULT_GRID_SIZE = 7;
	private final PIECE[][] boardGrid;
	private Point roiBleu;
	private Point roiWhite;
	private final GameAlgorithm<KVMove, KVRole, KVBoard> algoBlue = new AlphaBeta<>(KVRole.BLUE, KVRole.WHITE,
			KVHeuristique.heuristicBlue, 3);
	private final GameAlgorithm<KVMove, KVRole, KVBoard> algoWhite = new AlphaBeta<>(KVRole.WHITE, KVRole.BLUE,
			KVHeuristique.heuristicWhite, 3);

	public KVBoard() {
		boardGrid = new PIECE[DEFAULT_GRID_SIZE][DEFAULT_GRID_SIZE];
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				boardGrid[i][j] = PIECE.EMPTY;
			}
		}
		for (int i = 0; i < 7; i++) {
			boardGrid[6][i] = PIECE.SOLDATBLEU;
			boardGrid[0][i] = PIECE.SOLDATWHITE;
		}
		boardGrid[6][3] = PIECE.ROIWHITE;
		boardGrid[0][3] = PIECE.ROIBLEU;

		roiBleu = new Point(0, 3);
		roiWhite = new Point(6, 3);
	}

	public KVBoard(PIECE[][] board) {
		boardGrid = new PIECE[DEFAULT_GRID_SIZE][DEFAULT_GRID_SIZE];
		for (int i = 0; i < DEFAULT_GRID_SIZE; i++)
			System.arraycopy(board[i], 0, boardGrid[i], 0, DEFAULT_GRID_SIZE);

		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				switch (board[i][j]) {
					case ROIBLEU -> roiBleu = new Point(i, j);
					case ROIWHITE -> roiWhite = new Point(i, j);
				}
			}
		}
	}

	public KVMove bestMove(KVRole role) {
		return switch (role) {
			case BLUE -> algoBlue.bestMove(this, KVRole.BLUE);
			case WHITE -> algoWhite.bestMove(this, KVRole.WHITE);
		};
	}

	@Override
	public ArrayList<KVMove> possibleMoves(KVRole playerRole) {
		ArrayList<KVMove> possibleMoves = new ArrayList<>();

		ArrayList<PIECE> bluePieces = new ArrayList<>();
		bluePieces.add(PIECE.SOLDATBLEU);
		bluePieces.add(PIECE.ROIBLEU);

		ArrayList<PIECE> whitePieces = new ArrayList<>();
		whitePieces.add(PIECE.SOLDATWHITE);
		whitePieces.add(PIECE.ROIWHITE);

		// Parcours la matrice de plateau et trouver tous les moves possibles pour tous les pièces de joueur playerRole
		for (int i = 0; i < DEFAULT_GRID_SIZE; i++) {
			for (int j = 0; j < DEFAULT_GRID_SIZE; j++) {
				// Vérifier le role et la position de cette case
				if ((playerRole == KVRole.BLUE && bluePieces.contains(boardGrid[i][j])) ||
						(playerRole == KVRole.WHITE && whitePieces.contains(boardGrid[i][j]))) {
					for (KVMove.DIRECTION d : KVMove.DIRECTION.values()) {
						// Vérifier si ce move est valide
						if (isValidMove(new KVMove(i, j, d), playerRole)) {
							// Calculer la position de cette pièce apèrs le move
							Point step = step(d);
							int x = i;
							int y = j;
							for (int k = 0; k < DEFAULT_GRID_SIZE; k++) {
								x += step.x;
								y += step.y;

								if (x == 3 && y == 3) {
									if (playerRole == KVRole.BLUE && boardGrid[i][j] != PIECE.ROIBLEU) break;
									if (playerRole == KVRole.WHITE && boardGrid[i][j] != PIECE.ROIWHITE) break;
								}

								if ((x < 0 || x > DEFAULT_GRID_SIZE - 1 || y < 0 || y > DEFAULT_GRID_SIZE - 1) ||
										(boardGrid[x][y] != PIECE.EMPTY)) {

									possibleMoves.add(new KVMove(i, j, x - step.x, y - step.y));
									break;
								}
							}
						}
					}
				}
			}
		}
		return possibleMoves;
	}

	@Override
	public KVBoard play(KVMove move, KVRole playerRole) {
		PIECE[][] newGrid = copyGrid();
		PIECE piece = newGrid[move.start.x][move.start.y];

		newGrid[move.start.x][move.start.y] = PIECE.EMPTY;
		newGrid[move.end.x][move.end.y] = piece;

		// Mise à jour la position de roi si besoin
		switch (piece) {
			case ROIBLEU -> roiBleu = new Point(move.end.x, move.end.y);
			case ROIWHITE -> roiWhite = new Point(move.end.x, move.end.y);
		}

		return new KVBoard(newGrid);
	}

	@Override
	public boolean isValidMove(KVMove move, KVRole playerRole) {
		Point step = step(move.direction);
		int new_x = move.start.x + step.x;
		int new_y = move.start.y + step.y;

		// Vérifier si une pièce a un direction disponible
		if (new_x < 0 || new_x > DEFAULT_GRID_SIZE - 1 || new_y < 0 || new_y > DEFAULT_GRID_SIZE - 1) return false;
		return boardGrid[new_x][new_y] == PIECE.EMPTY;
	}

	@Override
	public boolean isGameOver() {
	    if (roiWhite.x == roiWhite.y && roiWhite.x == 3) return true;
		if (roiBleu.x == roiBleu.y && roiBleu.x == 3) return true;
		return roiBlock(KVRole.BLUE) || roiBlock(KVRole.WHITE);
	}

	/**
	 * Détecter si un roi est bloqué
	 * @param role KVRole
	 * @return boolean
	 */
	public boolean roiBlock(KVRole role) {
		if (role == KVRole.BLUE) {
			for (KVMove.DIRECTION d : KVMove.DIRECTION.values()) {
				Point step = step(d);
				step.x += roiBleu.x;
				step.y += roiBleu.y;

				if (step.x < DEFAULT_GRID_SIZE && step.y < DEFAULT_GRID_SIZE &&
						step.x > 0 && step.y > 0 && boardGrid[step.x][step.y] == PIECE.EMPTY) {
					return false;
				}
			}
		}
		if (role == KVRole.WHITE) {
			for (KVMove.DIRECTION d : KVMove.DIRECTION.values()) {
				Point step = step(d);
				step.x += roiWhite.x;
				step.y += roiWhite.y;

				if (step.x < DEFAULT_GRID_SIZE && step.y < DEFAULT_GRID_SIZE &&
						step.x > 0 && step.y > 0 && boardGrid[step.x][step.y] == PIECE.EMPTY) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public ArrayList<Score<KVRole>> getScores() {
		ArrayList<Score<KVRole>> scores = new ArrayList<>();
		if (roiWhite.x == roiWhite.y && roiWhite.x == 3 || roiBlock(KVRole.BLUE)) {
			scores.add(new Score<>(KVRole.BLUE,Score.Status.LOOSE,0));
			scores.add(new Score<>(KVRole.WHITE,Score.Status.WIN,1));
		}
		if (roiBleu.x == roiBleu.y && roiBleu.x == 3 || roiBlock(KVRole.WHITE)) {
			scores.add(new Score<>(KVRole.WHITE,Score.Status.WIN,1));
			scores.add(new Score<>(KVRole.BLUE,Score.Status.LOOSE,0));
		}
		return scores;
	}

	/**
	 * Copier la matrice de plateur
	 * @return PIECE[][]
	 */
	private PIECE[][] copyGrid() {
		PIECE[][] newGrid = new PIECE[DEFAULT_GRID_SIZE][DEFAULT_GRID_SIZE];
		for (int i = 0; i < DEFAULT_GRID_SIZE; i++)
			System.arraycopy(boardGrid[i], 0, newGrid[i], 0, DEFAULT_GRID_SIZE);
		return newGrid;
	}


	/**
	 * Avec direction, on calcule le pas
	 * @param direction KVMove.DIRECTION
	 * @return Point
	 */
	public static Point step(KVMove.DIRECTION direction) {
		Point res = null;

		switch (direction) {
			case UP -> res = new Point(0, 1);
			case DOWN -> res = new Point(0, -1);
			case LEFT -> res = new Point(-1, 0);
			case RIGHT -> res = new Point(1, 0);
			case UL -> res = new Point(-1, 1);
			case UR -> res = new Point(1, 1);
			case DL -> res = new Point(-1, -1);
			case DR -> res = new Point(1, -1);
		}

		return res;
	}

	/**
	 * get matrice de plateau
	 *
	 * @return PIECE[][]
	 */
	public PIECE[][] getBoardGrid() {
		return boardGrid;
	}

	public int getNbAutourSoleil(KVRole playerRole) {
		int nb = 0;
		ArrayList<PIECE> piece = new ArrayList<>();
		switch (playerRole) {
			case WHITE -> {
				piece.add(PIECE.ROIWHITE);
				piece.add(PIECE.SOLDATWHITE);
			}
			case BLUE -> {
				piece.add(PIECE.ROIBLEU);
				piece.add(PIECE.SOLDATBLEU);
			}
		}

		for (int i = 2; i < 4; i++) {
			for (int j = 2; j < 4; j++) {
				if (piece.contains(boardGrid[i][j]) && i != 3 && j != 3) {
					nb++;
				}
			}
		}
		return nb;
	}

	public int getNbRoiPossibleMove(KVRole playerRole) {
		int nb = 0;
		if (playerRole == KVRole.BLUE) {
			for (KVMove.DIRECTION d : KVMove.DIRECTION.values()) {
				if (isValidMove(new KVMove(roiBleu.x, roiBleu.y, d), playerRole)) nb++;
			}
		}
		if (playerRole == KVRole.WHITE) {
			for (KVMove.DIRECTION d : KVMove.DIRECTION.values()) {
				if (isValidMove(new KVMove(roiWhite.x, roiWhite.y, d), playerRole)) nb++;
			}
		}
		return nb;
	}

	public float distanceRoiCentre(KVRole playerRole) {
		if (playerRole == KVRole.BLUE) {
			return (float) Math.sqrt(Math.pow(roiBleu.x - 4, 2) + Math.pow(roiBleu.y - 3, 2));
		} else {
			return (float) Math.sqrt(Math.pow(roiWhite.x - 4, 2) + Math.pow(roiWhite.y - 3, 2));
		}
	}

	public int nbEmptyAutourRoi(KVRole role) {
		int nb = 0;
		if (role == KVRole.BLUE) {
			for (KVMove.DIRECTION d : KVMove.DIRECTION.values()) {
				Point step = step(d);
				step.x += roiBleu.x;
				step.y += roiBleu.y;

				if (step.x < DEFAULT_GRID_SIZE && step.y < DEFAULT_GRID_SIZE &&
						step.x > 0 && step.y > 0 && boardGrid[step.x][step.y] == PIECE.EMPTY) {
					nb++;
				}
			}
		}
		if (role == KVRole.WHITE) {
			for (KVMove.DIRECTION d : KVMove.DIRECTION.values()) {
				Point step = step(d);
				step.x += roiWhite.x;
				step.y += roiWhite.y;

				if (step.x < DEFAULT_GRID_SIZE && step.y < DEFAULT_GRID_SIZE &&
						step.x > 0 && step.y > 0 && boardGrid[step.x][step.y] == PIECE.EMPTY) {
					nb++;
				}
			}
		}
		return nb;
	}

	public boolean centreRoi(KVRole playerRole) {
		switch (playerRole) {
			case WHITE -> { return roiWhite.x == 3 && roiWhite.y == 3; }
			case BLUE ->  { return roiBleu.x == 3 && roiBleu.y == 3; }
		}
		return false;
	}
}