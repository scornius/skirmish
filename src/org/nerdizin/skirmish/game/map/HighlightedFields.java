package org.nerdizin.skirmish.game.map;

import org.nerdizin.skirmish.game.Game;
import javafx.scene.Group;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;

public class HighlightedFields {

    private Game game;
    private List<HighlightedField> highlightedFields = new ArrayList<>();
    private Group selectedFieldsGroup = new Group();

    public HighlightedFields(final Game game) {
        this.game = game;
    }

    public Node createOrUpdateNode() {
        selectedFieldsGroup.getChildren().clear();

        for(final HighlightedField highlightedField : highlightedFields) {
            selectedFieldsGroup.getChildren().add(highlightedField.createOrUpdateNode());
        }

        return selectedFieldsGroup;
    }

    public void reset() {
        highlightedFields.clear();
    }

    public void setFields(final List<Field> fields) {
        highlightedFields.clear();
        for (final Field field : fields) {
            highlightedFields.add(new HighlightedField(game, field));
        }
    }

    public void setHighlightedFields(final List<HighlightedField> highlightedFields) {
        this.highlightedFields = highlightedFields;
    }

    public List<HighlightedField> getHighlightedFields() {
        return highlightedFields;
    }
}
