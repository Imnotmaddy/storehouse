package com.itechart.studlab.app.repository.search;

import com.itechart.studlab.app.domain.StorageRoom;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the StorageRoom entity.
 */
public interface StorageRoomSearchRepository extends ElasticsearchRepository<StorageRoom, Long> {
}
