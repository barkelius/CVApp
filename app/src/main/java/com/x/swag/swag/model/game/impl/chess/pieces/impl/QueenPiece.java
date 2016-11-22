package com.x.swag.swag.model.game.impl.chess.pieces.impl;

import com.x.swag.swag.model.game.impl.chess.Board;
import com.x.swag.swag.model.game.impl.chess.Position;
import com.x.swag.swag.model.game.impl.chess.pieces.AbstractChessPiece;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by barke on 2016-03-25.
 */
public final class QueenPiece extends AbstractChessPiece {

    private QueenPiece(boolean white) {
        super(white);
    }
    public static QueenPiece black(){
        return new QueenPiece(false);
    }
    public static QueenPiece white(){
        return new QueenPiece(true);
    }


    @Override
    public List<Position> validMoves(Board board, Position pos, boolean includeKing) {
        if(!(board.get(pos) instanceof QueenPiece))
            throw new IllegalArgumentException("Not an instance of: " + getClass().getSimpleName());

        List<Position> valid = new ArrayList<>();

        boolean isWhite = team == 1;
        valid.addAll(RookPiece.of(isWhite).validMoves(board, pos, includeKing));
        valid.addAll(BishopPiece.of(isWhite).validMoves(board, pos, includeKing));

        return valid;
    }

    @Override
    public int value() {
        return 5;
    }
}
