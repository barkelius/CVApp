package com.x.swag.swag.model.game.impl.chess.pieces;

import com.x.swag.swag.model.game.impl.chess.Board;
import com.x.swag.swag.model.game.impl.chess.Position;

import java.util.List;

/**
 * Created by barke on 2016-03-25.
 */
public interface ChessPiece {

    /**
     * Check for all the valid moves in board from (@code Position) p.
     *
     * @param board the game board.
     * @param p the position to validate from
     * @return All valid moves from Position p on the provided board.
     */
    List<Position> validMoves(Board board, Position p, boolean includeKing);

    /**
     * @return is an Empty piece.
     */
    boolean isEmpty();

    boolean isValid();

}


