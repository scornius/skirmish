package org.nerdizin.skirmish.game.map;

import org.nerdizin.skirmish.game.Game;
import javafx.scene.Group;
import javafx.scene.Node;

public class Map {

    private Game game;
    private Field[][] fields;
    private int width;
    private int height;

    public Map(final Game game, final int width, final int height) {
        fields = new Field[width][height];
        this.width = width;
        this.height = height;
        this.game = game;
    }

    public Node createOrUpdateNode() {
        final Group fields = new Group();
        final Map map = game.getMap();
        for(int x=0; x<map.getWidth(); x++) {
            for(int y=0; y<map.getHeight(); y++) {
                final Field field = map.getField(x, y);
                addField(game, field, fields);
            }
        }
        return fields;
    }

    private void addField(final Game game, final Field field, final Group group) {
        final Node node = field.createNode(game);
        group.getChildren().add(node);
    }

    public Field getField(final Field field) {
        return fields[field.getX()][field.getY()];
    }

    public Field getField(final int x, final int y) {
        return fields[x][y];
    }

    public void setField(final int x, final int y, final Field field) {
        fields[x][y] = field;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Field[][] getFields() {
        return fields;
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for(int y=0; y<height; y++) {
            for(int x=0; x<width; x++) {
                sb.append(fields[x][y]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}