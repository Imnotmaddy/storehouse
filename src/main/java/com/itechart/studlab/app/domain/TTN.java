package com.itechart.studlab.app.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.itechart.studlab.app.domain.enumeration.TtnStatus;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.annotations.Cascade;
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

    @NotNull
    @Column(name = "serial_number", nullable = false)
    private String serialNumber;

    @NotNull
    @Column(name = "date_of_creation", nullable = false)
    private LocalDate dateOfCreation;

    @Column(name = "description")
    private String description;

    @Column(name = "driver_name")
    private String driverName;

    @Column(name = "products_amount")
    private Integer productsAmount;

    @Column(name = "number_of_product_entries")
    private Integer numberOfProductEntries;

    @NotNull
    @Column(name = "date_time_of_registration", nullable = false)
    private Instant dateTimeOfRegistration;

    @Column(name = "status")
    private TtnStatus status;

    @OneToOne
    @JoinColumn(unique = true)
    private User dispatcher;

    @OneToOne
    @JoinColumn(unique = true)
    private User manager;

    @Column(name = "sender")
    private String sender;

    @Column(name = "recipient")
    private String recipient;

    @OneToOne(optional = false)    @NotNull
    @JoinColumn(unique = true)
    private Transport transport;

    @OneToOne(optional = false)    @NotNull

    @JoinColumn(unique = true)
    private Transporter transporter;

    @OneToMany(mappedBy = "tTN", cascade = CascadeType.ALL, orphanRemoval = true)
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

    public String getDriverName() {
        return driverName;
    }

    public TTN driverName(String driverName) {
        this.driverName = driverName;
        return this;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
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

    public TtnStatus getStatus() {
        return status;
    }

    public TTN status(TtnStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(TtnStatus status) {
        this.status = status;
    }

    public User getDispatcher() {
        return dispatcher;
    }

    public TTN dispatcher(User user) {
        this.dispatcher = user;
        return this;
    }

    public void setDispatcher(User user) {
        this.dispatcher = user;
    }

    public User getManager() {
        return manager;
    }

    public TTN manager(User user) {
        this.manager = user;
        return this;
    }

    public void setManager(User user) {
        this.manager = user;
    }

    public String getSender() {
        return sender;
    }

    public TTN sender(String sender) {
        this.sender = sender;
        return this;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public TTN recipient(String recipient) {
        this.sender = recipient;
        return this;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
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
            ", driverName='" + getDriverName() + "'" +
            ", productsAmount=" + getProductsAmount() +
            ", numberOfProductEntries=" + getNumberOfProductEntries() +
            ", dateTimeOfRegistration='" + getDateTimeOfRegistration() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
