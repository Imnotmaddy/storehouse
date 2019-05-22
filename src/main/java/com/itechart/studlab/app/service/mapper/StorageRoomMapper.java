package com.itechart.studlab.app.service.mapper;

import com.itechart.studlab.app.domain.*;
import com.itechart.studlab.app.service.dto.StorageRoomDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity StorageRoom and its DTO StorageRoomDTO.
 */
@Mapper(componentModel = "spring", uses = {StorehouseMapper.class})
public interface StorageRoomMapper extends EntityMapper<StorageRoomDTO, StorageRoom> {

    @Mapping(source = "storehouse.id", target = "storehouseId")
    StorageRoomDTO toDto(StorageRoom storageRoom);

    @Mapping(source = "storehouseId", target = "storehouse")
    StorageRoom toEntity(StorageRoomDTO storageRoomDTO);

    default StorageRoom fromId(Long id) {
        if (id == null) {
            return null;
        }
        StorageRoom storageRoom = new StorageRoom();
        storageRoom.setId(id);
        return storageRoom;
    }
}
