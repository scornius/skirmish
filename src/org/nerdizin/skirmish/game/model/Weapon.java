package org.nerdizin.skirmish.game.model;

public class Weapon {

    private String id;
    private int range;
    private int damage;

    public Weapon(final String id, final int range, final int damage) {
        this.id = id;
        this.range = range;
        this.damage = damage;
    }

    public int getRange() {
        return range;
    }

    public void setRange(final int range) {
        this.range = range;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(final int damage) {
        this.damage = damage;
    }

    public String getId() {
        return id;
    }
}
