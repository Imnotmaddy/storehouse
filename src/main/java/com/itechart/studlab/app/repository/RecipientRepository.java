package com.itechart.studlab.app.repository;

import com.itechart.studlab.app.domain.Recipient;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Recipient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecipientRepository extends JpaRepository<Recipient, Long> {

}
