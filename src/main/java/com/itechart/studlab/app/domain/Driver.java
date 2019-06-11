package com.itechart.studlab.app.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Driver.
 */
@Entity
@Table(name = "driver")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "driver")
public class Driver implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "passport_id")
    private String passportId;

    @Column(name = "citizenship")
    private String citizenship;

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

    public Driver name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassportId() {
        return passportId;
    }

    public Driver passportId(String passportId) {
        this.passportId = passportId;
        return this;
    }

    public void setPassportId(String passportId) {
        this.passportId = passportId;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public Driver citizenship(String citizenship) {
        this.citizenship = citizenship;
        return this;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
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
        Driver driver = (Driver) o;
        if (driver.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), driver.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Driver{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", passportId='" + getPassportId() + "'" +
            ", citizenship='" + getCitizenship() + "'" +
            "}";
    }
}
