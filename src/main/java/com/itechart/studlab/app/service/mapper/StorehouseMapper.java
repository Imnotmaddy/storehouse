package com.itechart.studlab.app.service.mapper;

import com.itechart.studlab.app.domain.*;
import com.itechart.studlab.app.service.dto.StorehouseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Storehouse and its DTO StorehouseDTO.
 */
@Mapper(componentModel = "spring", uses = {AppUserMapper.class})
public interface StorehouseMapper extends EntityMapper<StorehouseDTO, Storehouse> {

    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "owner.user.login", target = "ownerName")
    @Mapping(source = "administrator.id", target = "administratorId")
    @Mapping(source = "administrator.user.login", target = "administratorName")
    @Mapping(source = "dispatcher.id", target = "dispatcherId")
    @Mapping(source = "dispatcher.user.login", target = "dispatcherName")
    @Mapping(source = "manager.id", target = "managerId")
    @Mapping(source = "manager.user.login", target = "managerName")
    @Mapping(source = "supervisor.id", target = "supervisorId")
    @Mapping(source = "supervisor.user.login", target = "supervisorName")
    StorehouseDTO toDto(Storehouse storehouse);

    @Mapping(source = "ownerId", target = "owner")
    @Mapping(source = "administratorId", target = "administrator")
    @Mapping(source = "dispatcherId", target = "dispatcher")
    @Mapping(source = "managerId", target = "manager")
    @Mapping(source = "supervisorId", target = "supervisor")
    @Mapping(target = "rooms", ignore = true)
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
