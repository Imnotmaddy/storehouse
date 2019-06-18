package com.itechart.studlab.app.repository;

import com.itechart.studlab.app.domain.Transporter;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Spring Data  repository for the Transporter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransporterRepository extends JpaRepository<Transporter, Long> {

    List<Transporter> findAllByDispatcherCompanyName(String dispatcherCompanyName);
    Transporter findFirstByDispatcherCompanyName(String companyName);

}
