package com.itechart.studlab.app.repository;

import com.itechart.studlab.app.domain.TTN;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TTN entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TTNRepository extends JpaRepository<TTN, Long> {

}
