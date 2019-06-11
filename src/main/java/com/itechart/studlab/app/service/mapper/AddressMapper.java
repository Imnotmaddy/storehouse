package com.itechart.studlab.app.service.mapper;

import com.itechart.studlab.app.domain.*;
import com.itechart.studlab.app.service.dto.AddressDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Address and its DTO AddressDTO.
 */
@Mapper(componentModel = "spring", uses = {AppUserMapper.class})
public interface AddressMapper extends EntityMapper<AddressDTO, Address> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.user.login", target = "userName")
    AddressDTO toDto(Address address);

    @Mapping(source = "userId", target = "user")
    Address toEntity(AddressDTO addressDTO);

    default Address fromId(Long id) {
        if (id == null) {
            return null;
        }
        Address address = new Address();
        address.setId(id);
        return address;
    }
}
