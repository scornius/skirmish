package org.nerdizin.skirmish.game;

import org.nerdizin.skirmish.game.model.Fighter;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Round {

    private IntegerProperty roundNumber;
    private Game game;

    public Round(final Game game) {
        this.game = game;
        roundNumber = new SimpleIntegerProperty(0);
    }

    public void startNextRound() {
        roundNumber.set(roundNumber.get() + 1);
        for(final Fighter fighter : game.getPlayer().getFighters()) {
            fighter.handleStartOfTurn(game);
        }
    }

    public int getRoundNumber() {
        return roundNumber.get();
    }

    public IntegerProperty getRoundNumberProperty() {
        return roundNumber;
    }
}
