package com.itechart.studlab.app.repository;

import com.itechart.studlab.app.domain.Product;
import com.itechart.studlab.app.domain.TTN;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Product entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> getAllByTTNIs(TTN ttn);
  
    List<Product> findAllByStorageRoom_Storehouse_Id(Long storehouseId);
}
