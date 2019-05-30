package com.itechart.studlab.app.service.mapper;

import com.itechart.studlab.app.domain.*;
import com.itechart.studlab.app.service.dto.TransporterDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Transporter and its DTO TransporterDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TransporterMapper extends EntityMapper<TransporterDTO, Transporter> {


    @Mapping(target = "vehicles", ignore = true)
    Transporter toEntity(TransporterDTO transporterDTO);

    default Transporter fromId(Long id) {
        if (id == null) {
            return null;
        }
        Transporter transporter = new Transporter();
        transporter.setId(id);
        return transporter;
    }
}
