package com.x.swag.swag.model.game.impl.chess.pieces.impl;

import com.x.swag.swag.model.game.impl.chess.Board;
import com.x.swag.swag.model.game.impl.chess.Position;
import com.x.swag.swag.model.game.impl.chess.pieces.AbstractChessPiece;

import java.util.ArrayList;
import java.util.List;

/**
 * Tower chess piece
 */
public final class RookPiece extends AbstractChessPiece {

    private RookPiece(boolean white) {
        super(white);
    }
    public static RookPiece black(){
        return new RookPiece(false);
    }
    public static RookPiece white(){
        return new RookPiece(true);
    }
    public static RookPiece of(boolean white){
        return new RookPiece(white);
    }

    @Override
    public List<Position> validMoves(Board board, Position pos, boolean includeKing) {
        if(!((board.get(pos) instanceof RookPiece) || (board.get(pos) instanceof QueenPiece)))
            throw new IllegalArgumentException(board.get(pos).getClass().getSimpleName() + " Not an instance of: " + getClass().getSimpleName());

        List<Position> valid = new ArrayList<>();
        Position tmp = pos;

        for(tmp = pos.moveY(Position.TOP); board.get(tmp).isEmpty(); tmp = tmp.moveY(Position.TOP) )
            valid.add(tmp);
        addIfEnemy(valid, board, tmp, includeKing);

        for(tmp = pos.moveY(Position.BOTTOM); board.get(tmp).isEmpty(); tmp = tmp.moveY(Position.BOTTOM))
            valid.add(tmp);
        addIfEnemy(valid, board, tmp, includeKing);

        for(tmp = pos.moveX(Position.RIGHT); board.get(tmp).isEmpty(); tmp = tmp.moveX(Position.RIGHT) )
            valid.add(tmp);
        addIfEnemy(valid, board, tmp, includeKing);

        for(tmp = pos.moveX(Position.LEFT); board.get(tmp).isEmpty(); tmp = tmp.moveX(Position.LEFT))
            valid.add(tmp);
        addIfEnemy(valid, board, tmp, includeKing);

        return valid;
    }

    @Override
    public int value() {
        return 4;
    }
}
