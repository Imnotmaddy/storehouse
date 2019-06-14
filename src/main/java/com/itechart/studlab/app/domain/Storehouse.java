package com.itechart.studlab.app.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
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

    @OneToOne(optional = false)    @NotNull

    @JoinColumn(unique = true)
    private User owner;

    @OneToOne(optional = false)    @NotNull

    @JoinColumn(unique = true)
    private User administrator;

    @OneToOne(optional = false)    @NotNull

    @JoinColumn(unique = true)
    private User dispatcher;

    @OneToOne(optional = false)    @NotNull

    @JoinColumn(unique = true)
    private User manager;

    @OneToOne(optional = false)    @NotNull

    @JoinColumn(unique = true)
    private User supervisor;

    @OneToMany(mappedBy = "storehouse")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<StorageRoom> rooms = new HashSet<>();
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

    public User getOwner() {
        return owner;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Storehouse owner(User user) {
        this.owner = user;
        return this;
    }

    public void setOwner(User user) {
        this.owner = user;
    }

    public User getAdministrator() {
        return administrator;
    }

    public Storehouse administrator(User user) {
        this.administrator = user;
        return this;
    }

    public void setAdministrator(User user) {
        this.administrator = user;
    }

    public User getDispatcher() {
        return dispatcher;
    }

    public Storehouse dispatcher(User user) {
        this.dispatcher = user;
        return this;
    }

    public void setDispatcher(User user) {
        this.dispatcher = user;
    }

    public User getManager() {
        return manager;
    }

    public Storehouse manager(User user) {
        this.manager = user;
        return this;
    }

    public void setManager(User user) {
        this.manager = user;
    }

    public User getSupervisor() {
        return supervisor;
    }

    public Storehouse supervisor(User user) {
        this.supervisor = user;
        return this;
    }

    public void setSupervisor(User user) {
        this.supervisor = user;
    }

    public Set<StorageRoom> getRooms() {
        return rooms;
    }

    public Storehouse rooms(Set<StorageRoom> storageRooms) {
        this.rooms = storageRooms;
        return this;
    }

    public Storehouse addRooms(StorageRoom storageRoom) {
        this.rooms.add(storageRoom);
        storageRoom.setStorehouse(this);
        return this;
    }

    public Storehouse removeRooms(StorageRoom storageRoom) {
        this.rooms.remove(storageRoom);
        storageRoom.setStorehouse(null);
        return this;
    }

    public void setRooms(Set<StorageRoom> storageRooms) {
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
