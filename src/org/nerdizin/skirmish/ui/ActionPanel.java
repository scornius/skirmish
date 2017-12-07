package org.nerdizin.skirmish.ui;

import org.nerdizin.skirmish.game.Game;
import org.nerdizin.skirmish.game.map.Field;
import org.nerdizin.skirmish.game.map.LineOfSightResult;
import org.nerdizin.skirmish.game.map.MapFactory;
import org.nerdizin.skirmish.game.map.MapHelper;
import org.nerdizin.skirmish.game.model.Action;
import org.nerdizin.skirmish.game.model.Fighter;
import org.nerdizin.skirmish.util.Messages;
import org.nerdizin.skirmish.util.SoundFx;
import javafx.beans.binding.StringBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.List;

public class ActionPanel {

    public ActionPanel() {
    }

    public Node createOrUpdateNode(final Game game) {
        final GridPane gridPane = new GridPane();
        gridPane.setId("ActionPanel");

        gridPane.add(createMapSelection(game), 1, 1, 2, 1);
        gridPane.add(createDistanceButton(game), 1, 2, 2, 1);
        gridPane.add(createLineOfSightButton(game), 1, 3, 2, 1);
        gridPane.add(createNeighborFieldsButton(game), 1, 4, 2, 1);
        gridPane.add(createPathButton(game), 1, 5, 2, 1);
        gridPane.add(createRoundButton(game), 1, 6, 2, 1);
        gridPane.add(createMoveButton(game), 1, 7, 2, 1);
        gridPane.add(createAttackButton(game), 1, 8, 2, 1);
        gridPane.add(createFacingButton(game, false), 1, 9, 1, 1);
        gridPane.add(createFacingButton(game, true), 2, 9, 1, 1);
        gridPane.add(createSoundButton(game), 1, 10, 2, 1);
        gridPane.add(createDialogButtons(game), 1, 11, 2, 1);

        return gridPane;
    }

