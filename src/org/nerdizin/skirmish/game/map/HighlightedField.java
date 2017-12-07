package org.nerdizin.skirmish.game.map;

import org.nerdizin.skirmish.game.Game;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

public class HighlightedField {

    private Game game;
    private SimpleObjectProperty<Field> field;

    public HighlightedField(final Game game, final Field field) {
        this.game = game;
        this.field = new SimpleObjectProperty<>(field);
    }

    public Node createOrUpdateNode() {
        final Rectangle rect = new Rectangle();
        rect.setWidth(game.getGridSize());
        rect.setHeight(game.getGridSize());
        rect.setId("highlightedField");
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

        return rect;
    }

}
