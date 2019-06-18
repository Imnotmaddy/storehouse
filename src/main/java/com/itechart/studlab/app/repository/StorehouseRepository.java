package com.itechart.studlab.app.repository;

import com.itechart.studlab.app.domain.Storehouse;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the Storehouse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StorehouseRepository extends JpaRepository<Storehouse, Long> {

    List<Storehouse> findAllByCompanyNameIs(String companyName);

    Optional<Storehouse> findAllByIdIs (Long id);
}
