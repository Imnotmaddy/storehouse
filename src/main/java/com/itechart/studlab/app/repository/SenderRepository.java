package com.itechart.studlab.app.repository;

import com.itechart.studlab.app.domain.Sender;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Sender entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SenderRepository extends JpaRepository<Sender, Long> {

}
