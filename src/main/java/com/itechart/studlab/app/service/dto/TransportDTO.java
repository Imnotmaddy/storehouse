package com.itechart.studlab.app.service.dto;
import java.io.Serializable;
import java.util.Objects;
import com.itechart.studlab.app.domain.enumeration.DeliveryType;
import com.itechart.studlab.app.domain.enumeration.Facility;

/**
 * A DTO for the Transport entity.
 */
public class TransportDTO implements Serializable {

    private Long id;

    private String vehicleNumber;

    private String wagonsNumber;

    private DeliveryType deliveryType;

    private Facility facility;


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

    public String getWagonsNumber() {
        return wagonsNumber;
    }

    public void setWagonsNumber(String wagonsNumber) {
        this.wagonsNumber = wagonsNumber;
    }

    public DeliveryType getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
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
            ", wagonsNumber='" + getWagonsNumber() + "'" +
            ", deliveryType='" + getDeliveryType() + "'" +
            ", facility='" + getFacility() + "'" +
            "}";
    }
}
