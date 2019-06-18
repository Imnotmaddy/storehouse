package com.itechart.studlab.app.service.mapper;

import com.itechart.studlab.app.domain.*;
import com.itechart.studlab.app.service.dto.TransportDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Transport and its DTO TransportDTO.
 */
@Mapper(componentModel = "spring", uses = {TransporterMapper.class})
public interface TransportMapper extends EntityMapper<TransportDTO, Transport> {

    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "company.companyName", target = "transporterCompanyName")
    TransportDTO toDto(Transport transport);

    @Mapping(source = "companyId", target = "company")
    Transport toEntity(TransportDTO transportDTO);

    default Transport fromId(Long id) {
        if (id == null) {
            return null;
        }
        Transport transport = new Transport();
        transport.setId(id);
        return transport;
    }
}
