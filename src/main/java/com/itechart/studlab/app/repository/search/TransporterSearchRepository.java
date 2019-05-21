package com.itechart.studlab.app.repository.search;

import com.itechart.studlab.app.domain.Transporter;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Transporter entity.
 */
public interface TransporterSearchRepository extends ElasticsearchRepository<Transporter, Long> {
}
