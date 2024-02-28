package test.frontfx;

import java.util.Comparator;

public class Move {
    private int row;
    private int col;
    private int score;

    public Move(int row, int col, int score) {
        this.row = row;
        this.col = col;
        this.score = score;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getScore() {
        return score;
    }
}