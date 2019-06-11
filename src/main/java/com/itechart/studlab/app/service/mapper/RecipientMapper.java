package com.itechart.studlab.app.service.mapper;

import com.itechart.studlab.app.domain.*;
import com.itechart.studlab.app.service.dto.RecipientDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Recipient and its DTO RecipientDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RecipientMapper extends EntityMapper<RecipientDTO, Recipient> {



    default Recipient fromId(Long id) {
        if (id == null) {
            return null;
        }
        Recipient recipient = new Recipient();
        recipient.setId(id);
        return recipient;
    }
}
