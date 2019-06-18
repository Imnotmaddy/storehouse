package com.itechart.studlab.app.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the Storehouse entity.
 */
public class StorehouseDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String companyName;


    private List<StorageRoomDTO> rooms;

    private List<UserDTO> employees;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<StorageRoomDTO> getRooms() {
        return rooms;
    }

    public void setRooms(List<StorageRoomDTO> storageRoomDTOSet) {
        this.rooms = storageRoomDTOSet;
    }

    public List<UserDTO> getEmployees() {
        return employees;
    }

    public void setEmployees(List<UserDTO> employees) {
        this.employees = employees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StorehouseDTO storehouseDTO = (StorehouseDTO) o;
        if (storehouseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), storehouseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StorehouseDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", companyName='" + getCompanyName() + "'" +
            ", employees='" + getEmployees() + "'" +
            ", rooms='" + getRooms() + "'" +
            "}";
    }
}
