package com.itechart.studlab.app.service.dto;
import java.io.Serializable;
import java.util.Objects;
import com.itechart.studlab.app.domain.enumeration.ProductState;
import com.itechart.studlab.app.domain.enumeration.Facility;

/**
 * A DTO for the Product entity.
 */
public class ProductDTO implements Serializable {

    private Long id;

    private ProductState state;

    private Integer daysInStorage;

    private Double cost;

    private Facility requiredFacility;

    private Float weight;

    private String name;


    private Long tTNId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
            ", state='" + getState() + "'" +
            ", daysInStorage=" + getDaysInStorage() +
            ", cost=" + getCost() +
            ", requiredFacility='" + getRequiredFacility() + "'" +
            ", weight=" + getWeight() +
            ", name='" + getName() + "'" +
            ", tTN=" + getTTNId() +
            "}";
    }
}
