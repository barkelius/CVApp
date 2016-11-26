package com.x.swag.swag.model.game.impl.chess.pieces.impl;

import com.x.swag.swag.model.game.impl.chess.board.Board;
import com.x.swag.swag.model.game.impl.chess.Position;
import com.x.swag.swag.model.game.impl.chess.pieces.AbstractChessPiece;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by barke on 2016-03-25.
 */
public final class PawnPiece extends AbstractChessPiece {

    private PawnPiece(boolean white) {
        super(white);
    }
    public static PawnPiece black(){
        return new PawnPiece(false);
    }
    public static PawnPiece white(){
        return new PawnPiece(true);
    }

    @Override
    public List<Position> validMoves(Board board, Position pos, boolean includeKing) {
        if(!(board.get(pos) instanceof PawnPiece))
            throw new IllegalArgumentException(board.get(pos).getClass().getSimpleName() + " Not an instance of: " + getClass().getSimpleName() + ", pos:" + pos);

        List<Position> valid = new ArrayList<>();

        addIfEmpty(valid, board, pos.moveY(team));
        if(valid.size() == 1){
            if(isWhite() && pos.getY() == 1)
                addIfEmpty(valid, board, pos.moveY(team * 2));
            else if(!isWhite() && pos.getY() == 6)
                addIfEmpty(valid, board, pos.moveY(team * 2));
        }

        addIfEnemy(valid, board, pos.moveLeft(team), includeKing);
        addIfEnemy(valid, board, pos.moveRight(team), includeKing);

        return valid;
    }

    @Override
    public int value() {
        return 1;
    }

}
