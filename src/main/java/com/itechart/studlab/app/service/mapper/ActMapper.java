package com.itechart.studlab.app.service.mapper;

import com.itechart.studlab.app.domain.*;
import com.itechart.studlab.app.service.dto.ActDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Act and its DTO ActDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, ProductMapper.class})
public interface ActMapper extends EntityMapper<ActDTO, Act> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "products", target = "products")
    ActDTO toDto(Act act);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "products", target = "products")
    Act toEntity(ActDTO actDTO);

    default Act fromId(Long id) {
        if (id == null) {
            return null;
        }
        Act act = new Act();
        act.setId(id);
        return act;
    }
}
