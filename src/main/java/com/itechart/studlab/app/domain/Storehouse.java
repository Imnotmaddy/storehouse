package com.itechart.studlab.app.domain;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A Storehouse.
 */
@Entity
@Table(name = "storehouse")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "storehouse")
public class Storehouse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "company_name")
    private String companyName;

    @JsonManagedReference
    @OneToMany(mappedBy = "storehouse", cascade = CascadeType.ALL, orphanRemoval = true)
    @NotNull
    private List<User> owners = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "storehouse", cascade = CascadeType.ALL, orphanRemoval = true)
    @NotNull
    private List<User> dispatchers = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "storehouse", cascade = CascadeType.ALL, orphanRemoval = true)
    @NotNull
    private List<User> managers = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "storehouse", cascade = CascadeType.ALL, orphanRemoval = true)
    @NotNull
    private List<User> supervisors = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "storehouse", cascade = CascadeType.ALL, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<StorageRoom> rooms = new ArrayList<>();

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

    public Storehouse name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getOwners() {
        return owners;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Storehouse owners(List<User> users) {
        this.owners = users;
        return this;
    }

    public void setOwners(List<User> users) {
        this.owners = users;
    }

    public List<User> getDispatchers() {
        return dispatchers;
    }

    public Storehouse dispatchers(List<User> users) {
        this.dispatchers = users;
        return this;
    }

    public void setDispatchers(List<User> users) {
        this.dispatchers = users;
    }

    public List<User> getManagers() {
        return managers;
    }

    public Storehouse managers(List<User> users) {
        this.managers = users;
        return this;
    }

    public void setManagers(List<User> users) {
        this.managers = users;
    }

    public List<User> getSupervisors() {
        return supervisors;
    }

    public Storehouse supervisors(List<User> users) {
        this.supervisors = users;
        return this;
    }

    public void setSupervisors(List<User> users) {
        this.supervisors = users;
    }

    public List<StorageRoom> getRooms() {
        return rooms;
    }

    public Storehouse rooms(List<StorageRoom> storageRooms) {
        this.rooms = storageRooms;
        return this;
    }

    public void setRooms(List<StorageRoom> storageRooms) {
        this.rooms = storageRooms;
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
        Storehouse storehouse = (Storehouse) o;
        if (storehouse.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), storehouse.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Storehouse{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
