package com.itechart.studlab.app.service.mapper;

import com.itechart.studlab.app.domain.*;
import com.itechart.studlab.app.service.dto.ProductDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Product and its DTO ProductDTO.
 */
@Mapper(componentModel = "spring", uses = {ActMapper.class, StorageRoomMapper.class, TTNMapper.class})
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {

    @Mapping(source = "act.id", target = "actId")
    @Mapping(source = "storageRoom.id", target = "storageRoomId")
    @Mapping(source = "storageRoom.roomNumber", target = "storageRoomRoomNumber")
    @Mapping(source = "TTN.id", target = "tTNId")
    @Mapping(source = "TTN.serialNumber", target = "tTNSerialNumber")
    ProductDTO toDto(Product product);

    @Mapping(source = "actId", target = "act")
    @Mapping(source = "storageRoomId", target = "storageRoom")
    @Mapping(source = "tTNId", target = "TTN")
    Product toEntity(ProductDTO productDTO);

    default Product fromId(Long id) {
        if (id == null) {
            return null;
        }
        Product product = new Product();
        product.setId(id);
        return product;
    }
}
