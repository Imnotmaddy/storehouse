package com.itechart.studlab.app.repository.search;

import com.itechart.studlab.app.domain.Driver;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Driver entity.
 */
public interface DriverSearchRepository extends ElasticsearchRepository<Driver, Long> {
}
