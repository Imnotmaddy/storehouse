package com.itechart.studlab.app.service.mapper;

import com.itechart.studlab.app.domain.*;
import com.itechart.studlab.app.service.dto.StorehouseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Storehouse and its DTO StorehouseDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, StorageRoomMapper.class})
public interface StorehouseMapper extends EntityMapper<StorehouseDTO, Storehouse> {

    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "owner.lastName", target = "ownerLastName")
    @Mapping(source = "dispatcher.id", target = "dispatcherId")
    @Mapping(source = "dispatcher.lastName", target = "dispatcherLastName")
    @Mapping(source = "manager.id", target = "managerId")
    @Mapping(source = "manager.lastName", target = "managerLastName")
    @Mapping(source = "supervisor.id", target = "supervisorId")
    @Mapping(source = "supervisor.lastName", target = "supervisorLastName")
    @Mapping(source = "rooms", target = "rooms")
    StorehouseDTO toDto(Storehouse storehouse);

    @Mapping(source = "ownerId", target = "owner")
    @Mapping(source = "dispatcherId", target = "dispatcher")
    @Mapping(source = "managerId", target = "manager")
    @Mapping(source = "supervisorId", target = "supervisor")
    @Mapping(source = "rooms", target = "rooms")
    Storehouse toEntity(StorehouseDTO storehouseDTO);

    default Storehouse fromId(Long id) {
        if (id == null) {
            return null;
        }
        Storehouse storehouse = new Storehouse();
        storehouse.setId(id);
        return storehouse;
    }
}
