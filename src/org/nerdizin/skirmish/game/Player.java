package org.nerdizin.skirmish.game;

import org.nerdizin.skirmish.game.model.Fighter;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private List<Fighter> fighters = new ArrayList<>();

    public void addFighter(final Fighter fighter) {
        fighters.add(fighter);
    }

    public List<Fighter> getFighters() {
        return fighters;
    }

    public void setFighters(final List<Fighter> fighters) {
        this.fighters = fighters;
    }
}
