package com.itechart.studlab.app.repository.search;

import com.itechart.studlab.app.domain.Storehouse;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Storehouse entity.
 */
public interface StorehouseSearchRepository extends ElasticsearchRepository<Storehouse, Long> {
}
