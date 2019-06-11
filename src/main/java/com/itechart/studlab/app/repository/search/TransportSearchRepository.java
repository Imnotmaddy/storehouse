package com.itechart.studlab.app.repository.search;

import com.itechart.studlab.app.domain.Transport;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Transport entity.
 */
public interface TransportSearchRepository extends ElasticsearchRepository<Transport, Long> {
}
