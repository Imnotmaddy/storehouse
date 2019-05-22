package com.itechart.studlab.app.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

import com.itechart.studlab.app.domain.enumeration.DeliveryType;

import com.itechart.studlab.app.domain.enumeration.Facility;

/**
 * A Transport.
 */
@Entity
@Table(name = "transport")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "transport")
public class Transport implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vehicle_number")
    private String vehicleNumber;

    @Column(name = "wagons_number")
    private String wagonsNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_type")
    private DeliveryType deliveryType;

    @Enumerated(EnumType.STRING)
    @Column(name = "facility")
    private Facility facility;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public Transport vehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
        return this;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getWagonsNumber() {
        return wagonsNumber;
    }

    public Transport wagonsNumber(String wagonsNumber) {
        this.wagonsNumber = wagonsNumber;
        return this;
    }

    public void setWagonsNumber(String wagonsNumber) {
        this.wagonsNumber = wagonsNumber;
    }

    public DeliveryType getDeliveryType() {
        return deliveryType;
    }

    public Transport deliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
        return this;
    }

    public void setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    public Facility getFacility() {
        return facility;
    }

    public Transport facility(Facility facility) {
        this.facility = facility;
        return this;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Transport transport = (Transport) o;
        if (transport.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), transport.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Transport{" +
            "id=" + getId() +
            ", vehicleNumber='" + getVehicleNumber() + "'" +
            ", wagonsNumber='" + getWagonsNumber() + "'" +
            ", deliveryType='" + getDeliveryType() + "'" +
            ", facility='" + getFacility() + "'" +
            "}";
    }
}
