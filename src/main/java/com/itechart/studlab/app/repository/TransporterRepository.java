package com.itechart.studlab.app.repository;

import com.itechart.studlab.app.domain.Transporter;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Transporter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransporterRepository extends JpaRepository<Transporter, Long> {

}
