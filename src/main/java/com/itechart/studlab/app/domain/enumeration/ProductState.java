package com.itechart.studlab.app.domain.enumeration;

/**
 * The ProductState enumeration.
 */
public enum ProductState {
    REGISTRATED("REGISTRATED"),
    APPROVED("APPROVED"),
    STORED("STORED"),
    LOST_BY_TRANSPORTER("LOST_BY_TRANSPORTER"),
    LOST_FROM_STORAGE("LOST_FROM_STORAGE"),
    STOLEN_FROM_STORAGE("STOLEN_FROM_STORAGE"),
    TRANSPORTER_SHORTAGE("TRANSPORTER_SHORTAGE"),
    CONFISCATED("CONFISCATED"),
    RECYCLED("RECYCLED"),
    UNSTORED("UNSTORED"),
    READY_TO_LEAVE("READY_TO_LEAVE"),
    REMOVED_FROM_STORAGE("REMOVED_FROM_STORAGE");

    String state;

    ProductState(String state){
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
