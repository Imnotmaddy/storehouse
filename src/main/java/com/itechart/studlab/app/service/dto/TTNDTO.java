package com.itechart.studlab.app.service.dto;
import java.time.Instant;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the TTN entity.
 */
public class TTNDTO implements Serializable {

    private Long id;

    private String serialNumber;

    private LocalDate dateOfCreation;

    private String description;

    private Integer productsAmount;

    private Integer numberOfProductEntries;

    private Instant dateTimeOfRegistration;

    private Boolean isAccepted;


    private Long storehouseDispatcherId;

    private String storehouseDispatcherName;

    private Long managerId;

    private String managerName;

    private Long senderId;

    private String senderName;

    private Long transportId;

    private String transportFacility;

    private Long transporterId;

    private String transporterCompanyName;

    private Long driverId;

    private String driverName;

    private Long recipientId;

    private String recipientCompanyName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public LocalDate getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(LocalDate dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getProductsAmount() {
        return productsAmount;
    }

    public void setProductsAmount(Integer productsAmount) {
        this.productsAmount = productsAmount;
    }

    public Integer getNumberOfProductEntries() {
        return numberOfProductEntries;
    }

    public void setNumberOfProductEntries(Integer numberOfProductEntries) {
        this.numberOfProductEntries = numberOfProductEntries;
    }

    public Instant getDateTimeOfRegistration() {
        return dateTimeOfRegistration;
    }

    public void setDateTimeOfRegistration(Instant dateTimeOfRegistration) {
        this.dateTimeOfRegistration = dateTimeOfRegistration;
    }

    public Boolean isIsAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(Boolean isAccepted) {
        this.isAccepted = isAccepted;
    }

    public Long getStorehouseDispatcherId() {
        return storehouseDispatcherId;
    }

    public void setStorehouseDispatcherId(Long appUserId) {
        this.storehouseDispatcherId = appUserId;
    }

    public String getStorehouseDispatcherName() {
        return storehouseDispatcherName;
    }

    public void setStorehouseDispatcherName(String appUserName) {
        this.storehouseDispatcherName = appUserName;
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

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long appUserId) {
        this.senderId = appUserId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String appUserName) {
        this.senderName = appUserName;
    }

    public Long getTransportId() {
        return transportId;
    }

    public void setTransportId(Long transportId) {
        this.transportId = transportId;
    }

    public String getTransportFacility() {
        return transportFacility;
    }

    public void setTransportFacility(String transportFacility) {
        this.transportFacility = transportFacility;
    }

    public Long getTransporterId() {
        return transporterId;
    }

    public void setTransporterId(Long transporterId) {
        this.transporterId = transporterId;
    }

    public String getTransporterCompanyName() {
        return transporterCompanyName;
    }

    public void setTransporterCompanyName(String transporterCompanyName) {
        this.transporterCompanyName = transporterCompanyName;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public Long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }

    public String getRecipientCompanyName() {
        return recipientCompanyName;
    }

    public void setRecipientCompanyName(String recipientCompanyName) {
        this.recipientCompanyName = recipientCompanyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TTNDTO tTNDTO = (TTNDTO) o;
        if (tTNDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tTNDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TTNDTO{" +
            "id=" + getId() +
            ", serialNumber='" + getSerialNumber() + "'" +
            ", dateOfCreation='" + getDateOfCreation() + "'" +
            ", description='" + getDescription() + "'" +
            ", productsAmount=" + getProductsAmount() +
            ", numberOfProductEntries=" + getNumberOfProductEntries() +
            ", dateTimeOfRegistration='" + getDateTimeOfRegistration() + "'" +
            ", isAccepted='" + isIsAccepted() + "'" +
            ", storehouseDispatcher=" + getStorehouseDispatcherId() +
            ", storehouseDispatcher='" + getStorehouseDispatcherName() + "'" +
            ", manager=" + getManagerId() +
            ", manager='" + getManagerName() + "'" +
            ", sender=" + getSenderId() +
            ", sender='" + getSenderName() + "'" +
            ", transport=" + getTransportId() +
            ", transport='" + getTransportFacility() + "'" +
            ", transporter=" + getTransporterId() +
            ", transporter='" + getTransporterCompanyName() + "'" +
            ", driver=" + getDriverId() +
            ", driver='" + getDriverName() + "'" +
            ", recipient=" + getRecipientId() +
            ", recipient='" + getRecipientCompanyName() + "'" +
            "}";
    }
}
