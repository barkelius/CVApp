package com.x.swag.swag.model.game;

import com.x.swag.swag.model.game.data.GameState;

/**
 * Created by barke on 2016-03-25.
 */
public interface TurnGame extends Game {
    interface TakeTurn {
        void takeTurn(boolean outcome, GameState updated);
    }

    //void takeTurn(TakeTurn callback, GameState current);
}
