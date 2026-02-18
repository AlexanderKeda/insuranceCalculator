package org.javaguru.travel.insurance.infrastructure.blacklist;

import org.javaguru.travel.insurance.core.api.dto.PersonDTO;

public record PersonCheckResultDTO(
        PersonDTO person,
        boolean blackListed
) {
}
