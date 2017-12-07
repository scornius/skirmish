package org.nerdizin.skirmish.game.map;

import java.util.List;

public class LineOfSightResult {

    private List<Field> fields;
    private boolean lineOfSightBlocked;

    public LineOfSightResult(final List<Field> fields, final boolean lineOfSightBlocked) {
        this.fields = fields;
        this.lineOfSightBlocked = lineOfSightBlocked;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(final List<Field> fields) {
        this.fields = fields;
    }

    public boolean isLineOfSightBlocked() {
        return lineOfSightBlocked;
    }

    public void setLineOfSightBlocked(final boolean lineOfSightBlocked) {
        this.lineOfSightBlocked = lineOfSightBlocked;
    }
}
