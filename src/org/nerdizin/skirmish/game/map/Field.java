package org.nerdizin.skirmish.game.map;

import org.nerdizin.skirmish.game.Game;
import org.nerdizin.skirmish.ui.FieldClickHandler;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Field {

    private FieldType type;
    private int x;
    private int y;

    public Field(final FieldType type, final int x, final int y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public Node createNode(final Game game) {
        final Rectangle rect = new Rectangle();
        rect.setWidth(game.getGridSize());
        rect.setHeight(game.getGridSize());
        rect.setX(getX() * game.getGridSize());
        rect.setY(getY() * game.getGridSize());
        rect.setFill(getFill());
        rect.setStroke(Color.WHITE);
        rect.setOnMouseClicked(new FieldClickHandler(game, this));
        return rect;
    }

    private Paint getFill() {
        if (getType() == FieldType.FLOOR) {
            return new Color(0.8, 0.8, 0.8, 1);
        } else if (getType() == FieldType.WALL) {
            return new Color(0.3, 0.3, 0.3, 1);
        } else if (getType() == FieldType.OBSTACLE) {
            return new Color(0.6, 0.5, 0.5, 1);
        } else if (getType() == FieldType.FOG) {
            return new Color(0.8, 0.8, 0.9, 1);
        } else {
            return Color.RED;
        }
    }

    public FieldType getType() {
        return type;
    }

    public void setType(final FieldType type) {
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public void setX(final int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(final int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Field field = (Field) o;

        return x == field.x && y == field.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    public String toString() {
        return "[" + x + "," + y + (type != null ? ":" + type : "") + "]";
    }
}
