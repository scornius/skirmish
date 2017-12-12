package org.nerdizin.skirmish.ui;

import org.nerdizin.skirmish.game.Game;
import org.nerdizin.skirmish.game.map.Direction;
import org.nerdizin.skirmish.game.map.Field;
import org.nerdizin.skirmish.game.model.Action;
import org.nerdizin.skirmish.game.map.MapHelper;
import org.nerdizin.skirmish.util.Messages;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import org.nerdizin.skirmish.game.model.Fighter;

public class KeyboardHandler implements EventHandler<KeyEvent> {

    private Game game;

    public KeyboardHandler(final Game game) {
        this.game = game;
    }

    @Override
    public void handle(final KeyEvent keyEvent) {
        switch(keyEvent.getCode()) {
            case NUMPAD8: move(Direction.NORTH); break;
            case NUMPAD9: move(Direction.NORTH_EAST); break;
            case NUMPAD6: move(Direction.EAST); break;
            case NUMPAD3: move(Direction.SOUTH_EAST); break;
            case NUMPAD2: move(Direction.SOUTH); break;
            case NUMPAD1: move(Direction.SOUTH_WEST); break;
            case NUMPAD4: move(Direction.WEST); break;
            case NUMPAD7: move(Direction.NORTH_WEST); break;
        }
    }

    private void move(final Direction direction) {
        MovementHelper.move(game, direction);
    }
}
