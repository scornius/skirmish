package org.nerdizin.skirmish.game.map;

import org.nerdizin.skirmish.game.Game;
import org.nerdizin.skirmish.game.map.path.AStar;
import org.nerdizin.skirmish.game.model.Fighter;
import org.nerdizin.skirmish.util.Dice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class MapHelper {

    public static LineOfSightResult getLineOfSight(final Field sourceField, final Field targetField, final Map map) {

        final List<Field> result = new ArrayList<>();

        int i;                          // loop counter
        int yStep, xStep;               // the step on y and x axis
        int error;                      // the error accumulated during the increment
        int errorPrev;                  // vision the previous value of the error variable
        int x = sourceField.getX();     // x
        int y = sourceField.getY();     // y
        int ddy, ddx;                   // compulsory variables: the double values of dy and dx
        int dx = targetField.getX() - sourceField.getX();
        int dy = targetField.getY() - sourceField.getY();

        // first point
        boolean lineOfSightBlocked = addFieldsAndCheckIfLineOfSightIsBlocked(result, sourceField);

        if (dy < 0) {
            yStep = -1;
            dy = -dy;
        } else {
            yStep = 1;
        }
        if (dx < 0){
            xStep = -1;
            dx = -dx;
        } else {
            xStep = 1;
        }

        ddy = 2 * dy;
        ddx = 2 * dx;

        if (ddx >= ddy){  // first octant (0 <= slope <= 1)
            // compulsory initialization (even for errorPrev, needed when dx==dy)
            errorPrev = error = dx;  // start in the middle of the square
            for (i=0 ; i < dx ; i++){  // do not use the first point (already done)
                x += xStep;
                error += ddy;
                if (error > ddx){  // increment y if AFTER the middle ( > )
                    y += yStep;
                    error -= ddx;
                    // three cases (octant == right->right-top for directions below):
                    if (error + errorPrev < ddx) {
                        // bottom square also
                        lineOfSightBlocked |= addFieldsAndCheckIfLineOfSightIsBlocked(result, map.getField(x, y-yStep));
                    } else if (error + errorPrev > ddx) {
                        // left square also
                        lineOfSightBlocked |= addFieldsAndCheckIfLineOfSightIsBlocked(result, map.getField(x-xStep, y));
                    } else {
                        // corner: bottom and left squares also
                        lineOfSightBlocked |= addFieldsAndCheckIfLineOfSightIsBlocked(
                                result, map.getField(x, y-yStep), map.getField(x-xStep, y)
                        );
                    }
                }
                lineOfSightBlocked |= addFieldsAndCheckIfLineOfSightIsBlocked(result, map.getField(x, y));
                errorPrev = error;
            }
        } else {  // the same as above
            errorPrev = error = dy;
            for (i=0 ; i < dy ; i++){
                y += yStep;
                error += ddx;
                if (error > ddy){
                    x += xStep;
                    error -= ddy;
                    if (error + errorPrev < ddy) {
                        lineOfSightBlocked |= addFieldsAndCheckIfLineOfSightIsBlocked(result, map.getField(x-xStep, y));
                    } else if (error + errorPrev > ddy) {
                        lineOfSightBlocked |= addFieldsAndCheckIfLineOfSightIsBlocked(result, map.getField(x, y-yStep));
                    } else{
                        lineOfSightBlocked |= addFieldsAndCheckIfLineOfSightIsBlocked(
                                result, map.getField(x-xStep, y), map.getField(x, y-yStep)
                        );
                    }
                }
                lineOfSightBlocked |= addFieldsAndCheckIfLineOfSightIsBlocked(result, map.getField(x, y));
                errorPrev = error;
            }
        }

        return new LineOfSightResult(result, lineOfSightBlocked);
    }

    private static boolean addFieldsAndCheckIfLineOfSightIsBlocked(final List<Field> result, final Field... fields) {

        Collections.addAll(result, fields);

        // line of sight is only blocked if all fields block (corner case)
        for(final Field field : fields) {
            if (!field.getType().isBlocksLineOfSight()) {
                return false;
            }
        }

        return true;
    }

    public static int getDistance(final Field sourceField, final Field targetField) {
        if (sourceField == null || targetField == null) {
            return 0;
        }

        int xDistance = Math.abs(sourceField.getX() - targetField.getX());
        int yDistance = Math.abs(sourceField.getY() - targetField.getY());

        return Math.max(xDistance, yDistance);
    }

    public static List<Field> getNeighborFields(final Field field, final Map map) {
        return getNeighborFields(field.getX(), field.getY(), map);
    }

    private static List<Field> getNeighborFields(final int x, final int y, final Map map) {

        final List<Field> result = new ArrayList<>();

        if (y>0) {
            if (x>0) {
                if (!map.getField(x-1, y).getType().isBlocksMovement()
                        || !map.getField(x, y-1).getType().isBlocksMovement()) {
                    addFieldIfMovementIsNotBlocked(result, map.getField(x-1, y-1));
                }
            }
            addFieldIfMovementIsNotBlocked(result, map.getField(x, y - 1));
            if (x<map.getWidth()-1) {
                if (!map.getField(x+1, y).getType().isBlocksMovement()
                        || !map.getField(x, y-1).getType().isBlocksMovement()) {
                    addFieldIfMovementIsNotBlocked(result, map.getField(x+1, y-1));
                }
            }
        }

        if (x>0) {
            addFieldIfMovementIsNotBlocked(result, map.getField(x-1, y));
        }
        if (x<map.getWidth()-1) {
            addFieldIfMovementIsNotBlocked(result, map.getField(x+1, y));
        }

        if (y<map.getHeight()-1) {
            if (x>0) {
                if (!map.getField(x-1, y).getType().isBlocksMovement()
                        || !map.getField(x, y+1).getType().isBlocksMovement()) {
                    addFieldIfMovementIsNotBlocked(result, map.getField(x-1, y+1));
                }
            }
            addFieldIfMovementIsNotBlocked(result, map.getField(x, y+1));
            if (x<map.getWidth()-1) {
                if (!map.getField(x+1, y).getType().isBlocksMovement()
                        || !map.getField(x, y+1).getType().isBlocksMovement()) {
                    addFieldIfMovementIsNotBlocked(result, map.getField(x+1, y+1));
                }
            }
        }

        return result;
    }

    private static void addFieldIfMovementIsNotBlocked(final List<Field> result, final Field field) {
        if (!field.getType().isBlocksMovement()) {
            result.add(field);
        }
    }

    public static List<Field> filterOutFieldsThatBlockMovement(final List<Field> fields) {
        final List<Field> result = new ArrayList<>(fields);
        final Iterator<Field> it = result.iterator();
        while(it.hasNext()) {
            if (it.next().getType().isBlocksMovement()) {
                it.remove();
            }
        }
        return result;
    }

    public static List<Field> filterOutFieldsThatBlockLineOfSight(final List<Field> fields) {
        final List<Field> result = new ArrayList<>(fields);
        final Iterator<Field> it = result.iterator();
        while(it.hasNext()) {
            if (it.next().getType().isBlocksLineOfSight()) {
                it.remove();
            }
        }
        return result;
    }

    public static Field getRandomFieldThatDoesNotBlockMovement(final Map map) {
        final List<Field> eligibleFields = new ArrayList<>();
        for(final Field[] row : map.getFields()) {
            for(final Field field : row) {
                if (!field.getType().isBlocksMovement()) {
                    eligibleFields.add(field);
                }
            }
        }
        return eligibleFields.get(Dice.roll(eligibleFields.size()) - 1);
    }

    public static boolean canMoveInDirection(final Field field, final Map map, final Direction direction) {
        switch(direction) {
            case NORTH: {
                return field.getY() > 0
                        && !map.getField(field.getX(), field.getY() - 1).getType().isBlocksMovement();
            }
            case NORTH_EAST: {
                return field.getY() > 0
                        && field.getX() < map.getWidth() - 1
                        && !map.getField(field.getX() + 1, field.getY() - 1).getType().isBlocksMovement()
                        && ! ( // fields in between
                            map.getField(field.getX() + 1, field.getY()).getType().isBlocksMovement()
                                && map.getField(field.getX(), field.getY() - 1).getType().isBlocksMovement()
                );
            }
            case EAST: {
                return field.getX() < map.getWidth() - 1
                        && !map.getField(field.getX() + 1, field.getY()).getType().isBlocksMovement();
            }
            case SOUTH_EAST: {
                return field.getY() < map.getHeight() - 1
                        && field.getX() < map.getWidth() - 1
                        && !map.getField(field.getX() + 1, field.getY() + 1).getType().isBlocksMovement()
                        && ! ( // fields in between
                            map.getField(field.getX() + 1, field.getY()).getType().isBlocksMovement()
                                && map.getField(field.getX(), field.getY() + 1).getType().isBlocksMovement()
                );
            }
            case SOUTH: {
                return field.getY() < map.getHeight() - 1
                        && !map.getField(field.getX(), field.getY() + 1).getType().isBlocksMovement();
            }
            case SOUTH_WEST: {
                return field.getY() > 0
                        && field.getX() > 0
                        && !map.getField(field.getX() - 1, field.getY() + 1).getType().isBlocksMovement()
                        && ! ( // fields in between
                        map.getField(field.getX() - 1, field.getY()).getType().isBlocksMovement()
                                && map.getField(field.getX(), field.getY() + 1).getType().isBlocksMovement()
                );
            }
            case WEST: {
                return field.getX() > 0
                        && !map.getField(field.getX() - 1, field.getY()).getType().isBlocksMovement();
            }
            case NORTH_WEST: {
                return field.getY() > 0
                        && field.getX() > 0
                        && !map.getField(field.getX() - 1, field.getY() - 1).getType().isBlocksMovement()
                        && ! ( // fields in between
                        map.getField(field.getX() - 1, field.getY()).getType().isBlocksMovement()
                                && map.getField(field.getX(), field.getY() - 1).getType().isBlocksMovement()
                );
            }
            default: throw new IllegalArgumentException("Unknown direction: " + direction);
        }
    }

    public static Field getFieldInDirection(final Field sourceField, final Map map, final Direction direction) {
        switch(direction) {
            case NORTH: {
                if (sourceField.getY() > 0) {
                    return map.getField(sourceField.getX(), sourceField.getY() - 1);
                }
            }
            case NORTH_EAST: {
                if (sourceField.getX() < map.getWidth() - 1 && sourceField.getY() > 0) {
                    return map.getField(sourceField.getX() + 1, sourceField.getY() - 1);
                }
            }
            case EAST: {
                if (sourceField.getX() < map.getWidth() - 1) {
                    return map.getField(sourceField.getX() + 1, sourceField.getY());
                }
            }
            case SOUTH_EAST: {
                if (sourceField.getX() < map.getWidth() - 1 && sourceField.getY() < map.getHeight() - 1) {
                    return map.getField(sourceField.getX() + 1, sourceField.getY() + 1);
                }
            }
            case SOUTH: {
                if (sourceField.getY() < map.getHeight() - 1) {
                    return map.getField(sourceField.getX(), sourceField.getY() + 1);
                }
            }
            case SOUTH_WEST: {
                if (sourceField.getX() > 0 && sourceField.getY() < map.getHeight() - 1) {
                    return map.getField(sourceField.getX() - 1, sourceField.getY() + 1);
                }
            }
            case WEST: {
                if (sourceField.getX() > 0) {
                    return map.getField(sourceField.getX() - 1, sourceField.getY());
                }
            }
            case NORTH_WEST: {
                if (sourceField.getX() > 0 && sourceField.getY() > 0) {
                    return map.getField(sourceField.getX() - 1, sourceField.getY() - 1);
                }
            }
            default: return null;
        }
    }

    public static Direction getDirectionOfTargetField(final Field sourceField, final Field targetField) {

        if (targetField.getY() < sourceField.getY()
                && targetField.getX() == sourceField.getX()) {
            return Direction.NORTH;
        } else if (targetField.getY() < sourceField.getY()
                && targetField.getX() > sourceField.getX()) {
            return Direction.NORTH_EAST;
        } else if (targetField.getY() == sourceField.getY()
                && targetField.getX() > sourceField.getX()) {
            return Direction.EAST;
        } else if (targetField.getY() > sourceField.getY()
                && targetField.getX() > sourceField.getX()) {
            return Direction.SOUTH_EAST;
        } else if (targetField.getY() > sourceField.getY()
                && targetField.getX() == sourceField.getX()) {
            return Direction.SOUTH;
        } else if (targetField.getY() > sourceField.getY()
                && targetField.getX() < sourceField.getX()) {
            return Direction.SOUTH_WEST;
        } else if (targetField.getY() == sourceField.getY()
                && targetField.getX() < sourceField.getX()) {
            return Direction.WEST;
        } else if (targetField.getY() < sourceField.getY()
                && targetField.getX() < sourceField.getX()) {
            return Direction.NORTH_WEST;
        } else {
            throw new IllegalStateException("Could not determine direction of target field.");
        }
    }

    public static List<Field> findPath(final Field sourceField, final Field targetField, final Map map) {
        return new AStar(map).calcShortestPath(sourceField, targetField);
    }

    public static Fighter getFighter(final Game game, final Field field) {
        for(final Fighter fighter : game.getPlayer().getFighters()) {
            if (fighter.getX() == field.getX() && fighter.getY() == field.getY()) {
                return fighter;
            }
        }
        return null;
    }
}
