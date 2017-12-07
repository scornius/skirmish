package org.nerdizin.skirmish.game.map;

public enum Direction {

    NORTH(0),
    NORTH_EAST(45),
    EAST(90),
    SOUTH_EAST(135),
    SOUTH(180),
    SOUTH_WEST(225),
    WEST(270),
    NORTH_WEST(315);

    private int rotation;

    Direction(final int rotation) {
        this.rotation = rotation;
    }

    public int getRotation() {
        return rotation;
    }

    public static Direction rotateClockwise(final Direction direction) {
        switch (direction) {
            case NORTH : return EAST;
            case EAST : return SOUTH;
            case SOUTH : return WEST;
            case WEST : return NORTH;
            default: return direction;
        }
    }

    public static Direction rotateCounterClockwise(final Direction direction) {
        switch (direction) {
            case NORTH : return WEST;
            case WEST : return SOUTH;
            case SOUTH : return EAST;
            case EAST : return NORTH;
            default: return direction;
        }
    }

    public static Direction rotateClockwise8(final Direction direction) {
        switch (direction) {
            case NORTH : return NORTH_EAST;
            case NORTH_EAST: return EAST;
            case EAST : return SOUTH_EAST;
            case SOUTH_EAST: return SOUTH;
            case SOUTH : return SOUTH_WEST;
            case SOUTH_WEST : return WEST;
            case WEST : return NORTH_WEST;
            case NORTH_WEST : return NORTH;
            default: return direction;
        }
    }

    public static Direction rotateCounterClockwise8(final Direction direction) {
        switch (direction) {
            case NORTH : return NORTH_WEST;
            case NORTH_WEST: return WEST;
            case WEST : return SOUTH_WEST;
            case SOUTH_WEST: return SOUTH;
            case SOUTH : return SOUTH_EAST;
            case SOUTH_EAST : return EAST;
            case EAST : return NORTH_EAST;
            case NORTH_EAST : return NORTH;
            default: return direction;
        }
    }
}
