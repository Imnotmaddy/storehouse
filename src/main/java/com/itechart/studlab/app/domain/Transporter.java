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
 * A Transporter.
 */
@Entity
@Table(name = "transporter")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "transporter")
public class Transporter implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "company_name", nullable = false)
    private String companyName;

    @OneToMany(mappedBy = "company")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Transport> vehicles = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Transporter companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Set<Transport> getVehicles() {
        return vehicles;
    }

    public Transporter vehicles(Set<Transport> transports) {
        this.vehicles = transports;
        return this;
    }

    public Transporter addVehicles(Transport transport) {
        this.vehicles.add(transport);
        transport.setCompany(this);
        return this;
    }

    public Transporter removeVehicles(Transport transport) {
        this.vehicles.remove(transport);
        transport.setCompany(null);
        return this;
    }

    public void setVehicles(Set<Transport> transports) {
        this.vehicles = transports;
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
        Transporter transporter = (Transporter) o;
        if (transporter.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), transporter.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Transporter{" +
            "id=" + getId() +
            ", companyName='" + getCompanyName() + "'" +
            "}";
    }
}
