package com.itechart.studlab.app.repository.search;

import com.itechart.studlab.app.domain.TTN;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TTN entity.
 */
public interface TTNSearchRepository extends ElasticsearchRepository<TTN, Long> {
}
