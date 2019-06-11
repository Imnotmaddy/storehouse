package com.itechart.studlab.app.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Transporter entity.
 */
public class TransporterDTO implements Serializable {

    private Long id;

    private String companyName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TransporterDTO transporterDTO = (TransporterDTO) o;
        if (transporterDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), transporterDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TransporterDTO{" +
            "id=" + getId() +
            ", companyName='" + getCompanyName() + "'" +
            "}";
    }
}
