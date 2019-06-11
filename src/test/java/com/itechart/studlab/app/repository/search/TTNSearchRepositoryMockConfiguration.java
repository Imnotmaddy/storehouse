package com.itechart.studlab.app.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of TTNSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class TTNSearchRepositoryMockConfiguration {

    @MockBean
    private TTNSearchRepository mockTTNSearchRepository;

}
