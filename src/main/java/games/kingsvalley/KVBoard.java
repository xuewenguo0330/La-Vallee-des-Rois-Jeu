package games.kingsvalley;

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

	public KVBoard() {
		boardGrid = new PIECE[DEFAULT_GRID_SIZE][DEFAULT_GRID_SIZE];
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				boardGrid[i][j] = PIECE.EMPTY;
			}
		}
		for (int i = 0; i < 7; i++) {
			boardGrid[0][i] = PIECE.SOLDATBLEU;
			boardGrid[6][i] = PIECE.SOLDATWHITE;
		}
		boardGrid[0][3] = PIECE.ROIWHITE;
		boardGrid[6][3] = PIECE.ROIBLEU;

		roiBleu = new Point(0, 3);
		roiWhite = new Point(6, 3);
	}

	public KVBoard(PIECE[][] board) {
		boardGrid = board;
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
			default -> {
			}
		}

		return new KVBoard(newGrid);
	}

	@Override
	public boolean isValidMove(KVMove move, KVRole playerRole) {
		if (boardGrid[move.start.x][move.start.y] == PIECE.EMPTY) System.out.println("Wrong move 2");

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
		for(KVMove.DIRECTION direction : KVMove.DIRECTION.values()) {
			Point step = step(direction);
			if (role == KVRole.BLUE && boardGrid[roiBleu.x + step.x][roiBleu.y + step.y] == PIECE.EMPTY) {
				return false;
			}
			if (role == KVRole.WHITE && boardGrid[roiWhite.x + step.x][roiWhite.y + step.y] == PIECE.EMPTY) {
				return false;
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
	 * @return PIECE[][]
	 */
	public PIECE[][] getBoardGrid() {
		return boardGrid;
	}
}