package com.itechart.studlab.app.repository;

import com.itechart.studlab.app.domain.TTN;
import com.itechart.studlab.app.domain.enumeration.TtnStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the TTN entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TTNRepository extends JpaRepository<TTN, Long> {

    List<TTN> findAllByStatus(TtnStatus status);

}
