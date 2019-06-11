package com.itechart.studlab.app.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Storehouse entity.
 */
public class StorehouseDTO implements Serializable {

    private Long id;


    private Long ownerId;

    private String ownerName;

    private Long administratorId;

    private String administratorName;

    private Long dispatcherId;

    private String dispatcherName;

    private Long managerId;

    private String managerName;

    private Long supervisorId;

    private String supervisorName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long appUserId) {
        this.ownerId = appUserId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String appUserName) {
        this.ownerName = appUserName;
    }

    public Long getAdministratorId() {
        return administratorId;
    }

    public void setAdministratorId(Long appUserId) {
        this.administratorId = appUserId;
    }

    public String getAdministratorName() {
        return administratorName;
    }

    public void setAdministratorName(String appUserName) {
        this.administratorName = appUserName;
    }

    public Long getDispatcherId() {
        return dispatcherId;
    }

    public void setDispatcherId(Long appUserId) {
        this.dispatcherId = appUserId;
    }

    public String getDispatcherName() {
        return dispatcherName;
    }

    public void setDispatcherName(String appUserName) {
        this.dispatcherName = appUserName;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long appUserId) {
        this.managerId = appUserId;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String appUserName) {
        this.managerName = appUserName;
    }

    public Long getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(Long appUserId) {
        this.supervisorId = appUserId;
    }

    public String getSupervisorName() {
        return supervisorName;
    }

    public void setSupervisorName(String appUserName) {
        this.supervisorName = appUserName;
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
            ", owner=" + getOwnerId() +
            ", owner='" + getOwnerName() + "'" +
            ", administrator=" + getAdministratorId() +
            ", administrator='" + getAdministratorName() + "'" +
            ", dispatcher=" + getDispatcherId() +
            ", dispatcher='" + getDispatcherName() + "'" +
            ", manager=" + getManagerId() +
            ", manager='" + getManagerName() + "'" +
            ", supervisor=" + getSupervisorId() +
            ", supervisor='" + getSupervisorName() + "'" +
            "}";
    }
}
