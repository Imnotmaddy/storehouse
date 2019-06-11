package com.itechart.studlab.app.repository.search;

import com.itechart.studlab.app.domain.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Product entity.
 */
public interface ProductSearchRepository extends ElasticsearchRepository<Product, Long> {
}
