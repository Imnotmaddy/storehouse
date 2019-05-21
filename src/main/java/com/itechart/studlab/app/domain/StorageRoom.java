package com.itechart.studlab.app.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

import com.itechart.studlab.app.domain.enumeration.Facility;

/**
 * A StorageRoom.
 */
@Entity
@Table(name = "storage_room")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "storageroom")
public class StorageRoom implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount_of_distinct_products")
    private Integer amountOfDistinctProducts;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private Facility type;

    @ManyToOne
    @JsonIgnoreProperties("rooms")
    private Storehouse storehouse;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAmountOfDistinctProducts() {
        return amountOfDistinctProducts;
    }

    public StorageRoom amountOfDistinctProducts(Integer amountOfDistinctProducts) {
        this.amountOfDistinctProducts = amountOfDistinctProducts;
        return this;
    }

    public void setAmountOfDistinctProducts(Integer amountOfDistinctProducts) {
        this.amountOfDistinctProducts = amountOfDistinctProducts;
    }

    public Facility getType() {
        return type;
    }

    public StorageRoom type(Facility type) {
        this.type = type;
        return this;
    }

    public void setType(Facility type) {
        this.type = type;
    }

    public Storehouse getStorehouse() {
        return storehouse;
    }

    public StorageRoom storehouse(Storehouse storehouse) {
        this.storehouse = storehouse;
        return this;
    }

    public void setStorehouse(Storehouse storehouse) {
        this.storehouse = storehouse;
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
        StorageRoom storageRoom = (StorageRoom) o;
        if (storageRoom.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), storageRoom.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StorageRoom{" +
            "id=" + getId() +
            ", amountOfDistinctProducts=" + getAmountOfDistinctProducts() +
            ", type='" + getType() + "'" +
            "}";
    }
}
