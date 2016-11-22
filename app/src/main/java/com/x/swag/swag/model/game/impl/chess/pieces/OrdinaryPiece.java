package com.x.swag.swag.model.game.impl.chess.pieces;

import com.x.swag.swag.model.game.impl.chess.Position;

/**
 * Created by barke on 2016-03-25.
 */
@Deprecated
public interface OrdinaryPiece {
    Position whitePosLeft();
    Position whitePosRight();
    Position blackPosLeft();
    Position blackPosRight();
}
