package com.itechart.studlab.app.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;
import com.itechart.studlab.app.domain.enumeration.ActType;

/**
 * A DTO for the Act entity.
 */
public class ActDTO implements Serializable {

    private Long id;

    private LocalDate date;

    private Double cost;

    private ActType type;


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

    public void setUserId(Long appUserId) {
        this.userId = appUserId;
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
            "}";
    }
}
