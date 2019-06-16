package com.itechart.studlab.app.service.dto;
import java.time.Instant;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the TTN entity.
 */
public class TTNDTO implements Serializable {

    private Long id;

    @NotNull
    private String serialNumber;

    @NotNull
    private LocalDate dateOfCreation;

    private String description;

    private String driverName;

    private Integer productsAmount;

    private Integer numberOfProductEntries;

    @NotNull
    private Instant dateTimeOfRegistration;

    private Boolean isAccepted;


    private Long dispatcherId;

    private String dispatcherLastName;

    private Long managerId;

    private String managerLastName;

    private Long senderId;

    private String senderLastName;

    private Long transportId;

    private Long transporterId;

    private String transporterCompanyName;

    private List<ProductDTO> products;

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }

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

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
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

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long userId) {
        this.senderId = userId;
    }

    public String getSenderLastName() {
        return senderLastName;
    }

    public void setSenderLastName(String userLastName) {
        this.senderLastName = userLastName;
    }

    public Long getTransportId() {
        return transportId;
    }

    public void setTransportId(Long transportId) {
        this.transportId = transportId;
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
            ", driverName='" + getDriverName() + "'" +
            ", productsAmount=" + getProductsAmount() +
            ", numberOfProductEntries=" + getNumberOfProductEntries() +
            ", dateTimeOfRegistration='" + getDateTimeOfRegistration() + "'" +
            ", isAccepted='" + isIsAccepted() + "'" +
            ", dispatcher=" + getDispatcherId() +
            ", dispatcher='" + getDispatcherLastName() + "'" +
            ", manager=" + getManagerId() +
            ", manager='" + getManagerLastName() + "'" +
            ", sender=" + getSenderId() +
            ", sender='" + getSenderLastName() + "'" +
            ", transport=" + getTransportId() +
            ", transporter=" + getTransporterId() +
            ", transporter='" + getTransporterCompanyName() + "'" +
            "}";
    }
}
