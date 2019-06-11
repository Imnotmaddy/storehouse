package com.itechart.studlab.app.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the AppUser entity.
 */
public class AppUserDTO implements Serializable {

    private Long id;

    private LocalDate birthdate;

    private Boolean isSuspended;

    private Double money;


    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public Boolean isIsSuspended() {
        return isSuspended;
    }

    public void setIsSuspended(Boolean isSuspended) {
        this.isSuspended = isSuspended;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AppUserDTO appUserDTO = (AppUserDTO) o;
        if (appUserDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), appUserDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AppUserDTO{" +
            "id=" + getId() +
            ", birthdate='" + getBirthdate() + "'" +
            ", isSuspended='" + isIsSuspended() + "'" +
            ", money=" + getMoney() +
            ", user=" + getUserId() +
            "}";
    }
}
