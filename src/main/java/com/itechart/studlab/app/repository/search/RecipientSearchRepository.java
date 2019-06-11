package com.itechart.studlab.app.repository.search;

import com.itechart.studlab.app.domain.Recipient;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Recipient entity.
 */
public interface RecipientSearchRepository extends ElasticsearchRepository<Recipient, Long> {
}
