package com.x.swag.swag.model.game.options;

/**
 * Created by barke on 2016-03-25.
 */
public interface HasDifficulty {

    public enum Difficulty {
        Easy, Normal, Hard, difficulty;
    }

    public Difficulty getDifficulty();
}
