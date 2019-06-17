package com.itechart.studlab.app.service.mapper;

import com.itechart.studlab.app.domain.*;
import com.itechart.studlab.app.service.dto.TTNDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TTN and its DTO TTNDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, TransportMapper.class, TransporterMapper.class, ProductMapper.class})
public interface TTNMapper extends EntityMapper<TTNDTO, TTN> {

    @Mapping(source = "dispatcher.id", target = "dispatcherId")
    @Mapping(source = "dispatcher.lastName", target = "dispatcherLastName")
    @Mapping(source = "manager.id", target = "managerId")
    @Mapping(source = "manager.lastName", target = "managerLastName")
    @Mapping(source = "sender.id", target = "senderId")
    @Mapping(source = "sender.lastName", target = "senderLastName")
    @Mapping(source = "transport.id", target = "transportId")
    @Mapping(source = "transporter.id", target = "transporterId")
    @Mapping(source = "transporter.companyName", target = "transporterCompanyName")
    @Mapping(source = "products", target = "products")
    TTNDTO toDto(TTN tTN);

    @Mapping(source = "dispatcherId", target = "dispatcher")
    @Mapping(source = "managerId", target = "manager")
    @Mapping(source = "senderId", target = "sender")
    @Mapping(source = "transportId", target = "transport")
    @Mapping(source = "transporterId", target = "transporter")
    @Mapping(source = "products", target = "products")
    TTN toEntity(TTNDTO tTNDTO);

    default TTN fromId(Long id) {
        if (id == null) {
            return null;
        }
        TTN tTN = new TTN();
        tTN.setId(id);
        return tTN;
    }
}
