package com.itechart.studlab.app.service.mapper;

import com.itechart.studlab.app.domain.Storehouse;
import com.itechart.studlab.app.domain.User;
import com.itechart.studlab.app.service.dto.StorehouseDTO;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper for the entity Storehouse and its DTO StorehouseDTO.
 */
@Mapper(componentModel = "spring", uses = {StorageRoomMapper.class, UserMapper.class})
public interface StorehouseMapper extends EntityMapper<StorehouseDTO, Storehouse> {

    @Mapping(source = "employees", target = "employees")
    @Mapping(source = "rooms", target = "rooms")
    StorehouseDTO toDto(Storehouse storehouse);

    @Mapping(source = "employees", target = "employees")
    @Mapping(source = "rooms", target = "rooms")
    Storehouse toEntity(StorehouseDTO storehouseDTO);

    @BeforeMapping
    default void checkEmployeesNull(Storehouse storehouse) {
        if (storehouse.getEmployees() == null) {
            storehouse.setEmployees(new ArrayList<>(0));
        }
    }

    @BeforeMapping
    default void checkEmployees (StorehouseDTO storehouseDTO) {
        if (storehouseDTO.getEmployees() == null) {
            storehouseDTO.setEmployees(new ArrayList<>(0));
        }
    }

    default Storehouse fromId(Long id) {
        if (id == null) {
            return null;
        }
        Storehouse storehouse = new Storehouse();
        storehouse.setId(id);
        return storehouse;
    }
}
