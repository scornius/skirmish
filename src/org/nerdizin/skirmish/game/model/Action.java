package org.nerdizin.skirmish.game.model;

public enum Action {

    MOVE(1),
    ATTACK(1),
    ROTATE(0);

    private int actionPoints;

    Action(final int actionPoints) {
        this.actionPoints = actionPoints;
    }

    public int getActionPoints() {
        return actionPoints;
    }
}
