package org.git.api.utils;

import org.springframework.web.reactive.function.client.WebClient;

public class WebClientUtils {

    public static WebClient getWebClient(String url) {
        return WebClient.builder().baseUrl(url).build();
    }
}
