package org.nerdizin.skirmish.game;

import org.nerdizin.skirmish.game.map.HighlightedFields;
import org.nerdizin.skirmish.game.map.Map;
import org.nerdizin.skirmish.game.map.MapFactory;
import org.nerdizin.skirmish.game.map.SelectedFields;
import org.nerdizin.skirmish.game.model.FighterBuilder;
import org.nerdizin.skirmish.game.model.Fighters;
import org.nerdizin.skirmish.game.model.Weapon;
import org.nerdizin.skirmish.ui.ControlPanel;
import org.nerdizin.skirmish.ui.KeyboardHandler;
import org.nerdizin.skirmish.game.map.MapHelper;
import javafx.scene.Group;

public class Game {

    private static final int GRID_SIZE = 50;

    private Player player;
    private Round round;
    private Map map;
    private SelectedFields selectedFields;
    private HighlightedFields highlightedFields;
    private Fighters fighters;
    private ControlPanel controlPanel;

    private Group root;
    private Group mapLayer;
    private Group controlPanelLayer;

    public Game() {
        map = MapFactory.createMapById(this, MapFactory.DEFAULT_MAP);
        round = new Round(this);
        player = new Player();
        player.addFighter(new FighterBuilder("Alphons", 4, 3)
                .weapon(new Weapon("Laspistol", 4, 1))
                .field(MapHelper.getRandomFieldThatDoesNotBlockMovement(map))
                .build());
        player.addFighter(new FighterBuilder("Berthold", 3, 4)
                .weapon(new Weapon("Bolter", 8, 2))
                .field(MapHelper.getRandomFieldThatDoesNotBlockMovement(map))
                .build());

        selectedFields = new SelectedFields(this);
        highlightedFields = new HighlightedFields(this);
        fighters = new Fighters(this);
        round.startNextRound();

        root = new Group();
        mapLayer = new Group();
        controlPanelLayer = new Group();
        root.getChildren().add(mapLayer);
        root.getChildren().add(controlPanelLayer);
        root.setOnKeyPressed(new KeyboardHandler(this));
    }

    public void init() {
        mapLayer.getChildren().add(getMap().createOrUpdateNode());
        mapLayer.getChildren().add(getFighters().createOrUpdateNode());
        mapLayer.getChildren().add(getHighlightedFields().createOrUpdateNode());
        mapLayer.getChildren().add(getSelectedFields().createOrUpdateNode());
        controlPanelLayer.getChildren().add(getControlPanel().createOrUpdateNode());
    }

    public void updateMap() {
        mapLayer.getChildren().clear();
        selectedFields.reset();
        highlightedFields.reset();
        mapLayer.getChildren().add(getMap().createOrUpdateNode());
        mapLayer.getChildren().add(getFighters().createOrUpdateNode());
        mapLayer.getChildren().add(getHighlightedFields().createOrUpdateNode());
        mapLayer.getChildren().add(getSelectedFields().createOrUpdateNode());
    }

    public Group getGroup() {
        return root;
    }

    public Player getPlayer() {
        return player;
    }

    public Round getRound() {
        return round;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(final Map map) {
        this.map = map;
    }

    public void initControlPanel(int x, int y) {
        controlPanel = new ControlPanel(this, x, y);
    }

    public int getGridSize() {
        return GRID_SIZE;
    }

    public SelectedFields getSelectedFields() {
        return selectedFields;
    }

    public HighlightedFields getHighlightedFields() {
        return highlightedFields;
    }

    public Fighters getFighters() {
        return fighters;
    }

    public ControlPanel getControlPanel() {
        return controlPanel;
    }

    public String toString() {
        return map.toString();
    }
}
