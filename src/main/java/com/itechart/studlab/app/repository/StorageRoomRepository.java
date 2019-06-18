package com.itechart.studlab.app.repository;

import com.itechart.studlab.app.domain.StorageRoom;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the StorageRoom entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StorageRoomRepository extends JpaRepository<StorageRoom, Long> {
        List<StorageRoom> findAllByStorehouseId(Long storehouseId);
}
