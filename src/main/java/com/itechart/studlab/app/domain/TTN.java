package com.itechart.studlab.app.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A TTN.
 */
@Entity
@Table(name = "ttn")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "ttn")
public class TTN implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "date_of_creation")
    private LocalDate dateOfCreation;

    @Column(name = "description")
    private String description;

    @Column(name = "products_amount")
    private Integer productsAmount;

    @Column(name = "number_of_product_entries")
    private Integer numberOfProductEntries;

    @Column(name = "date_time_of_registration")
    private Instant dateTimeOfRegistration;

    @Column(name = "is_accepted")
    private Boolean isAccepted;

    @OneToOne
    @JoinColumn(unique = true)
    private AppUser storehouseDispatcher;

    @OneToOne
    @JoinColumn(unique = true)
    private AppUser manager;

    @OneToOne(optional = false)    @NotNull

    @JoinColumn(unique = true)
    private AppUser sender;

    @OneToOne(optional = false)    @NotNull

    @JoinColumn(unique = true)
    private Transport transport;

    @OneToOne(optional = false)    @NotNull

    @JoinColumn(unique = true)
    private Transporter transporter;

    @OneToOne
    @JoinColumn(unique = true)
    private Driver driver;

    @OneToOne
    @JoinColumn(unique = true)
    private Recipient recipient;

    @OneToMany(mappedBy = "tTN")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Product> products = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public TTN serialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public LocalDate getDateOfCreation() {
        return dateOfCreation;
    }

    public TTN dateOfCreation(LocalDate dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
        return this;
    }

    public void setDateOfCreation(LocalDate dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public String getDescription() {
        return description;
    }

    public TTN description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getProductsAmount() {
        return productsAmount;
    }

    public TTN productsAmount(Integer productsAmount) {
        this.productsAmount = productsAmount;
        return this;
    }

    public void setProductsAmount(Integer productsAmount) {
        this.productsAmount = productsAmount;
    }

    public Integer getNumberOfProductEntries() {
        return numberOfProductEntries;
    }

    public TTN numberOfProductEntries(Integer numberOfProductEntries) {
        this.numberOfProductEntries = numberOfProductEntries;
        return this;
    }

    public void setNumberOfProductEntries(Integer numberOfProductEntries) {
        this.numberOfProductEntries = numberOfProductEntries;
    }

    public Instant getDateTimeOfRegistration() {
        return dateTimeOfRegistration;
    }

    public TTN dateTimeOfRegistration(Instant dateTimeOfRegistration) {
        this.dateTimeOfRegistration = dateTimeOfRegistration;
        return this;
    }

    public void setDateTimeOfRegistration(Instant dateTimeOfRegistration) {
        this.dateTimeOfRegistration = dateTimeOfRegistration;
    }

    public Boolean isIsAccepted() {
        return isAccepted;
    }

    public TTN isAccepted(Boolean isAccepted) {
        this.isAccepted = isAccepted;
        return this;
    }

    public void setIsAccepted(Boolean isAccepted) {
        this.isAccepted = isAccepted;
    }

    public AppUser getStorehouseDispatcher() {
        return storehouseDispatcher;
    }

    public TTN storehouseDispatcher(AppUser appUser) {
        this.storehouseDispatcher = appUser;
        return this;
    }

    public void setStorehouseDispatcher(AppUser appUser) {
        this.storehouseDispatcher = appUser;
    }

    public AppUser getManager() {
        return manager;
    }

    public TTN manager(AppUser appUser) {
        this.manager = appUser;
        return this;
    }

    public void setManager(AppUser appUser) {
        this.manager = appUser;
    }

    public AppUser getSender() {
        return sender;
    }

    public TTN sender(AppUser appUser) {
        this.sender = appUser;
        return this;
    }

    public void setSender(AppUser appUser) {
        this.sender = appUser;
    }

    public Transport getTransport() {
        return transport;
    }

    public TTN transport(Transport transport) {
        this.transport = transport;
        return this;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    public Transporter getTransporter() {
        return transporter;
    }

    public TTN transporter(Transporter transporter) {
        this.transporter = transporter;
        return this;
    }

    public void setTransporter(Transporter transporter) {
        this.transporter = transporter;
    }

    public Driver getDriver() {
        return driver;
    }

    public TTN driver(Driver driver) {
        this.driver = driver;
        return this;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public TTN recipient(Recipient recipient) {
        this.recipient = recipient;
        return this;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public TTN products(Set<Product> products) {
        this.products = products;
        return this;
    }

    public TTN addProducts(Product product) {
        this.products.add(product);
        product.setTTN(this);
        return this;
    }

    public TTN removeProducts(Product product) {
        this.products.remove(product);
        product.setTTN(null);
        return this;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
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
        TTN tTN = (TTN) o;
        if (tTN.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tTN.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TTN{" +
            "id=" + getId() +
            ", serialNumber='" + getSerialNumber() + "'" +
            ", dateOfCreation='" + getDateOfCreation() + "'" +
            ", description='" + getDescription() + "'" +
            ", productsAmount=" + getProductsAmount() +
            ", numberOfProductEntries=" + getNumberOfProductEntries() +
            ", dateTimeOfRegistration='" + getDateTimeOfRegistration() + "'" +
            ", isAccepted='" + isIsAccepted() + "'" +
            "}";
    }
}
