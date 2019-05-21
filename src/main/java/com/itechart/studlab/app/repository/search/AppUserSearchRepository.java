package com.itechart.studlab.app.repository.search;

import com.itechart.studlab.app.domain.AppUser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AppUser entity.
 */
public interface AppUserSearchRepository extends ElasticsearchRepository<AppUser, Long> {
}
