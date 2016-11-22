package com.x.swag.swag.model.game.data;

/**
 * Created by barke on 2016-03-25.
 */
public class GameState {

    private final String separator = ";";

    public interface HasState {
        String toState();
        void fromState(String state);
    }

    private Class<?> stateOf;

    private String object;

    private GameState(Class<?> stateOf, String object){
        this.stateOf = stateOf;
        this.object = object;
    }

    public static GameState of(Class<?> stateOf, String data){
        return new GameState(stateOf, data);
    }

    public String serialize(){
        return stateOf.getSimpleName() + separator + object;
    }

    public String unserialize(Class<?> target) {
        if(validateStateOf(target))
            return object;
        return null;
    }

    private boolean validateStateOf(Class<?> target){
        boolean result = stateOf.equals(target);
        if(!result)
            throw new IllegalArgumentException("This state is not the same type as target:" + target.getSimpleName());
        return result;
    }

    public GameState clone(){
        return new GameState(stateOf, object);
    }

}
