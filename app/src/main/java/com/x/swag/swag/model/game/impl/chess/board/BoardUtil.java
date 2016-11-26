package com.x.swag.swag.model.game.impl.chess.board;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.x.swag.swag.model.game.impl.chess.Position;
import com.x.swag.swag.model.game.impl.chess.pieces.AbstractChessPiece;
import com.x.swag.swag.model.game.impl.chess.pieces.impl.KingPiece;

import java.util.Map;

/**
 * Created by axel on 2016-11-24.
 */

public final class BoardUtil {

    public static King findKing(final Board board, final boolean white) {
        Optional<Map.Entry<Position, AbstractChessPiece>> kingEntry =
            FluentIterable.from(board.entries())
                .firstMatch(new Predicate<Map.Entry<Position, AbstractChessPiece>>() {
                    @Override public boolean apply(Map.Entry<Position, AbstractChessPiece> e) {
                        return e.getValue() instanceof KingPiece && e.getValue().isWhite() == white;
                    }
                });

        if (!kingEntry.isPresent())
            throw new IllegalStateException("No " + (white ? "white":"black") + " king on the board");
        return new King(kingEntry.get().getKey(),
                        (KingPiece) kingEntry.get().getValue());
    }

    public static class King {
        public Position pos;
        public KingPiece king;
        private King(Position pos, KingPiece king){
            this.pos = pos;
            this.king = king;
        }
    }
}
