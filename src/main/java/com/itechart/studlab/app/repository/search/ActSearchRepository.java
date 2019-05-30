package com.itechart.studlab.app.repository.search;

import com.itechart.studlab.app.domain.Act;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Act entity.
 */
public interface ActSearchRepository extends ElasticsearchRepository<Act, Long> {
}
