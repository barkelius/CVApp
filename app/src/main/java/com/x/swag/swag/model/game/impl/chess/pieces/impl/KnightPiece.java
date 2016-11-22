package com.x.swag.swag.model.game.impl.chess.pieces.impl;

import com.x.swag.swag.model.game.impl.chess.Board;
import com.x.swag.swag.model.game.impl.chess.Position;
import com.x.swag.swag.model.game.impl.chess.pieces.AbstractChessPiece;

import java.util.ArrayList;
import java.util.List;

import static com.x.swag.swag.model.game.impl.chess.Position.BOTTOM;
import static com.x.swag.swag.model.game.impl.chess.Position.LEFT;
import static com.x.swag.swag.model.game.impl.chess.Position.RIGHT;
import static com.x.swag.swag.model.game.impl.chess.Position.TOP;

/**
 * Created by barke on 2016-03-25.
 */
public final class KnightPiece extends AbstractChessPiece {

    private KnightPiece(boolean white) {
        super(white);
    }
    public static KnightPiece black(){
        return new KnightPiece(false);
    }
    public static KnightPiece white(){
        return new KnightPiece(true);
    }

    @Override
    public List<Position> validMoves(Board boardInterface, Position pos, boolean includeKing) {

        if(!(boardInterface.get(pos) instanceof KnightPiece))
            throw new IllegalArgumentException("Not an instance of: " + getClass().getSimpleName());

        List<Position> valid = new ArrayList<>();

        addIfEnemyOrEmpty(valid, boardInterface, pos.moveTopLeft().moveY(TOP), includeKing);
        addIfEnemyOrEmpty(valid, boardInterface, pos.moveTopLeft().moveX(LEFT), includeKing);

        addIfEnemyOrEmpty(valid, boardInterface, pos.moveTopRight().moveY(TOP), includeKing);
        addIfEnemyOrEmpty(valid, boardInterface, pos.moveTopRight().moveX(RIGHT), includeKing);

        addIfEnemyOrEmpty(valid, boardInterface, pos.moveBottomRight().moveX(RIGHT), includeKing);
        addIfEnemyOrEmpty(valid, boardInterface, pos.moveBottomRight().moveY(BOTTOM), includeKing);

        addIfEnemyOrEmpty(valid, boardInterface, pos.moveBottomLeft().moveY(BOTTOM), includeKing);
        addIfEnemyOrEmpty(valid, boardInterface, pos.moveBottomLeft().moveX(LEFT), includeKing);

        return valid;
    }

    @Override
    public int value() {
        return 2;
    }
}
