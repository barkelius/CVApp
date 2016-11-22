package com.x.swag.swag.model.game.ai;

import com.x.swag.swag.model.game.data.GameState;
import com.x.swag.swag.model.game.options.HasDifficulty;

/**
 * Created by barke on 2016-05-08.
 */
public abstract class AI {

    public interface AITurnHandler {
        GameState played();
    }

    protected HasDifficulty.Difficulty difficulty;
    protected AITurnHandler turnHandler;

    public AI(HasDifficulty.Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public final GameState process(GameState current){
        GameState state = innerProcess(current);
        if(turnHandler != null)
            turnHandler.played();
        return state;
    }

    protected abstract GameState innerProcess(GameState current);


    public void setTurnHandler(AITurnHandler turnHandler) {
        this.turnHandler = turnHandler;
    }
}
