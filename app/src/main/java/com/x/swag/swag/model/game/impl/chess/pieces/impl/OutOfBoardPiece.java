package com.x.swag.swag.model.game.impl.chess.pieces.impl;

import com.x.swag.swag.model.game.impl.chess.Board;
import com.x.swag.swag.model.game.impl.chess.Position;
import com.x.swag.swag.model.game.impl.chess.pieces.ChessPiece;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by barke on 2016-03-26.
 */
public class OutOfBoardPiece implements ChessPiece {

    public OutOfBoardPiece(){ }

    @Override
    public List<Position> validMoves(Board board, Position p, boolean includeKing) {
        return new ArrayList<>();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean isValid() {
        return false;
    }


}
