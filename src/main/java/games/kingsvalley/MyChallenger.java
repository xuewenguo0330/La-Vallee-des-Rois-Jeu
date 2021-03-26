package games.kingsvalley;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;

import iialib.games.model.IChallenger;

public class MyChallenger implements IChallenger {
	KVBoard board;

	KVRole roleChallenger;
	KVRole roleOther;

	String collone = "ABCDEFG";


	@Override
	public String teamName() {
		return "ABADIE - GUO - CHEN";
	}

	@Override
	public void setRole(String role) {
		switch (role) {
			case "BLACK":
				roleChallenger = KVRole.BLUE;
				roleOther = KVRole.WHITE;
				break;
			case "WHITE":
				roleChallenger = KVRole.WHITE;
				roleOther = KVRole.BLUE;
				break;
			default:
				System.out.println("Unrecognized role name, reset to default");
				roleChallenger = KVRole.BLUE;
				roleOther = KVRole.WHITE;
				break;
		}
	}

	@Override
	public void iPlay(String move) {
		board = board.play(moveToKvmove(move), roleChallenger);
	}

	@Override
	public void otherPlay(String move) {
		board = board.play(moveToKvmove(move), roleOther);
	}

	private KVMove moveToKvmove(String move) {
		char[] moves = move.toCharArray();
		int x1 = collone.indexOf(moves[0]);
		int x2 = collone.indexOf(moves[3]);
		int y1 = moves[1] - '0' - 1;
		int y2 = moves[4] - '0' - 1;

		return new KVMove(x1, y1, x2, y2);
	}

	@Override
	public String bestMove() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String victory() {
		return "Vous avez gagné!";
	}

	@Override
	public String defeat() {
		return "Vous avez perdu!";
	}

	@Override
	public String tie() {
		return "C'est la partie nulle!";
	}

	@Override
	public String getBoard() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBoardFromFile(String fileName) {
		File text = new File(fileName);
		Scanner myReader;
		try {
			myReader = new Scanner(text);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Override
	public Set<String> possibleMoves() {
		// TODO Auto-generated method stub
		return null;
	}

}
