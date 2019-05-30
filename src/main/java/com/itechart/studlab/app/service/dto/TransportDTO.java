package com.itechart.studlab.app.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.itechart.studlab.app.domain.enumeration.DeliveryType;

/**
 * A DTO for the Transport entity.
 */
public class TransportDTO implements Serializable {

    private Long id;

    private String vehicleNumber;

    @NotNull
    private DeliveryType deliveryType;


    private Long companyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public DeliveryType getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long transporterId) {
        this.companyId = transporterId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TransportDTO transportDTO = (TransportDTO) o;
        if (transportDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), transportDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TransportDTO{" +
            "id=" + getId() +
            ", vehicleNumber='" + getVehicleNumber() + "'" +
            ", deliveryType='" + getDeliveryType() + "'" +
            ", company=" + getCompanyId() +
            "}";
    }
}
