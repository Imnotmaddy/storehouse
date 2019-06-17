package com.itechart.studlab.app.service.mapper;

import com.itechart.studlab.app.domain.*;
import com.itechart.studlab.app.service.dto.StorehouseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Storehouse and its DTO StorehouseDTO.
 */
@Mapper(componentModel = "spring", uses = {StorageRoomMapper.class, UserMapper.class})
public interface StorehouseMapper extends EntityMapper<StorehouseDTO, Storehouse> {

    @Mapping(source = "owners", target = "owners")
    @Mapping(source = "dispatchers", target = "dispatchers")
    @Mapping(source = "managers", target = "managers")
    @Mapping(source = "supervisors", target = "supervisors")
    @Mapping(source = "rooms", target = "rooms")
    StorehouseDTO toDto(Storehouse storehouse);

    @Mapping(source = "owners", target = "owners")
    @Mapping(source = "dispatchers", target = "dispatchers")
    @Mapping(source = "managers", target = "managers")
    @Mapping(source = "supervisors", target = "supervisors")
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
