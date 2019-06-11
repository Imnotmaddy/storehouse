package com.itechart.studlab.app.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of TransporterSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class TransporterSearchRepositoryMockConfiguration {

    @MockBean
    private TransporterSearchRepository mockTransporterSearchRepository;

}
