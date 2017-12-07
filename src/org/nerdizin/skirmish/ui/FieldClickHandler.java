package org.nerdizin.skirmish.ui;

import org.nerdizin.skirmish.game.map.Field;
import org.nerdizin.skirmish.game.Game;
import org.nerdizin.skirmish.game.map.SelectedField;
import org.nerdizin.skirmish.game.map.SelectedSourceField;
import org.nerdizin.skirmish.game.map.SelectedTargetField;
import org.nerdizin.skirmish.game.map.MapHelper;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class FieldClickHandler implements EventHandler<MouseEvent> {

    private Game game;
    private Field field;

    public FieldClickHandler(final Game game, final Field field) {
        this.game = game;
        this.field = field;
    }

    @Override
    public void handle(final MouseEvent mouseEvent) {

        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            SelectedField previousSelectedField = game.getSelectedFields().getSelectedTargetField();

            if (previousSelectedField == null) {
                previousSelectedField = new SelectedTargetField(game, field);
                game.getSelectedFields().setSelectedTargetField(previousSelectedField);
            } else {
                if (previousSelectedField.getField().equals(field)) {
                    game.getSelectedFields().setSelectedTargetField(null);
                }
                previousSelectedField.setField(field);
            }

            game.getSelectedFields().createOrUpdateNode();

        } else if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
            SelectedField previousSelectedField = game.getSelectedFields().getSelectedSourceField();

            if (previousSelectedField == null) {
                previousSelectedField = new SelectedSourceField(game, field);
                game.getSelectedFields().setSelectedSourceField(previousSelectedField);
            } else {
                if (previousSelectedField.getField().equals(field)) {
                    game.getSelectedFields().setSelectedSourceField(null);
                }
                previousSelectedField.setField(field);
            }

            game.getControlPanel().getFighterPanel().setFighter(MapHelper.getFighter(game, field));

            game.getSelectedFields().createOrUpdateNode();
        }

    }

}
