package org.nerdizin.skirmish.game.model;

import org.nerdizin.skirmish.game.map.Field;

public class FighterBuilder {

    private Fighter fighter;

    public FighterBuilder(final String id, final int initialActionPoints, final int initialHitPoints) {
        fighter = new Fighter(id, initialActionPoints, initialHitPoints);
    }

    public FighterBuilder weapon(final Weapon weapon) {
        fighter.setWeapon(weapon);
        return this;
    }

    public FighterBuilder field(final Field field) {
        fighter.setField(field);
        return this;
    }

    public FighterBuilder x(final int x) {
        fighter.setX(x);
        return this;
    }

    public FighterBuilder y(final int y) {
        fighter.setY(y);
        return this;
    }

    public Fighter build() {
        return fighter;
    }

}
