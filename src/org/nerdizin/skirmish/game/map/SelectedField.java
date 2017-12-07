package org.nerdizin.skirmish.game.map;

import org.nerdizin.skirmish.game.Game;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class SelectedField {

    private Game game;
    private SimpleObjectProperty<Field> field;

    public SelectedField(final Game game, final Field field) {
        this.game = game;
        this.field = new SimpleObjectProperty<>(field);
    }

    public Node createOrUpdateNode() {
        final Rectangle rect = new Rectangle();
        rect.setWidth(game.getGridSize());
        rect.setHeight(game.getGridSize());
        rect.setFill(Color.TRANSPARENT);
        rect.setMouseTransparent(true);

        rect.xProperty().bind(new IntegerBinding() {
               {
                   bind(field);
               }
               @Override
               protected int computeValue() {
                   return field.get().getX() * game.getGridSize();
               }
           }
        );
        rect.yProperty().bind(new IntegerBinding() {
              {
                  bind(field);
              }
              @Override
              protected int computeValue() {
                  return field.get().getY() * game.getGridSize();
              }
          }
        );

        setStyleId(rect);
        return rect;
    }

    protected abstract void setStyleId(final Rectangle rect);

    public Field getField() {
        return field.get();
    }

    public void setField(final Field field) {
        this.field.set(field);
    }

}
