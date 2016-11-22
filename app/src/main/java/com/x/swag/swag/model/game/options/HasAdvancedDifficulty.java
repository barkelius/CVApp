package com.x.swag.swag.model.game.options;

/**
 * Created by barke on 2016-03-25.
 */
public interface HasAdvancedDifficulty {

    public enum Difficulty {
        TRASH(10), BEGINER(3), AVERAGE(1), DREAMER(0.5), PRO(0.1);

        double points;
        private Difficulty(double pts){
            this.points = pts;
        }
        public double getPointsMultiplier(){
            return points;
        }
    }

    public Difficulty getDificulty();

}
