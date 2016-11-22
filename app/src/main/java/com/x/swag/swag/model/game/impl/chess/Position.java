package com.x.swag.swag.model.game.impl.chess;

import static com.x.swag.swag.model.game.impl.chess.ChessGame.width;

/**
 * Created by barke on 2016-03-25.
 */
public abstract class Position {

    public static final int LEFT = -1, TOP = -1;
    public static final int RIGHT = 1, BOTTOM = 1;

    protected int x;
    protected int y;

    protected Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    protected Position() { }

    public static Position of(int x, int y) {
        if(valid(x, y))
            return new ValidPosition(x, y);
        return invalid();
    }

    private static boolean valid(int x, int y){
        return 0 <= x && x < width && 0 <= y && y < width;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Position moveTopLeft(){
        return Position.of(x + LEFT, y + TOP);
    }
    public Position moveTopRight(){
        return Position.of(x + RIGHT, y + TOP);
    }
    public Position moveBottomLeft(){
        return Position.of(x + LEFT, y + BOTTOM);
    }
    public Position moveBottomRight(){
        return Position.of(x + RIGHT, y + BOTTOM);
    }

    public Position moveX(final int move){
        return Position.of(x + move, y);
    }
    public Position moveY(final int move){
        return Position.of(x, y + move);
    }

    public Position moveLeft(int team){
        return Position.of(x + LEFT, y + team);
    }
    public Position moveRight(int team){
        return Position.of(x + RIGHT, y + team);
    }

    public abstract Position clone();
    public abstract boolean isValid();

    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;
        if (o instanceof InvalidPosition) return false;

        Position position = (Position) o;

        return x == position.x && y == position.y;
    }*/

    @Override
    public int hashCode() {
        return 31 * x + y;
    }

    @Override
    public String toString() {
        return "Position{" + "x=" + x + ", y=" + y + '}';
    }


    public static InvalidPosition invalid(){
        return new InvalidPosition();
    }


    public static class InvalidPosition extends Position {
        private InvalidPosition() {
            super();
            this.x = -1;
            this.y = -1;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Position)) return false;
            return o instanceof InvalidPosition;
        }

        public Position clone() { return new InvalidPosition(); }

        public boolean isValid() { return false; }

    }



    public static class ValidPosition extends Position {
        private ValidPosition(int x, int y) {
            super(x,y);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof ValidPosition))
                return false;

            ValidPosition pos = (ValidPosition)o;
            return pos.getX() == x && pos.getY() == y;
        }

        public Position clone() { return new ValidPosition(x, y); }

        public boolean isValid() { return true; }

    }



    public static Position from(int position){
        return of(position%8, position/8);
    }
}
