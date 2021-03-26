package games.kingsvalley;

import iialib.games.model.IBoard;
import iialib.games.model.Score;

import java.awt.*;
import java.util.ArrayList;

public class KVBoard implements IBoard<KVMove, KVRole, KVBoard> {
	enum PIECE {EMPTY, SOLDATBLEU, SOLDATROUGE, ROIBLEU, ROIROUGE}

	private static final int DEFAULT_GRID_SIZE = 7;
	private final PIECE[][] boardGrid;
	private Point roiBleu;
	private Point roiRouge;

	public KVBoard() {
		boardGrid = new PIECE[DEFAULT_GRID_SIZE][DEFAULT_GRID_SIZE];
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				boardGrid[i][j] = PIECE.EMPTY;
			}
		}
		for (int i = 0; i < 7; i++) {
			boardGrid[0][i] = PIECE.SOLDATBLEU;
			boardGrid[6][i] = PIECE.SOLDATROUGE;
		}
		boardGrid[0][3] = PIECE.ROIROUGE;
		boardGrid[6][3] = PIECE.ROIBLEU;

		roiBleu = new Point(0, 3);
		roiRouge = new Point(6, 3);
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
		whitePieces.add(PIECE.SOLDATROUGE);
		whitePieces.add(PIECE.ROIROUGE);

		for (int i = 0; i < DEFAULT_GRID_SIZE; i++) {
			for (int j = 0; j < DEFAULT_GRID_SIZE; j++) {
				if ((playerRole == KVRole.BLUE && bluePieces.contains(boardGrid[i][j])) ||
						(playerRole == KVRole.WHITE && whitePieces.contains(boardGrid[i][j]))) {
					for (KVMove.DIRECTION d : KVMove.DIRECTION.values()) {
						KVMove move = new KVMove(i, j, d);
						if (isValidMove(move, playerRole))
							possibleMoves.add(move);
					}
				}
			}
		}

		return possibleMoves;
	}

	@Override
	public KVBoard play(KVMove move, KVRole playerRole) {
		PIECE[][] newGrid = copyGrid();
		Point step = step(move.direction);

		int x = move.start.x;
		int y = move.start.y;

		PIECE piece = newGrid[x][y];

		newGrid[x][y] = PIECE.EMPTY;
		for (int i = 0; i < DEFAULT_GRID_SIZE; i++) {
			x += step.x;
			y += step.y;

			if (newGrid[x][y] != PIECE.EMPTY) {
				newGrid[x - step.x][y - step.y] = piece;
				switch (piece) {
					case ROIBLEU:
						roiBleu = new Point(x - step.x, y - step.y);
						break;
					case ROIROUGE:
						roiRouge = new Point(x - step.x, y - step.y);
						break;
					default:
						break;
				}
				break;
			}
		}

		return new KVBoard(newGrid);
	}

	@Override
	public boolean isValidMove(KVMove move, KVRole playerRole) {
		Point step = step(move.direction);
		return boardGrid[move.start.x + step.x][move.start.y + step.y] == PIECE.EMPTY;
	}

	@Override
	public boolean isGameOver() {
	    if (roiRouge.x == roiRouge.y && roiRouge.x == 3) return true;
		if (roiBleu.x == roiBleu.y && roiBleu.x == 3) return true;
		return roiBlock(KVRole.BLUE) || roiBlock(KVRole.WHITE);
	}

	public boolean roiBlock(KVRole role) {
		for(KVMove.DIRECTION direction : KVMove.DIRECTION.values()) {
			Point step = step(direction);
			if (role == KVRole.BLUE && boardGrid[roiBleu.x + step.x][roiBleu.y + step.y] == PIECE.EMPTY) {
				return false;
			}
			if (role == KVRole.WHITE && boardGrid[roiRouge.x + step.x][roiRouge.y + step.y] == PIECE.EMPTY) {
				return false;
			}
		}
		return true;
	}

	@Override
	public ArrayList<Score<KVRole>> getScores() {
		ArrayList<Score<KVRole>> scores = new ArrayList<>();
		if (roiRouge.x == roiRouge.y && roiRouge.x == 3) {
			scores.add(new Score<>(KVRole.BLUE,Score.Status.LOOSE,0));
			scores.add(new Score<>(KVRole.WHITE,Score.Status.WIN,1));
		}
		if (roiBleu.x == roiBleu.y && roiBleu.x == 3) {
			scores.add(new Score<>(KVRole.WHITE,Score.Status.WIN,1));
			scores.add(new Score<>(KVRole.BLUE,Score.Status.LOOSE,0));
		}


		boolean flag = true;
		for(KVMove.DIRECTION direction : KVMove.DIRECTION.values()) {
			Point step = step(direction);
			if (boardGrid[roiRouge.x + step.x][roiRouge.y + step.y] == PIECE.EMPTY) {
				flag = false;
				break;
			}
		}
		if (flag) {
			scores.add(new Score<>(KVRole.WHITE,Score.Status.LOOSE,0));
			scores.add(new Score<>(KVRole.BLUE,Score.Status.WIN,1));
		}

		flag = true;
		for (KVMove.DIRECTION direction : KVMove.DIRECTION.values()) {
			Point step = step(direction);
			if (boardGrid[roiBleu.x + step.x][roiBleu.y + step.y] == PIECE.EMPTY) {
				flag = false;
				break;
			}
		}
		if (flag) {
			scores.add(new Score<>(KVRole.BLUE,Score.Status.LOOSE,0));
			scores.add(new Score<>(KVRole.WHITE,Score.Status.WIN,1));
		}

		return scores;
	}

	private PIECE[][] copyGrid() {
		PIECE[][] newGrid = new PIECE[DEFAULT_GRID_SIZE][DEFAULT_GRID_SIZE];
		for (int i = 0; i < DEFAULT_GRID_SIZE; i++)
			System.arraycopy(boardGrid[i], 0, newGrid[i], 0, DEFAULT_GRID_SIZE);
		return newGrid;
	}

	private Point step(KVMove.DIRECTION direction) {
		Point res = new Point(0, 0);

		switch (direction) {
			case UP -> res.y += 1;
			case DOWN -> res.y += -1;
			case LEFT -> res.x += -1;
			case RIGHT -> res.x += 1;
			case UL -> { res.x += -1; res.y += 1; }
			case UR -> { res.x += 1; res.y += 1; }
			case DL -> { res.x += -1; res.y += -1; }
			case DR -> { res.x += 1; res.y += -1; }
			default -> throw new IllegalStateException("Unexpected value: " + direction);
		}

		return res;
	}

	public Point getRoiBleu() {
		return roiBleu;
	}

	public Point getRoiRouge() {
		return roiRouge;
	}

	public PIECE[][] getBoardGrid() {
		return boardGrid;
	}
}
