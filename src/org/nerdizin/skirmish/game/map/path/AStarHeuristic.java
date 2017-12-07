package org.nerdizin.skirmish.game.map.path;

import org.nerdizin.skirmish.game.map.Field;

public class AStarHeuristic {

    public float getEstimatedDistanceToGoal(final Field start, final Field goal) {
        float dx = goal.getX() - start.getX();
        float dy = goal.getY() - start.getY();

        return (float) (Math.sqrt((dx*dx)+(dy*dy)));
    }
}
