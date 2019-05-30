package com.itechart.studlab.app.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Storehouse entity.
 */
public class StorehouseDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;


    private Long ownerId;

    private String ownerLastName;

    private Long administratorId;

    private String administratorLastName;

    private Long dispatcherId;

    private String dispatcherLastName;

    private Long managerId;

    private String managerLastName;

    private Long supervisorId;

    private String supervisorLastName;

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

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long userId) {
        this.ownerId = userId;
    }

    public String getOwnerLastName() {
        return ownerLastName;
    }

    public void setOwnerLastName(String userLastName) {
        this.ownerLastName = userLastName;
    }

    public Long getAdministratorId() {
        return administratorId;
    }

    public void setAdministratorId(Long userId) {
        this.administratorId = userId;
    }

    public String getAdministratorLastName() {
        return administratorLastName;
    }

    public void setAdministratorLastName(String userLastName) {
        this.administratorLastName = userLastName;
    }

    public Long getDispatcherId() {
        return dispatcherId;
    }

    public void setDispatcherId(Long userId) {
        this.dispatcherId = userId;
    }

    public String getDispatcherLastName() {
        return dispatcherLastName;
    }

    public void setDispatcherLastName(String userLastName) {
        this.dispatcherLastName = userLastName;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long userId) {
        this.managerId = userId;
    }

    public String getManagerLastName() {
        return managerLastName;
    }

    public void setManagerLastName(String userLastName) {
        this.managerLastName = userLastName;
    }

    public Long getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(Long userId) {
        this.supervisorId = userId;
    }

    public String getSupervisorLastName() {
        return supervisorLastName;
    }

    public void setSupervisorLastName(String userLastName) {
        this.supervisorLastName = userLastName;
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
            ", owner=" + getOwnerId() +
            ", owner='" + getOwnerLastName() + "'" +
            ", administrator=" + getAdministratorId() +
            ", administrator='" + getAdministratorLastName() + "'" +
            ", dispatcher=" + getDispatcherId() +
            ", dispatcher='" + getDispatcherLastName() + "'" +
            ", manager=" + getManagerId() +
            ", manager='" + getManagerLastName() + "'" +
            ", supervisor=" + getSupervisorId() +
            ", supervisor='" + getSupervisorLastName() + "'" +
            "}";
    }
}
