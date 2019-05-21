package com.itechart.studlab.app.repository;

import com.itechart.studlab.app.domain.Act;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Act entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActRepository extends JpaRepository<Act, Long> {

}
