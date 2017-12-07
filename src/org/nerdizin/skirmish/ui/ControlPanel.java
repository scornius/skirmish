package org.nerdizin.skirmish.ui;

import org.nerdizin.skirmish.game.Game;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class ControlPanel {

    private Game game;
    private ActionPanel actionPanel;
    private FighterPanel fighterPanel;

    private int x;
    private int y;

    public ControlPanel(final Game game, final int x, final int y) {
        this.game = game;
        this.x = x;
        this.y = y;

        actionPanel = new ActionPanel();
        fighterPanel = new FighterPanel();
    }

    public Node createOrUpdateNode() {
        final VBox vbox = new VBox();
        vbox.setPadding(new Insets(0));
        vbox.setSpacing(0);
        vbox.setLayoutX(x);
        vbox.setLayoutY(y);

        vbox.getChildren().add(actionPanel.createOrUpdateNode(game));
        vbox.getChildren().add(fighterPanel.createOrUpdateNode(game));

        return vbox;
    }

    public ActionPanel getActionPanel() {
        return actionPanel;
    }

    public FighterPanel getFighterPanel() {
        return fighterPanel;
    }
}
