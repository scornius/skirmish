package org.nerdizin.skirmish.game.map.path;

import java.util.ArrayList;
import java.util.Collections;

public class SortedNodeList {

    private ArrayList<Node> list = new ArrayList<>();

    public Node getFirst() {
        return list.get(0);
    }

    public void clear() {
        list.clear();
    }

    public void add(final Node node) {
        list.add(node);
        Collections.sort(list);
    }

    public void remove(final Node n) {
        list.remove(n);
    }

    public int size() {
        return list.size();
    }

    public boolean contains(final Node n) {
        return list.contains(n);
    }

}
