package org.nerdizin.skirmish.game.map;

import org.nerdizin.skirmish.game.Game;
import javafx.scene.shape.Rectangle;

public class SelectedSourceField extends SelectedField {

    public SelectedSourceField(final Game game, final Field field) {
        super(game, field);
    }

    @Override
    protected void setStyleId(Rectangle rect) {
        rect.setId("selectedSourceField");
    }
}
