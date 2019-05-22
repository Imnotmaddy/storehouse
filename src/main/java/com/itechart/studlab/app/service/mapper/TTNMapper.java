package com.itechart.studlab.app.service.mapper;

import com.itechart.studlab.app.domain.*;
import com.itechart.studlab.app.service.dto.TTNDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TTN and its DTO TTNDTO.
 */
@Mapper(componentModel = "spring", uses = {AppUserMapper.class, TransportMapper.class, TransporterMapper.class, DriverMapper.class, RecipientMapper.class})
public interface TTNMapper extends EntityMapper<TTNDTO, TTN> {

    @Mapping(source = "storehouseDispatcher.id", target = "storehouseDispatcherId")
    @Mapping(source = "storehouseDispatcher.user.login", target = "storehouseDispatcherName")
    @Mapping(source = "manager.id", target = "managerId")
    @Mapping(source = "manager.user.login", target = "managerName")
    @Mapping(source = "sender.id", target = "senderId")
    @Mapping(source = "sender.user.login", target = "senderName")
    @Mapping(source = "transport.id", target = "transportId")
    @Mapping(source = "transport.facility", target = "transportFacility")
    @Mapping(source = "transporter.id", target = "transporterId")
    @Mapping(source = "transporter.companyName", target = "transporterCompanyName")
    @Mapping(source = "driver.id", target = "driverId")
    @Mapping(source = "driver.name", target = "driverName")
    @Mapping(source = "recipient.id", target = "recipientId")
    @Mapping(source = "recipient.companyName", target = "recipientCompanyName")
    TTNDTO toDto(TTN tTN);

    @Mapping(source = "storehouseDispatcherId", target = "storehouseDispatcher")
    @Mapping(source = "managerId", target = "manager")
    @Mapping(source = "senderId", target = "sender")
    @Mapping(source = "transportId", target = "transport")
    @Mapping(source = "transporterId", target = "transporter")
    @Mapping(source = "driverId", target = "driver")
    @Mapping(source = "recipientId", target = "recipient")
    @Mapping(target = "products", ignore = true)
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
