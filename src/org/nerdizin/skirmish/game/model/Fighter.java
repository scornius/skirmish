package org.nerdizin.skirmish.game.model;

import org.nerdizin.skirmish.game.Game;
import org.nerdizin.skirmish.game.map.Direction;
import org.nerdizin.skirmish.game.map.Field;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Fighter {

    private IntegerProperty actionPoints;
    private int initialActionPoints;
    private IntegerProperty hitPoints;
    private int initialHitPoints;
    private Weapon weapon;

    private String id;
    private IntegerProperty x;
    private IntegerProperty y;
    private SimpleObjectProperty<Direction> facing;

    public Fighter(final String id, final int initialActionPoints, final int initialHitPoints) {
        this.id = id;
        this.initialActionPoints = initialActionPoints;
        this.initialHitPoints = initialHitPoints;
        hitPoints = new SimpleIntegerProperty(initialHitPoints);
        actionPoints = new SimpleIntegerProperty();
        x = new SimpleIntegerProperty();
        y = new SimpleIntegerProperty();
        facing = new SimpleObjectProperty<>(Direction.EAST);
    }

    public Node createNode(final Game game) {

        final ImageView imageView = new ImageView(new Image("resources/images/marine.png"));
        imageView.setMouseTransparent(true);
        imageView.rotateProperty().bind(new IntegerBinding() {
                {
                    bind(facing);
                }
                @Override
                protected int computeValue() {
                    return facing.get().getRotation();
                }
            }
        );
        imageView.xProperty().bind(new IntegerBinding() {
               {
                   bind(x);
               }
               @Override
               protected int computeValue() {
                   return x.get() * game.getGridSize();
               }
           }
        );
        imageView.yProperty().bind(new IntegerBinding() {
               {
                   bind(y);
               }
               @Override
               protected int computeValue() {
                   return y.get() * game.getGridSize();
               }
           }
        );

        return imageView;
    }

    public void handleStartOfTurn(final Game game) {
        actionPoints.setValue(initialActionPoints);
    }

    public List<Action> getAffordableActions() {
        final List<Action> result = new ArrayList<>();
        for(final Action action : Action.values()) {
            if (actionPoints.get() >= action.getActionPoints()) {
                result.add(action);
            }
        }
        return result;
    }

    public void move(final Field targetField) {
        setX(targetField.getX());
        setY(targetField.getY());
        actionPoints.setValue(actionPoints.get() - Action.MOVE.getActionPoints());
    }

    public void attack(final Fighter target) {
        actionPoints.setValue(actionPoints.get() - Action.ATTACK.getActionPoints());
        target.dealDamage(getWeapon().getDamage());
    }

    public boolean canAffordAction(final Action action) {
        return actionPoints.get() >= action.getActionPoints();
    }

    public int getHitPoints() {
        return hitPoints.get();
    }

    public void dealDamage(final int damage) {
        hitPoints.set(hitPoints.get() - damage);
    }

    public void heal(final int healing) {
        hitPoints.set(hitPoints.get() + healing);
        if (hitPoints.get() > initialHitPoints) {
            hitPoints.set(initialHitPoints);
        }
    }

    public void rotate(final boolean clockwise) {
        actionPoints.setValue(actionPoints.get() - Action.ROTATE.getActionPoints());
        if (clockwise) {
            setFacing(Direction.rotateClockwise(getFacing()));
        } else {
            setFacing(Direction.rotateCounterClockwise(getFacing()));
        }
    }

    public int getActionPoints() {
        return actionPoints.get();
    }

    public void setActionPoints(final int actionPoints) {
        this.actionPoints.set(actionPoints);
    }

    public int getX() {
        return x.get();
    }

    public void setX(final int x) {
        this.x.set(x);
    }

    public int getY() {
        return y.get();
    }

    public void setY(final int y) {
        this.y.set(y);
    }

    public void setField(final Field field) {
        setX(field.getX());
        setY(field.getY());
    }

    public Direction getFacing() {
        return facing.get();
    }

    public void setFacing(final Direction facing) {
        this.facing.set(facing);
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(final Weapon weapon) {
        this.weapon = weapon;
    }

    public IntegerProperty getActionPointsProperty() {
        return actionPoints;
    }

    public IntegerProperty getHitPointsProperty() {
        return hitPoints;
    }
}
