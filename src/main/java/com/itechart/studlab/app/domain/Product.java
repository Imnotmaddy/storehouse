package com.itechart.studlab.app.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

import com.itechart.studlab.app.domain.enumeration.ProductState;

import com.itechart.studlab.app.domain.enumeration.Facility;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private ProductState state;

    @Column(name = "days_in_storage")
    private Integer daysInStorage;

    @NotNull
    @Column(name = "jhi_cost", nullable = false)
    private Double cost;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "required_facility", nullable = false)
    private Facility requiredFacility;

    @NotNull
    @Column(name = "weight", nullable = false)
    private Double weight;

    @ManyToOne
    @JsonIgnoreProperties("products")
    private Act act;

    @ManyToOne
    @JsonIgnoreProperties("products")
    private StorageRoom storageRoom;

    @ManyToOne
    @JsonIgnoreProperties("products")
    private TTN tTN;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Product name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Product quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public ProductState getState() {
        return state;
    }

    public Product state(ProductState state) {
        this.state = state;
        return this;
    }

    public void setState(ProductState state) {
        this.state = state;
    }

    public Integer getDaysInStorage() {
        return daysInStorage;
    }

    public Product daysInStorage(Integer daysInStorage) {
        this.daysInStorage = daysInStorage;
        return this;
    }

    public void setDaysInStorage(Integer daysInStorage) {
        this.daysInStorage = daysInStorage;
    }

    public Double getCost() {
        return cost;
    }

    public Product cost(Double cost) {
        this.cost = cost;
        return this;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Facility getRequiredFacility() {
        return requiredFacility;
    }

    public Product requiredFacility(Facility requiredFacility) {
        this.requiredFacility = requiredFacility;
        return this;
    }

    public void setRequiredFacility(Facility requiredFacility) {
        this.requiredFacility = requiredFacility;
    }

    public Double getWeight() {
        return weight;
    }

    public Product weight(Double weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Act getAct() {
        return act;
    }

    public Product act(Act act) {
        this.act = act;
        return this;
    }

    public void setAct(Act act) {
        this.act = act;
    }

    public StorageRoom getStorageRoom() {
        return storageRoom;
    }

    public Product storageRoom(StorageRoom storageRoom) {
        this.storageRoom = storageRoom;
        return this;
    }

    public void setStorageRoom(StorageRoom storageRoom) {
        this.storageRoom = storageRoom;
    }

    public TTN getTTN() {
        return tTN;
    }

    public Product tTN(TTN tTN) {
        this.tTN = tTN;
        return this;
    }

    public void setTTN(TTN tTN) {
        this.tTN = tTN;
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
        Product product = (Product) o;
        if (product.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), product.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", quantity=" + getQuantity() +
            ", state='" + getState() + "'" +
            ", daysInStorage=" + getDaysInStorage() +
            ", cost=" + getCost() +
            ", requiredFacility='" + getRequiredFacility() + "'" +
            ", weight=" + getWeight() +
            "}";
    }
}
