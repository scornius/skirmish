package org.nerdizin.skirmish.game.map;

import org.nerdizin.skirmish.game.Game;
import javafx.scene.Group;
import javafx.scene.Node;

public class SelectedFields {

    private Game game;
    private SelectedField selectedSourceField;
    private SelectedField selectedTargetField;
    private Group selectedFields = new Group();

    public SelectedFields(final Game game) {
        this.game = game;
    }

    public Node createOrUpdateNode() {
        selectedFields.getChildren().clear();
        if (selectedSourceField != null) {
            selectedFields.getChildren().add(selectedSourceField.createOrUpdateNode());
        }
        if (selectedTargetField != null) {
            selectedFields.getChildren().add(selectedTargetField.createOrUpdateNode());
        }
        return selectedFields;
    }

    public void reset() {
        selectedSourceField = null;
        selectedTargetField = null;
    }

    public SelectedField getSelectedSourceField() {
        return selectedSourceField;
    }

    public void setSelectedSourceField(final SelectedField selectedSourceField) {
        this.selectedSourceField = selectedSourceField;
    }

    public SelectedField getSelectedTargetField() {
        return selectedTargetField;
    }

    public void setSelectedTargetField(final SelectedField selectedTargetField) {
        this.selectedTargetField = selectedTargetField;
    }
}