    private Node createMapSelection(final Game game) {
        final ChoiceBox<String> choiceBoxMap = new ChoiceBox<>(
                FXCollections.observableArrayList(MapFactory.MAPS)
        );
        choiceBoxMap.getSelectionModel().selectedIndexProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, final Number previousEntry, final Number selectedEntry) {
                        if (previousEntry.intValue() != -1) {
                            game.setMap(MapFactory.createMapById(game, MapFactory.MAPS[selectedEntry.intValue()]));
                            game.updateMap();
                        }
                    }
                }
        );
        choiceBoxMap.setTooltip(new Tooltip("Select the map"));
        choiceBoxMap.setValue(MapFactory.DEFAULT_MAP);

        return choiceBoxMap;
    }

    private Node createDistanceButton(final Game game) {
        final Button buttonDistance = new Button("Distance");
        buttonDistance.setPrefSize(150, 20);
        buttonDistance.setOnMouseClicked(mouseEvent -> {
            if (game.getSelectedFields().getSelectedSourceField() == null ||
                    game.getSelectedFields().getSelectedTargetField() == null) {
                return;
            }
            int distance = MapHelper.getDistance(
                    game.getSelectedFields().getSelectedSourceField().getField(),
                    game.getSelectedFields().getSelectedTargetField().getField());
            Messages.send("Distance: " + distance);
        });

        return buttonDistance;
    }

    private Node createLineOfSightButton(final Game game) {
        final Button buttonLineOfSight = new Button("Line of sight");
        buttonLineOfSight.setPrefSize(150, 20);
        buttonLineOfSight.setOnMouseClicked(mouseEvent -> {
            if (game.getSelectedFields().getSelectedSourceField() == null ||
                    game.getSelectedFields().getSelectedTargetField() == null) {
                return;
            }
            final LineOfSightResult lineOfSightResult = MapHelper.getLineOfSight(
                    game.getSelectedFields().getSelectedSourceField().getField(),
                    game.getSelectedFields().getSelectedTargetField().getField(),
                    game.getMap());
            Messages.send("Line of sight: " + lineOfSightResult.getFields());
            Messages.send("Blocked: " + lineOfSightResult.isLineOfSightBlocked());

            // highlight
            game.getHighlightedFields().setFields(lineOfSightResult.getFields());
            game.getHighlightedFields().createOrUpdateNode();
        });

        return buttonLineOfSight;
    }

    private Node createNeighborFieldsButton(final Game game) {
        final Button buttonNeighborFields = new Button("Neighbor fields");
        buttonNeighborFields.setPrefSize(150, 20);
        buttonNeighborFields.setOnMouseClicked(mouseEvent -> {
            if (game.getSelectedFields().getSelectedSourceField() == null) {
                return;
            }
            final List<Field> neighborFields = MapHelper.getNeighborFields(
                    game.getSelectedFields().getSelectedSourceField().getField(),
                    game.getMap());
            Messages.send("Neighbor fields: " + neighborFields);

            // highlight
            game.getHighlightedFields().setFields(neighborFields);
            game.getHighlightedFields().createOrUpdateNode();
        });

        return buttonNeighborFields;
    }

    private Node createPathButton(final Game game) {
        final Button buttonPathFields = new Button("Path");
        buttonPathFields.setPrefSize(150, 20);
        buttonPathFields.setOnMouseClicked(mouseEvent -> {
            if (game.getSelectedFields().getSelectedSourceField() == null ||
                    game.getSelectedFields().getSelectedTargetField() == null) {
                return;
            }
            final List<Field> pathFields = MapHelper.findPath(
                    game.getSelectedFields().getSelectedSourceField().getField(),
                    game.getSelectedFields().getSelectedTargetField().getField(),
                    game.getMap());
            Messages.send("Path fields: " + pathFields);

            // highlight
            game.getHighlightedFields().setFields(pathFields);
            game.getHighlightedFields().createOrUpdateNode();
        });

        return buttonPathFields;
    }

    private Node createMoveButton(final Game game) {
        final Button buttonMove = new Button("Move");
        buttonMove.setPrefSize(150, 20);
        buttonMove.setOnMouseClicked(mouseEvent -> {
            if (game.getSelectedFields().getSelectedSourceField() == null
                    || game.getSelectedFields().getSelectedTargetField() == null) {
                return;
            }

            final org.nerdizin.skirmish.game.model.Fighter fighter = MapHelper.getFighter(game,
                    game.getSelectedFields().getSelectedSourceField().getField());

            if (fighter == null) {
                Messages.send("No fighter at source field.");
                return;
            }

            if (!MapHelper.canMoveInDirection(
                    game.getSelectedFields().getSelectedSourceField().getField(),
                    game.getMap(),
                    MapHelper.getDirectionOfTargetField(
                            game.getSelectedFields().getSelectedSourceField().getField(),
                            game.getSelectedFields().getSelectedTargetField().getField()
                    ))) {
                Messages.send("Way to target field is blocked.");
                return;
            }

            if (MapHelper.getDistance(game.getSelectedFields().getSelectedSourceField().getField(),
                    game.getSelectedFields().getSelectedTargetField().getField()) > 1) {
                Messages.send("Target field is too far away.");
                return;
            }

            if (! fighter.canAffordAction(Action.MOVE)) {
                Messages.send("Fighter has not enough AP to move.");
                return;
            }

            fighter.move(game.getSelectedFields().getSelectedTargetField().getField());
        });

        return buttonMove;
    }

    private Node createRoundButton(final Game game) {
        final Button buttonRound = new Button();
        buttonRound.textProperty().bind(new StringBinding() {
                {
                    bind(game.getRound().getRoundNumberProperty());
                }

                @Override
                protected String computeValue() {
                    return "Round #" + game.getRound().getRoundNumberProperty().get();
                }
            }
        );

        buttonRound.setPrefSize(150, 20);
        buttonRound.setOnMouseClicked(mouseEvent -> game.getRound().startNextRound());

        return buttonRound;
    }

    private Node createAttackButton(final Game game) {
        final Button buttonAttack = new Button("Attack");
        buttonAttack.setPrefSize(150, 20);
        buttonAttack.setOnMouseClicked(mouseEvent -> {
            if (game.getSelectedFields().getSelectedSourceField() == null
                    || game.getSelectedFields().getSelectedTargetField() == null) {
                return;
            }

            final org.nerdizin.skirmish.game.model.Fighter fighter = MapHelper.getFighter(game,
                    game.getSelectedFields().getSelectedSourceField().getField());
            final org.nerdizin.skirmish.game.model.Fighter target = MapHelper.getFighter(game,
                    game.getSelectedFields().getSelectedTargetField().getField());

            if (fighter == null) {
                Messages.send("No fighter at source field.");
                return;
            }

            if (target == null) {
                Messages.send("No fighter at target field.");
                return;
            }

            if (!fighter.canAffordAction(Action.ATTACK)) {
                Messages.send("Fighter doesn't have enough AP to attack.");
                return;
            }

            final int distance = MapHelper.getDistance(game.getSelectedFields().getSelectedSourceField().getField(),
                    game.getSelectedFields().getSelectedTargetField().getField());
            if (fighter.getWeapon().getRange() < distance) {
                Messages.send("Target is out of range.");
                return;
            }

            final LineOfSightResult los = MapHelper.getLineOfSight(
                    game.getSelectedFields().getSelectedSourceField().getField(),
                    game.getSelectedFields().getSelectedTargetField().getField(), game.getMap());
            if (los.isLineOfSightBlocked()) {
                Messages.send("Line of sight to target is blocked.");
                return;
            }

            fighter.attack(target);
            Messages.send("BANG !");
        });

        return buttonAttack;
    }

    private Node createFacingButton(final Game game, final boolean clockwise) {
        final Button buttonFacing = new Button();
        if (clockwise) {
            buttonFacing.setText("->");
        } else {
            buttonFacing.setText("<-");
        }
        buttonFacing.setPrefSize(75, 20);
        buttonFacing.setOnMouseClicked(mouseEvent -> {
            if (game.getSelectedFields().getSelectedSourceField() == null) {
                return;
            }

            final Fighter fighter = MapHelper.getFighter(game,
                    game.getSelectedFields().getSelectedSourceField().getField());

            if (fighter == null) {
                Messages.send("No fighter at source field.");
                return;
            }

            if (!fighter.canAffordAction(Action.ROTATE)) {
                Messages.send("Fighter doesn't have enough AP to rotate.");
                return;
            }

            fighter.rotate(clockwise);
        });

        return buttonFacing;
    }

    private Node createSoundButton(final Game game) {
        final Button buttonSound = new Button();
        buttonSound.setText("Play sound");
        buttonSound.setPrefSize(150, 20);
        buttonSound.setOnMouseClicked(mouseEvent -> SoundFx.LASPISTOL.play());

        return buttonSound;
    }

    private Node createDialogButtons(final Game game) {
        final HBox hBox = new HBox();

        final Button buttonDialog = new Button();
        buttonDialog.setText("Info");
        buttonDialog.setPrefSize(50, 20);
        buttonDialog.setOnMouseClicked(mouseEvent -> Dialogs.showInformation("Laber"));
        hBox.getChildren().add(buttonDialog);

        final Button buttonConfirmation = new Button();
        buttonConfirmation.setText("Ok?");
        buttonConfirmation.setPrefSize(50, 20);
        buttonConfirmation.setOnMouseClicked(mouseEvent ->
                Messages.send("Result: " + Dialogs.showConfirmation("Ja oder Nein ?")));
        hBox.getChildren().add(buttonConfirmation);

        final Button buttonError = new Button();
        buttonError.setText("Error");
        buttonError.setPrefSize(50, 20);
        buttonError.setOnMouseClicked(mouseEvent -> {throw new RuntimeException("ARGH !");});
        hBox.getChildren().add(buttonError);

        return hBox;
    }
}
