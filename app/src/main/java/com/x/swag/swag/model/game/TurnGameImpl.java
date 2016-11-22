package com.x.swag.swag.model.game;

import com.x.swag.swag.model.game.data.GameState;

/**
 * Created by barke on 2016-03-25.
 */
public abstract class TurnGameImpl extends GameImpl implements TurnGame {

    private final TakeTurn callback;

    protected int turn = 0;

    protected TurnGameImpl(final TakeTurn callback){
        this.callback = new TakeTurn() {
            @Override public void takeTurn(boolean outcome, GameState updated) {
                ++turn;
                callback.takeTurn(outcome, updated);
            }
        };
    }

    protected void takeTurn(boolean outcome, GameState updated){
        callback.takeTurn(outcome, updated);
    }

    public final int getTurn(){
        return turn;
    }

}
