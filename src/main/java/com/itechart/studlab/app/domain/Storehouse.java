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

    @OneToOne(optional = false)    @NotNull

    @JoinColumn(unique = true)
    private AppUser owner;

    @OneToOne(optional = false)    @NotNull

    @JoinColumn(unique = true)
    private AppUser administrator;

    @OneToOne(optional = false)    @NotNull

    @JoinColumn(unique = true)
    private AppUser dispatcher;

    @OneToOne(optional = false)    @NotNull

    @JoinColumn(unique = true)
    private AppUser manager;

    @OneToOne(optional = false)    @NotNull

    @JoinColumn(unique = true)
    private AppUser supervisor;

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

    public AppUser getOwner() {
        return owner;
    }

    public Storehouse owner(AppUser appUser) {
        this.owner = appUser;
        return this;
    }

    public void setOwner(AppUser appUser) {
        this.owner = appUser;
    }

    public AppUser getAdministrator() {
        return administrator;
    }

    public Storehouse administrator(AppUser appUser) {
        this.administrator = appUser;
        return this;
    }

    public void setAdministrator(AppUser appUser) {
        this.administrator = appUser;
    }

    public AppUser getDispatcher() {
        return dispatcher;
    }

    public Storehouse dispatcher(AppUser appUser) {
        this.dispatcher = appUser;
        return this;
    }

    public void setDispatcher(AppUser appUser) {
        this.dispatcher = appUser;
    }

    public AppUser getManager() {
        return manager;
    }

    public Storehouse manager(AppUser appUser) {
        this.manager = appUser;
        return this;
    }

    public void setManager(AppUser appUser) {
        this.manager = appUser;
    }

    public AppUser getSupervisor() {
        return supervisor;
    }

    public Storehouse supervisor(AppUser appUser) {
        this.supervisor = appUser;
        return this;
    }

    public void setSupervisor(AppUser appUser) {
        this.supervisor = appUser;
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
            "}";
    }
}
