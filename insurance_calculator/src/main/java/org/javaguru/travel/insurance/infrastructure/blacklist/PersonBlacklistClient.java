package org.javaguru.travel.insurance.infrastructure.blacklist;

import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Component
class PersonBlacklistClient {

    private final RestClient restClient;
    private final String blacklistUri;

    public PersonBlacklistClient(
            RestClient restClient,
            @Value("${blacklist.client.uri}") String blacklistUri) {
        this.restClient = restClient;
        this.blacklistUri = blacklistUri;
    }

    @Retryable(
            maxAttemptsExpression = "${blacklist.client.retry-max-attempts:3}",
            backoff = @Backoff(
                    delayExpression = "${blacklist.client.retry-delay:1000}",
                    multiplierExpression = "${blacklist.client.retry-multiplier:2}"
            ),
            noRetryFor = HttpClientErrorException.class
    )
    public PersonCheckResultDTO checkPerson(PersonDTO personDTO) {
        return  restClient.post()
                .uri(blacklistUri)
                .contentType(MediaType.APPLICATION_JSON)
                .body(personDTO)
                .retrieve()
                .body(PersonCheckResultDTO.class);
    }


}
