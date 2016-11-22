package com.x.swag.swag.model.game;

/**
 * Created by barke on 2016-03-24.
 */
public abstract class GameImpl implements Game {

    final long id;

    public GameImpl(){
        this.id = 0;
    }

    @Override
    public long getId() {
        return id;
    }
}
