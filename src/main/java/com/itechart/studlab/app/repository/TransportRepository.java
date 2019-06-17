package com.itechart.studlab.app.repository;

import com.itechart.studlab.app.domain.Transport;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Transport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransportRepository extends JpaRepository<Transport, Long> {

    List<Transport> findAllByCompany_DispatcherCompanyName(String dispatcherCompanyName);

}
