package com.x.swag.swag.model.game.impl.chess.pieces.impl;

import com.x.swag.swag.model.game.impl.chess.Board;
import com.x.swag.swag.model.game.impl.chess.Position;
import com.x.swag.swag.model.game.impl.chess.pieces.AbstractChessPiece;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by barke on 2016-03-25.
 */
public class BishopPiece extends AbstractChessPiece {

    private BishopPiece(boolean white) {
        super(white);
    }

    public static BishopPiece black(){
        return new BishopPiece(false);
    }
    public static BishopPiece white(){
        return new BishopPiece(true);
    }
    public static BishopPiece of(boolean white){
        return new BishopPiece(white);
    }
    @Override
    public List<Position> validMoves(Board board, Position pos, boolean includeKing) {
        if(!((board.get(pos) instanceof BishopPiece) || (board.get(pos) instanceof QueenPiece)))
            throw new IllegalArgumentException(board.get(pos).getClass().getSimpleName() + " Not an instance of: " + getClass().getSimpleName() + ", pos:" + pos);

        List<Position> valid = new ArrayList<>();
        Position tmp = Position.invalid();

        for(tmp = pos.moveTopRight(); board.get(tmp).isEmpty(); tmp = tmp.moveTopRight())
            valid.add(tmp);
        addIfEnemy(valid, board, tmp, includeKing);

        for(tmp = pos.moveTopLeft(); board.get(tmp).isEmpty(); tmp = tmp.moveTopLeft())
            valid.add(tmp);
        addIfEnemy(valid, board, tmp, includeKing);

        for(tmp = pos.moveBottomRight(); board.get(tmp).isEmpty(); tmp = tmp.moveBottomRight())
            valid.add(tmp);
        addIfEnemy(valid, board, tmp, includeKing);

        for(tmp = pos.moveBottomLeft(); board.get(tmp).isEmpty(); tmp = tmp.moveBottomLeft())
            valid.add(tmp);
        addIfEnemy(valid, board, tmp, includeKing);

        return valid;
    }

    @Override
    public int value() {
        return 3;
    }
}
