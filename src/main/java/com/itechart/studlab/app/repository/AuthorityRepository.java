package com.itechart.studlab.app.repository;

import com.itechart.studlab.app.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {

    List<Authority> findAuthoritiesByNameIsIn(List<String> authorities);
}
