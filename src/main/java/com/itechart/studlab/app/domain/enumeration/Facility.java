package com.itechart.studlab.app.domain.enumeration;

/**
 * The Facility enumeration.
 */
public enum Facility {
    REFRIGERATOR("REFRIGERATOR"),
    OPEN_SPACE("OPEN_SPACE"),
    HEATED_SPACE("HEATED_SPACE"),
    ORDINARY_ROOM("ORDINARY_ROOM");

    String name;

    Facility(String name) {
        this.name = name;

    }

    public String getName() {
        return name;
    }
}
