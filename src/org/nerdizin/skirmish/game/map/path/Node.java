package org.nerdizin.skirmish.game.map.path;

import org.nerdizin.skirmish.game.map.Field;

public class Node extends Field implements Comparable<Node> {

    float distanceFromStart;
    float heuristicDistanceFromGoal;
    Node previousNode;

    public Node(final Field field) {
        super(field.getType(), field.getX(), field.getY());
    }

    public Node getPreviousNode() {
        return previousNode;
    }

    public void setPreviousNode(final Node previousNode) {
        this.previousNode = previousNode;
    }

    public float getHeuristicDistanceFromGoal() {
        return heuristicDistanceFromGoal;
    }

    public void setHeuristicDistanceFromGoal(float heuristicDistanceFromGoal) {
        this.heuristicDistanceFromGoal = heuristicDistanceFromGoal;
    }

    public float getDistanceFromStart() {
        return distanceFromStart;
    }

    public void setDistanceFromStart(final float f) {
        this.distanceFromStart = f;
    }

    public int compareTo(final Node otherNode) {
        float thisTotalDistanceFromGoal = heuristicDistanceFromGoal + distanceFromStart;
        float otherTotalDistanceFromGoal = otherNode.getHeuristicDistanceFromGoal() + otherNode.getDistanceFromStart();

        if (thisTotalDistanceFromGoal < otherTotalDistanceFromGoal) {
            return -1;
        } else if (thisTotalDistanceFromGoal > otherTotalDistanceFromGoal) {
            return 1;
        } else {
            return 0;
        }
    }
}
