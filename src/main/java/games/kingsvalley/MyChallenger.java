package games.kingsvalley;

import iialib.games.model.IChallenger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class MyChallenger implements IChallenger {
	KVBoard board;
	KVRole roleChallenger;
	KVRole roleOther;
	String colonne = "ABCDEFG";


	@Override
	public String teamName() {
		Scanner scanner = new Scanner(System.in);
		StringBuilder teamName = new StringBuilder();
		for (int i = 0; i < 3; i++) {
			System.out.println("Enter Participant " + (i+1) + ": ");
			teamName.append(scanner.nextLine()).append(" - ");
		}
		teamName.delete(teamName.length() - 3, teamName.length());
//		System.out.println(teamName.toString());
		return teamName.toString();
	}

	@Override
	public void setRole(String role) {
		switch (role) {
			case "BLACK" -> {
				roleChallenger = KVRole.BLUE;
				roleOther = KVRole.WHITE;
			}
			case "WHITE" -> {
				roleChallenger = KVRole.WHITE;
				roleOther = KVRole.BLUE;
			}
			default -> {
				System.out.println("Unrecognized role name, reset to default");
				roleChallenger = KVRole.BLUE;
				roleOther = KVRole.WHITE;
			}
		}
	}

	@Override
	public void iPlay(String move) {
		if (possibleMovesRole(roleChallenger).contains(move)) {
			board = board.play(moveToKvmove(move), roleChallenger);
		} else {
			System.out.println("Wrong move 3");
		}
	}

	@Override
	public void otherPlay(String move) {
		if (possibleMovesRole(roleOther).contains(move)) {
			board = board.play(moveToKvmove(move), roleOther);
		} else {
			System.out.println("Wrong move 4");
		}

	}

	private KVMove moveToKvmove(String move) {
		char[] moves = move.toCharArray();
		int x1 = 7 - (moves[1] - '0');
		int x2 = 7 - (moves[4] - '0');
		int y1 = colonne.indexOf(moves[0]);
		int y2 = colonne.indexOf(moves[3]);
//		System.out.println(x1 + ", " + y1 + "\t" + x2 + ", " + y2);
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
		KVBoard.PIECE[][] boardGrid = board.getBoardGrid();
		StringBuilder table = new StringBuilder();
		table.append("    A B C D E F G \n  .................\n");
		for (int i = 0; i < KVBoard.DEFAULT_GRID_SIZE; i++) {
			table.append(7 - i).append(" : ");
			for (int j = 0; j < KVBoard.DEFAULT_GRID_SIZE; j++) {
				if (i == 3 && j == 3) {
					switch (boardGrid[i][j]) {
						case ROIBLEU -> table.append("O ");
						case ROIWHITE -> table.append("X ");
						default -> table.append("+ ");
					}
					continue;
				}
				switch (boardGrid[i][j]) {
					case EMPTY -> table.append("- ");
					case ROIBLEU -> table.append("O ");
					case ROIWHITE -> table.append("X ");
					case SOLDATBLEU -> table.append("o ");
					case SOLDATWHITE -> table.append("x ");
				}
			}
			table.append(" : ").append(7 - i).append("\n");
		}
		table.append("  .................\n    A B C D E F G");
		return table.toString();

	}

	@Override
	public void setBoardFromFile(String fileName) {
		File text = new File(fileName);
		Scanner scanner = null;
		try {
			scanner = new Scanner(text);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		assert scanner != null;

		KVBoard.PIECE[][] boardGrid = new KVBoard.PIECE[KVBoard.DEFAULT_GRID_SIZE][KVBoard.DEFAULT_GRID_SIZE];
		scanner.nextLine();
		scanner.nextLine();
		int lines = 0;
		while (scanner.hasNextLine() && lines < 7) {
			String line = scanner.nextLine();
			String[] strings = line.split(" ");
			int collone = 0;

			for (String c : strings) {
				switch (c) {
					case "o" -> boardGrid[lines][collone] = KVBoard.PIECE.SOLDATBLEU;
					case "x" -> boardGrid[lines][collone] = KVBoard.PIECE.SOLDATWHITE;
					case "X" -> boardGrid[lines][collone] = KVBoard.PIECE.ROIWHITE;
					case "O" -> boardGrid[lines][collone] = KVBoard.PIECE.ROIBLEU;
					case "-", "+" -> boardGrid[lines][collone] = KVBoard.PIECE.EMPTY;
					default -> collone--;
				}
				collone++;
			}
			lines++;
		}

		board = new KVBoard(boardGrid);
	}

	@Override
	public Set<String> possibleMoves() {
		return possibleMovesRole(roleChallenger);
	}

	private Set<String> possibleMovesRole(KVRole role) {
		Set<String> moves = new HashSet<>();
		for (KVMove m : board.possibleMoves(role)) {
			String test = colonne.charAt(m.start.y) + "" + (7 - m.start.x) + "-" + colonne.charAt(m.end.y) + (7 - m.end.x);
			moves.add(test);
			System.out.println(test);
		}
		return moves;
	}
}
