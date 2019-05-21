package com.itechart.studlab.app.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Sender entity.
 */
public class SenderDTO implements Serializable {

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

        SenderDTO senderDTO = (SenderDTO) o;
        if (senderDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), senderDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SenderDTO{" +
            "id=" + getId() +
            ", companyName='" + getCompanyName() + "'" +
            "}";
    }
}
