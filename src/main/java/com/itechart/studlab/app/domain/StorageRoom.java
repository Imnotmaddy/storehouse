package com.itechart.studlab.app.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.itechart.studlab.app.domain.enumeration.Facility;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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

    @NotNull
    @Column(name = "room_number", nullable = false)
    private String roomNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type", nullable = false)
    private Facility type;

    @OneToMany(mappedBy = "storageRoom")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Product> products = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storehouse_id")
    private Storehouse storehouse;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public StorageRoom roomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
        return this;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
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

    public Set<Product> getProducts() {
        return products;
    }

    public StorageRoom products(Set<Product> products) {
        this.products = products;
        return this;
    }

    public StorageRoom addProducts(Product product) {
        this.products.add(product);
        product.setStorageRoom(this);
        return this;
    }

    public StorageRoom removeProducts(Product product) {
        this.products.remove(product);
        product.setStorageRoom(null);
        return this;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
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
            ", roomNumber='" + getRoomNumber() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
