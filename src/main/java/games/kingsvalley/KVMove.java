package games.kingsvalley;

import iialib.games.model.IMove;

import java.awt.*;

public class KVMove implements IMove {
    enum DIRECTION{UP, DOWN, LEFT, RIGHT, UL, UR, DL, DR}
    Point position;
    DIRECTION direction;

    KVMove(int x, int y, DIRECTION d) {
        position = new Point(x, y);
        direction = d;
    }

    @Override
    public String toString() {
        return "Move{" + position.x + "," + position.y + "}: " + direction.toString();
    }
}
