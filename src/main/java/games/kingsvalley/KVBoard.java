package games.kingsvalley;

import java.util.ArrayList;

import iialib.games.model.IBoard;
import iialib.games.model.Score;

public class KVBoard implements IBoard<KVMove, KVRole, KVBoard> {

	@Override
	public ArrayList<KVMove> possibleMoves(KVRole playerRole) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public KVBoard play(KVMove move, KVRole playerRole) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isValidMove(KVMove move, KVRole playerRole) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isGameOver() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Score<KVRole>> getScores() {
		// TODO Auto-generated method stub
		return null;
	}

}
