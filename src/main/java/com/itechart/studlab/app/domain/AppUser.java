package com.itechart.studlab.app.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A AppUser.
 */
@Entity
@Table(name = "app_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "appuser")
public class AppUser implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "birthdate")
    private LocalDate birthdate;

    @Column(name = "is_suspended")
    private Boolean isSuspended;

    @Column(name = "money")
    private Double money;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToOne(mappedBy = "user")
    @JsonIgnore
    private Address address;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public AppUser birthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public Boolean isIsSuspended() {
        return isSuspended;
    }

    public AppUser isSuspended(Boolean isSuspended) {
        this.isSuspended = isSuspended;
        return this;
    }

    public void setIsSuspended(Boolean isSuspended) {
        this.isSuspended = isSuspended;
    }

    public Double getMoney() {
        return money;
    }

    public AppUser money(Double money) {
        this.money = money;
        return this;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public User getUser() {
        return user;
    }

    public AppUser user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Address getAddress() {
        return address;
    }

    public AppUser address(Address address) {
        this.address = address;
        return this;
    }

    public void setAddress(Address address) {
        this.address = address;
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
        AppUser appUser = (AppUser) o;
        if (appUser.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), appUser.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AppUser{" +
            "id=" + getId() +
            ", birthdate='" + getBirthdate() + "'" +
            ", isSuspended='" + isIsSuspended() + "'" +
            ", money=" + getMoney() +
            "}";
    }
}
