package org.javaguru.travel.insurance.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.client.RestClient;

import java.net.http.HttpClient;
import java.time.Duration;

@Configuration
@EnableRetry
public class BlacklistClientConfig {

    private final String baseUrl;
    private final int connectionTimeout;
    private final int readTimeout;

    public BlacklistClientConfig(
            @Value("${blacklist.client.base-url}") String baseUrl,
            @Value("${blacklist.client.connection-timeout}") int connectionTimeout,
            @Value("${blacklist.client.read-timeout}") int readTimeout) {
        this.baseUrl = baseUrl;
        this.connectionTimeout = connectionTimeout;
        this.readTimeout = readTimeout;
    }

    @Bean
    public RestClient blacklistRestClient(RestClient.Builder builder ) {
        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofMillis(connectionTimeout))
                .build();
        JdkClientHttpRequestFactory factory = new JdkClientHttpRequestFactory(httpClient);
        factory.setReadTimeout(readTimeout);
        return builder.
                requestFactory(factory)
                .baseUrl(baseUrl)
                .build();
    }

}
