package games.kingsvalley;

import iialib.games.model.IChallenger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class MyChallenger implements IChallenger {
    KVBoard board = new KVBoard();
    KVRole roleChallenger;
    KVRole roleOther;
    ArrayList<String> bibliotheque = new ArrayList<>();

    @Override
    public String teamName() {
        return "Humain";
    }

    @Override
    public void setRole(String role) {
        switch (role) {
            case "BLUE":
                roleChallenger = KVRole.BLUE;
                roleOther = KVRole.WHITE;
                System.out.println("You are BLUE.");
                break;
            case "WHITE":
                roleChallenger = KVRole.WHITE;
                roleOther = KVRole.BLUE;
                System.out.println("You are WHITE.");
                break;
        }

        // Bibilothéque Overture
        if (roleChallenger == KVRole.BLUE) {
            bibliotheque.add("B1-B6");
            bibliotheque.add("D7-G4");
            bibliotheque.add("G1-C5");
            bibliotheque.add("C1-C4");
            bibliotheque.add("G4-D4");
        } else {
            bibliotheque.add("B7-B2");
            bibliotheque.add("D1-G4");
            bibliotheque.add("G7-C3");
            bibliotheque.add("C7-C4");
            bibliotheque.add("G4-D4");
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

    @Override
    public String bestMove() {
        if (bibliotheque.isEmpty()) {
            return board.bestMove(roleChallenger).toString();
        } else {
            String move = bibliotheque.get(0);
            if (board.isValidMove(moveToKvmove(move), roleChallenger)) {
                bibliotheque.remove(move);
                return move;
            } else {
                bibliotheque = new ArrayList<>();
                return bestMove();
            }
        }
    }

    @Override
    public String victory() {
        return "YES!";
    }

    @Override
    public String defeat() {
        return "GG";
    }

    @Override
    public String tie() {
        return "What!";
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
                        case ROIBLEU:
                            table.append("O ");
                            break;
                        case ROIWHITE:
                            table.append("X ");
                            break;
                        default:
                            table.append("+ ");
                            break;
                    }
                    continue;
                }
                switch (boardGrid[i][j]) {
                    case EMPTY:
                        table.append("- ");
                        break;
                    case ROIBLEU:
                        table.append("O ");
                        break;
                    case ROIWHITE:
                        table.append("X ");
                        break;
                    case SOLDATBLEU:
                        table.append("o ");
                        break;
                    case SOLDATWHITE:
                        table.append("x ");
                        break;
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
                    case "o":
                        boardGrid[lines][collone] = KVBoard.PIECE.SOLDATBLEU;
                        break;
                    case "x":
                        boardGrid[lines][collone] = KVBoard.PIECE.SOLDATWHITE;
                        break;
                    case "X":
                        boardGrid[lines][collone] = KVBoard.PIECE.ROIWHITE;
                        break;
                    case "O":
                        boardGrid[lines][collone] = KVBoard.PIECE.ROIBLEU;
                        break;
                    case "-":
                        boardGrid[lines][collone] = KVBoard.PIECE.EMPTY;
                        break;
                    case "+":
                        boardGrid[lines][collone] = KVBoard.PIECE.EMPTY;
                        break;
                    default:
                        collone--;
                        break;
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
            moves.add(m.toString());
        }
        return moves;
    }

    /**
     * Transmettre String à un instance KVMove
     *
     * @param move String
     * @return KVMove
     */
    private KVMove moveToKvmove(String move) {
        String colonne = "ABCDEFG";
        char[] moves = move.toCharArray();
        int x1 = 7 - (moves[1] - '0');
        int x2 = 7 - (moves[4] - '0');
        int y1 = colonne.indexOf(moves[0]);
        int y2 = colonne.indexOf(moves[3]);
//		System.out.println(x1 + ", " + y1 + "\t" + x2 + ", " + y2);
        return new KVMove(x1, y1, x2, y2);
    }

}
