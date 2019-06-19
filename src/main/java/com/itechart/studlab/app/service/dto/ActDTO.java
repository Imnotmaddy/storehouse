package com.itechart.studlab.app.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import com.itechart.studlab.app.domain.enumeration.ActType;

/**
 * A DTO for the Act entity.
 */
public class ActDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate date;

    @NotNull
    private Double cost;

    @NotNull
    private ActType type;


    private List<ProductDTO> products;

    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public ActType getType() {
        return type;
    }

    public void setType(ActType type) {
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ActDTO actDTO = (ActDTO) o;
        if (actDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), actDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ActDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", cost=" + getCost() +
            ", type='" + getType() + "'" +
            ", user=" + getUserId() +
            ", products=" + getProducts() +
            "}";
    }
}
