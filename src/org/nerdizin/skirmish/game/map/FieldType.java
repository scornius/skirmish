package org.nerdizin.skirmish.game.map;

public enum FieldType {

    WALL('#', true, true),
    FLOOR('.', false, false),
    OBSTACLE('o', true, false),
    FOG('f', false, true);

    private char symbol;
    private boolean blocksLineOfSight;
    private boolean blocksMovement;

    FieldType(final char symbol, final boolean blocksMovement, final boolean blocksLineOfSight) {
        this.symbol = symbol;
        this.blocksMovement = blocksMovement;
        this.blocksLineOfSight = blocksLineOfSight;
    }

    public static FieldType getFieldTypeBySymbol(final char symbol) {
        for(final FieldType fieldType : FieldType.values()) {
            if (fieldType.getSymbol() == symbol) {
                return fieldType;
            }
        }
        throw new IllegalArgumentException("No FieldType found for symbol " + symbol);
    }

    public boolean isBlocksLineOfSight() {
        return blocksLineOfSight;
    }

    public boolean isBlocksMovement() {
        return blocksMovement;
    }

    public char getSymbol() {
        return symbol;
    }
}
