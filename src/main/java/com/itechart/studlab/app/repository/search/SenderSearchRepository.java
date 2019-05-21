package com.itechart.studlab.app.repository.search;

import com.itechart.studlab.app.domain.Sender;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Sender entity.
 */
public interface SenderSearchRepository extends ElasticsearchRepository<Sender, Long> {
}
