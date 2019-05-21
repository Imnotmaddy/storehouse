package com.itechart.studlab.app.service.mapper;

import com.itechart.studlab.app.domain.*;
import com.itechart.studlab.app.service.dto.SenderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Sender and its DTO SenderDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SenderMapper extends EntityMapper<SenderDTO, Sender> {



    default Sender fromId(Long id) {
        if (id == null) {
            return null;
        }
        Sender sender = new Sender();
        sender.setId(id);
        return sender;
    }
}
