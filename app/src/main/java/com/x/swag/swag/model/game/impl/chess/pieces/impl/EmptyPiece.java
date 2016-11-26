package com.x.swag.swag.model.game.impl.chess.pieces.impl;

import com.x.swag.swag.model.game.impl.chess.board.Board;
import com.x.swag.swag.model.game.impl.chess.Position;
import com.x.swag.swag.model.game.impl.chess.pieces.ChessPiece;

import java.util.ArrayList;
import java.util.List;

public final class EmptyPiece implements ChessPiece {

    public EmptyPiece(){ }

    @Override
    public List<Position> validMoves(Board board, Position p, boolean includeKing) {
        return new ArrayList<>();
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public boolean isValid() {
        return true;
    }
}
