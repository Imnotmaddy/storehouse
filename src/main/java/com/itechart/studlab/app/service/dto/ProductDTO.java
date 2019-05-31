package com.itechart.studlab.app.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.itechart.studlab.app.domain.enumeration.ProductState;
import com.itechart.studlab.app.domain.enumeration.Facility;

/**
 * A DTO for the Product entity.
 */
public class ProductDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Integer quantity;

    private ProductState state;

    private Integer daysInStorage;

    @NotNull
    private Double cost;

    @NotNull
    private Facility requiredFacility;

    @NotNull
    private Double weight;


    private Long actId;

    private Long storageRoomId;

    private Long tTNId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public ProductState getState() {
        return state;
    }

    public void setState(ProductState state) {
        this.state = state;
    }

    public Integer getDaysInStorage() {
        return daysInStorage;
    }

    public void setDaysInStorage(Integer daysInStorage) {
        this.daysInStorage = daysInStorage;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Facility getRequiredFacility() {
        return requiredFacility;
    }

    public void setRequiredFacility(Facility requiredFacility) {
        this.requiredFacility = requiredFacility;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Long getActId() {
        return actId;
    }

    public void setActId(Long actId) {
        this.actId = actId;
    }

    public Long getStorageRoomId() {
        return storageRoomId;
    }

    public void setStorageRoomId(Long storageRoomId) {
        this.storageRoomId = storageRoomId;
    }

    public Long getTTNId() {
        return tTNId;
    }

    public void setTTNId(Long tTNId) {
        this.tTNId = tTNId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductDTO productDTO = (ProductDTO) o;
        if (productDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", quantity=" + getQuantity() +
            ", state='" + getState() + "'" +
            ", daysInStorage=" + getDaysInStorage() +
            ", cost=" + getCost() +
            ", requiredFacility='" + getRequiredFacility() + "'" +
            ", weight=" + getWeight() +
            ", act=" + getActId() +
            ", storageRoom=" + getStorageRoomId() +
            ", tTN=" + getTTNId() +
            "}";
    }
}