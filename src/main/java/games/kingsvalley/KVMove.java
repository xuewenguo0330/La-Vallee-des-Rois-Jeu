package games.kingsvalley;

import iialib.games.model.IMove;

import java.awt.*;

public class KVMove implements IMove {
    enum DIRECTION {UP, DOWN, LEFT, RIGHT, UL, UR, DL, DR}

    Point start;
    Point end;
    DIRECTION direction;

    KVMove(int x1, int y1, int x2, int y2) {
        start = new Point(x1, y1);
        end = new Point(x2, y2);

        // Trouver la direction de ce move
        if (x1 > x2) {
            if (y1 > y2) {
                direction = DIRECTION.DL;
            } else if (y1 == y2) {
                direction = DIRECTION.LEFT;
            } else {
                direction = DIRECTION.UL;
            }
        } else if (x1 == x2) {
            if (y1 > y2) {
                direction = DIRECTION.DOWN;
            } else if (y1 == y2) {
                System.out.println("Wrong move");
            } else {
                direction = DIRECTION.UP;
            }
        } else {
            if (y1 > y2) {
                direction = DIRECTION.DR;
            } else if (y1 == y2) {
                direction = DIRECTION.RIGHT;
            } else {
                direction = DIRECTION.UR;
            }
        }
    }

    KVMove(int x, int y, DIRECTION d) {
        start = new Point(x, y);
        direction = d;
    }

    @Override
    public String toString() {
        String colonne = "ABCDEFG";
        return colonne.charAt(start.y) + "" + (7 - start.x) + "-" + colonne.charAt(end.y) + (7 - end.x);
    }
}
