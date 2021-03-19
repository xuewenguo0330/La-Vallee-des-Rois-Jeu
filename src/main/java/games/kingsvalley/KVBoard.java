package games.kingsvalley;

import java.util.ArrayList;

import iialib.games.model.IBoard;
import iialib.games.model.Score;

public class KVBoard implements IBoard<KVMove, KVRole, KVBoard> {
	enum PIECE {EMPTY, SOLDATBLEU, SOLDATROUGE, ROIBLEU, ROIROUGE}

	private static final int DEFAULT_GRID_SIZE = 7;
	private PIECE[][] boardGrid;

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
	}

	public KVBoard(PIECE[][] board) {
		boardGrid = board;
	}

	@Override
	public ArrayList<KVMove> possibleMoves(KVRole playerRole) {
		// TODO Auto-generated method stub

		return null;
	}

	@Override
	public KVBoard play(KVMove move, KVRole playerRole) {
		PIECE[][] newGrid = copyGrid();
		int stepx = 0;
		int stepy = 0;
		switch (move.direction) {
			case UP:
				stepy = 1;
				break;
			case DOWN:
				stepy = -1;
				break;
			case LEFT:
				stepx = -1;
				break;
			case RIGHT:
				stepx = 1;
				break;
			case UL:
				stepx = 1;
				stepy = 1;
				break;
			case UR:
				stepx = -1;
				stepy = 1;
				break;
			case DL:
				stepx = 1;
				stepy = -1;
				break;
			case DR:
				stepx = -1;
				stepy = -1;
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + move.direction);
		}

		int x = move.position.x;
		int y = move.position.y;

		PIECE piece = newGrid[x][y];
		newGrid[x][y] = PIECE.EMPTY;
		for (int i = 0; i < DEFAULT_GRID_SIZE; i++) {
			x += stepx;
			y += stepy;

			if (newGrid[x][y] != PIECE.EMPTY) {
				newGrid[x - stepx][y - stepy] = piece;
				break;
			}
		}

		return new KVBoard(newGrid);
	}

	@Override
	public boolean isValidMove(KVMove move, KVRole playerRole) {
		int stepx = 0;
		int stepy = 0;
		switch (move.direction) {
			case UP:
				stepy = 1;
				break;
			case DOWN:
				stepy = -1;
				break;
			case LEFT:
				stepx = -1;
				break;
			case RIGHT:
				stepx = 1;
				break;
			case UL:
				stepx = 1;
				stepy = 1;
				break;
			case UR:
				stepx = -1;
				stepy = 1;
				break;
			case DL:
				stepx = 1;
				stepy = -1;
				break;
			case DR:
				stepx = -1;
				stepy = -1;
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + move.direction);
		}
		return boardGrid[move.position.x + stepx][move.position.y + stepy] == PIECE.EMPTY;
	}

	@Override
	public boolean isGameOver() {

		return false;
	}

	@Override
	public ArrayList<Score<KVRole>> getScores() {
		// TODO Auto-generated method stub
		return null;
	}

	private PIECE[][] copyGrid() {
		PIECE[][] newGrid = new PIECE[DEFAULT_GRID_SIZE][DEFAULT_GRID_SIZE];
		for (int i = 0; i < DEFAULT_GRID_SIZE; i++)
			System.arraycopy(boardGrid[i], 0, newGrid[i], 0, DEFAULT_GRID_SIZE);
		return newGrid;
	}
}
