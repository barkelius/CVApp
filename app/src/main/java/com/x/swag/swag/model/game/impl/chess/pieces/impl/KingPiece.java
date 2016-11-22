package com.x.swag.swag.model.game.impl.chess.pieces.impl;

import com.x.swag.swag.model.game.impl.chess.Board;
import com.x.swag.swag.model.game.impl.chess.Position;
import com.x.swag.swag.model.game.impl.chess.pieces.AbstractChessPiece;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.x.swag.swag.model.game.impl.chess.Position.BOTTOM;
import static com.x.swag.swag.model.game.impl.chess.Position.LEFT;
import static com.x.swag.swag.model.game.impl.chess.Position.RIGHT;
import static com.x.swag.swag.model.game.impl.chess.Position.TOP;

/**
 * Created by barke on 2016-03-25.
 */
public final class KingPiece extends AbstractChessPiece {

    private KingPiece (boolean white) {
        super(white);
    }
    public static KingPiece black(){
        return new KingPiece(false);
    }
    public static KingPiece white(){
        return new KingPiece(true);
    }

    @Override
    public List<Position> validMoves(Board boardInterface, Position pos, boolean includeKing) {
        List<Position> valid = new ArrayList<>();

        addIfEnemyOrEmpty(valid, boardInterface, pos.moveTopLeft(), includeKing);
        addIfEnemyOrEmpty(valid, boardInterface, pos.moveY(TOP), includeKing);
        addIfEnemyOrEmpty(valid, boardInterface, pos.moveTopRight(), includeKing);
        addIfEnemyOrEmpty(valid, boardInterface, pos.moveX(RIGHT), includeKing);
        addIfEnemyOrEmpty(valid, boardInterface, pos.moveBottomRight(), includeKing);
        addIfEnemyOrEmpty(valid, boardInterface, pos.moveY(BOTTOM), includeKing);
        addIfEnemyOrEmpty(valid, boardInterface, pos.moveBottomLeft(), includeKing);
        addIfEnemyOrEmpty(valid, boardInterface, pos.moveX(LEFT), includeKing);

        return valid;
    }

    @Override
    public int value() {
        return 6;
    }


    public List<Position> checked(Board board, Position kingPos) {
        List<Position> fromChecked = new ArrayList<>();
        for(Map.Entry<Position, AbstractChessPiece> entry : board.entries()){
            if(isEnemy(entry.getValue()))
                if(entry.getValue().validMoves(board, entry.getKey(), true).contains(kingPos))
                    fromChecked.add(entry.getKey());
        }

        return fromChecked;
    }


}
