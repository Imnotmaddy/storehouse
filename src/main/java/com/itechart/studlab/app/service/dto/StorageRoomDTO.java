package com.itechart.studlab.app.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.itechart.studlab.app.domain.enumeration.Facility;

/**
 * A DTO for the StorageRoom entity.
 */
public class StorageRoomDTO implements Serializable {

    private Long id;

    @NotNull
    private String roomNumber;

    @NotNull
    private Facility type;


    private Long storehouseId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Facility getType() {
        return type;
    }

    public void setType(Facility type) {
        this.type = type;
    }

    public Long getStorehouseId() {
        return storehouseId;
    }

    public void setStorehouseId(Long storehouseId) {
        this.storehouseId = storehouseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StorageRoomDTO storageRoomDTO = (StorageRoomDTO) o;
        if (storageRoomDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), storageRoomDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StorageRoomDTO{" +
            "id=" + getId() +
            ", roomNumber='" + getRoomNumber() + "'" +
            ", type='" + getType() + "'" +
            ", storehouse=" + getStorehouseId() +
            "}";
    }
}
