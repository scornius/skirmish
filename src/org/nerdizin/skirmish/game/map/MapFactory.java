package org.nerdizin.skirmish.game.map;

import org.nerdizin.skirmish.game.Game;
import org.nerdizin.skirmish.util.Dice;

public class MapFactory {

    public final static String[] MAPS = {
            "Random",
            "Map 1",
            "Map 2"
    };

    public final static String DEFAULT_MAP = MAPS[1];

    public static Map createRandomMap(final Game game, int width, int height) {
        final Map map = new Map(game, width, height);
        for(int x=0; x<width; x++) {
            for(int y=0; y<height; y++) {
                int roll = Dice.roll(4);
                if (roll == 1) {
                    map.setField(x, y, new Field(FieldType.FLOOR, x, y));
                } else if (roll == 2) {
                    map.setField(x, y, new Field(FieldType.WALL, x, y));
                } else if (roll == 3) {
                    map.setField(x, y, new Field(FieldType.OBSTACLE, x, y));
                } else if (roll == 4) {
                    map.setField(x, y, new Field(FieldType.FOG, x, y));
                }
            }
        }
        return map;
    }

    public static Map createMapById(final Game game, final String id) {

        final String[] map;
        if (MAPS[0].equals(id)) {
            return createRandomMap(game, 10, 10);
        } else if (MAPS[1].equals(id)) {
            map = MapRepository.MAP_1;
        } else if (MAPS[2].equals(id)) {
            map = MapRepository.MAP_2;
        } else {
            throw new IllegalArgumentException("No map known for id " + id);
        }

        final Map result = new Map(game, map[0].length(), map.length);

        for(int y=0; y<map.length; y++) {
            for (int x=0; x<map[y].length(); x++) {
                final Field field = new Field(FieldType.getFieldTypeBySymbol(map[y].charAt(x)), x, y);
                result.setField(x, y, field);
            }
        }

        return result;
    }
}
