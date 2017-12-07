package org.nerdizin.skirmish.game.map.path;

import java.util.ArrayList;

public class Path {
    private ArrayList<Node> waypoints = new ArrayList<>();

    public int getLength() {
        return waypoints.size();
    }

    public Node getWayPointNode(final int index) {
        return waypoints.get(index);
    }

    public int getX(final int index) {
        return getWayPointNode(index).getX();
    }

    public int getY(final int index) {
        return getWayPointNode(index).getY();
    }

    public void appendWayPoint(final Node n) {
        waypoints.add(n);
    }

    public void prependWayPoint(final Node n) {
        waypoints.add(0, n);
    }

    public boolean contains(final int x, final int y) {
        for(final Node node : waypoints) {
            if (node.getX() == x && node.getY() == y)
                return true;
        }
        return false;
    }
}
