package org.nerdizin.skirmish.ui;

import org.nerdizin.skirmish.game.Game;
import org.nerdizin.skirmish.game.map.Direction;
import org.nerdizin.skirmish.game.map.Field;
import org.nerdizin.skirmish.game.map.MapHelper;
import org.nerdizin.skirmish.game.model.Action;
import org.nerdizin.skirmish.util.Messages;

public class MovementHelper {

    public static void move(final Game game, final Direction direction) {

        final Field sourceField = game.getSelectedFields().getSelectedSourceField().getField();
        final Field targetField;
        if (direction != null) {
            targetField = MapHelper.getFieldInDirection(
                    sourceField,
                    game.getMap(),
                    direction);
        } else {
            targetField = game.getSelectedFields().getSelectedTargetField().getField();
        }

        if (sourceField == null || targetField == null) {
            return;
        }

        final org.nerdizin.skirmish.game.model.Fighter fighter = MapHelper.getFighter(game,
                sourceField);

        if (fighter == null) {
            Messages.send("No fighter at source field.");
            return;
        }

        if (!MapHelper.canMoveInDirection(
                sourceField,
                game.getMap(),
                MapHelper.getDirectionOfTargetField(
                        sourceField,
                        targetField
                ))) {
            Messages.send("Way to target field is blocked.");
            return;
        }

        if (MapHelper.getDistance(sourceField, targetField) > 1) {
            Messages.send("Target field is too far away.");
            return;
        }

        if (! fighter.canAffordAction(Action.MOVE)) {
            Messages.send("Fighter has not enough AP to move.");
            return;
        }

        fighter.move(targetField);
        game.getSelectedFields().getSelectedSourceField().setField(targetField);
    }
}
