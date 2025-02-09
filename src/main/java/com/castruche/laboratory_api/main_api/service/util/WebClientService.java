package com.castruche.laboratory_api.main_api.service.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WebClientService {

    private static final Logger logger = LogManager.getLogger(WebClientService.class);

    public WebClientService() {
    }

    public WebClient initWebClient(String url, Integer maxInMemorySize) {
        return WebClient.builder()
                .codecs(configurer -> configurer.defaultCodecs()
                        .maxInMemorySize((maxInMemorySize!=null?5:16) * 1024 * 1024))
                .baseUrl(url).build();
    }



}
