package com.itechart.studlab.app.domain.enumeration;

/**
 * The ActType enumeration.
 */
public enum ActType {
    THEFT("THEFT"),
    INCONSISTENCY("INCONSISTENCY"),
    LOSS("LOSS"),
    WRITE_OFF("WRITE_OFF");

    String name;

    ActType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
