package org.nerdizin.skirmish.game.model;

import org.nerdizin.skirmish.game.Game;
import javafx.scene.Group;
import javafx.scene.Node;

public class Fighters {

    private Game game;
    private Group fighters = new Group();

    public Fighters(final Game game) {
        this.game = game;
    }

    public Node createOrUpdateNode() {
        fighters.getChildren().clear();

        for(final Fighter fighter : game.getPlayer().getFighters()) {
            fighters.getChildren().add(fighter.createNode(game));
        }

        return fighters;
    }

}
