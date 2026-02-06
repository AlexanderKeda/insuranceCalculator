package org.javaguru.blacklist.core.api.command;

import org.javaguru.blacklist.core.api.dto.PersonDTO;

public record PersonCheckResult(
        PersonDTO person,
        boolean blackListed
) {
}
