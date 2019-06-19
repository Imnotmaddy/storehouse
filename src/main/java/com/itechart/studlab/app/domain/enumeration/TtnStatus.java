package com.itechart.studlab.app.domain.enumeration;

public enum TtnStatus {
    REGISTERED("REGISTERED"),
    CHECKED("CHECKED"),
    EDITING_BY_DISPATCHER("EDITING_BY_DISPATCHER"),
    EDITING_BY_MANAGER("EDITING_BY_MANAGER"),
    ACCEPTED_TO_STORAGE("ACCEPTED_TO_STORAGE"),
    RELEASE_ALLOWED("RELEASE_ALLOWED"),
    REMOVED_FROM_STORAGE("REMOVED_FROM_STORAGE");

    String name;

    TtnStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
