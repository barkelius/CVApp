package com.x.swag.swag.model.game.impl.chess.board;

import com.x.swag.swag.model.game.impl.chess.Position;
import com.x.swag.swag.model.game.impl.chess.pieces.AbstractChessPiece;
import com.x.swag.swag.model.game.impl.chess.pieces.ChessPiece;

import java.util.Map;

/**
 * Created by barke on 2016-06-28.
 * Board interface for a chess game.
 */
public interface Board {
    ChessPiece get(Position position);

    AbstractChessPiece move(Position from, Position to);

    //Board.King findKing(boolean white);

    Iterable<Map.Entry<Position, AbstractChessPiece>> entries();

    Board clone();

}
