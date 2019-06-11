package com.itechart.studlab.app.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of SenderSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class SenderSearchRepositoryMockConfiguration {

    @MockBean
    private SenderSearchRepository mockSenderSearchRepository;

}
