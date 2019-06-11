package com.itechart.studlab.app.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.itechart.studlab.app.domain.enumeration.ActType;

/**
 * A Act.
 */
@Entity
@Table(name = "act")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "act")
public class Act implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_date")
    private LocalDate date;

    @Column(name = "jhi_cost")
    private Double cost;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private ActType type;

    @OneToOne
    @JoinColumn(unique = true)
    private AppUser user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Act date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getCost() {
        return cost;
    }

    public Act cost(Double cost) {
        this.cost = cost;
        return this;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public ActType getType() {
        return type;
    }

    public Act type(ActType type) {
        this.type = type;
        return this;
    }

    public void setType(ActType type) {
        this.type = type;
    }

    public AppUser getUser() {
        return user;
    }

    public Act user(AppUser appUser) {
        this.user = appUser;
        return this;
    }

    public void setUser(AppUser appUser) {
        this.user = appUser;
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
        Act act = (Act) o;
        if (act.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), act.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Act{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", cost=" + getCost() +
            ", type='" + getType() + "'" +
            "}";
    }
}
