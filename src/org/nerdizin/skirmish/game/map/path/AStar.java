package org.nerdizin.skirmish.game.map.path;

import org.nerdizin.skirmish.game.map.Field;
import org.nerdizin.skirmish.game.map.Map;
import org.nerdizin.skirmish.game.map.MapHelper;

import java.util.ArrayList;
import java.util.List;

public class AStar {

    private Map map;
    private AStarHeuristic heuristic;

    private ArrayList<Node> closedList;
    private SortedNodeList openList;
    private ArrayList<Field> shortestPath;

    public AStar(final Map map) {
        this.map = map;
        this.heuristic = new AStarHeuristic();
        closedList = new ArrayList<>();
        openList = new SortedNodeList();
    }

    public ArrayList<Field> calcShortestPath(final Field sourceField, final Field targetField) {

        // Check if the targetField is also an obstacle (if it is, it is impossible to find a path there)
        if (targetField.getType().isBlocksMovement()) {
            return null;
        }

        final Node source = new Node(sourceField);
        source.setDistanceFromStart(0);
        closedList.clear();
        openList.clear();
        openList.add(source);

        // while we haven't reached the goal yet
        while(openList.size() != 0) {

            // get the first Node from non-searched Node list, sorted by lowest distance from
            // our goal as guessed by our heuristic
            Node current = openList.getFirst();

            // check if our current Node location is the goal Node. If it is, we are done.
            if(current.getX() == targetField.getX() && current.getY() == targetField.getY()) {
                return reconstructPath(current);
            }

            // move current Node to the closed (already searched) list
            openList.remove(current);
            closedList.add(current);

            //go through all the current Nodes neighbors and calculate if one should be our next step
            for(Node neighbor : getNeighborList(current, map)) {
                boolean neighborIsBetter;

                //if we have already searched this Node, don't bother and continue to the next one
                if (closedList.contains(neighbor))
                    continue;

                // calculate how long the path is if we choose this neighbor as the next step in the path
                float neighborDistanceFromStart = (current.getDistanceFromStart()
                        + MapHelper.getDistance(current, neighbor));

                // add neighbor to the open list if it is not there
                if(!openList.contains(neighbor)) {
                    openList.add(neighbor);
                    neighborIsBetter = true;
                    //if neighbor is closer to start it could also be better
                } else if(neighborDistanceFromStart < current.getDistanceFromStart()) {
                    neighborIsBetter = true;
                } else {
                    neighborIsBetter = false;
                }
                // set neighbors parameters if it is better
                if (neighborIsBetter) {
                    neighbor.setPreviousNode(current);
                    neighbor.setDistanceFromStart(neighborDistanceFromStart);
                    neighbor.setHeuristicDistanceFromGoal(
                            heuristic.getEstimatedDistanceToGoal(neighbor, targetField));
                }

            }
        }
        return null;
    }

    private List<Node> getNeighborList(final Field sourceField, final Map map) {
        List<Node> result = new ArrayList<>();
        for (final Field field : MapHelper.getNeighborFields(sourceField, map)) {
            result.add(new Node(field));
        }
        return result;
    }

    private ArrayList<Field> reconstructPath(Node node) {
        final ArrayList<Field> path = new ArrayList<>();
        while(!(node.getPreviousNode() == null)) {
            path.add(0, node);
            node = node.getPreviousNode();
        }
        this.shortestPath = path;
        return path;
    }
}
