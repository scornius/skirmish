package org.nerdizin.skirmish.ui;

import org.nerdizin.skirmish.game.Game;
import org.nerdizin.skirmish.game.model.Fighter;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class FighterPanel {

    private Fighter fighter;
    private Label fighterId;
    private Label fighterAp;
    private Label fighterHp;
    private Label fighterWeapon;

    public FighterPanel() {
    }

    public Node createOrUpdateNode(final Game game) {
        final VBox vbox = new VBox();
        vbox.setPadding(new Insets(0));
        vbox.setSpacing(0);

        final GridPane gridPane = new GridPane();
        gridPane.setId("fighterGrid");
        fighterId = new Label();
        fighterAp = new Label();
        fighterHp = new Label();
        fighterWeapon = new Label();
        gridPane.add(new Label("Id:"), 1, 1);
        gridPane.add(fighterId, 2, 1);
        gridPane.add(new Label("Ap:"), 1, 2);
        gridPane.add(fighterAp, 2, 2);
        gridPane.add(new Label("Hp:"), 1, 3);
        gridPane.add(fighterHp, 2, 3);
        gridPane.add(new Label("Weapon:"), 1, 4);
        gridPane.add(fighterWeapon, 2, 4);

        updateFighter();

        vbox.getChildren().add(gridPane);

        return vbox;
    }

    private void updateFighter() {
        if (fighter != null) {
            fighterId.setText(fighter.getId());
            fighterWeapon.setText(fighter.getWeapon().getId()
                    + " [" + fighter.getWeapon().getRange() + "]");

            fighterAp.textProperty().bind(Bindings.convert(fighter.getActionPointsProperty()));
            fighterHp.textProperty().bind(Bindings.convert(fighter.getHitPointsProperty()));
        } else {
            fighterId.setText("n/a");
            fighterWeapon.setText("n/a");
        }
    }

    public void setFighter(final Fighter fighter) {
        this.fighter = fighter;
        updateFighter();
    }
}
