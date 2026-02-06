package org.javaguru.blacklist.core.api.command;

import org.javaguru.blacklist.core.api.dto.PersonDTO;

public record PersonAddCommand(
        PersonDTO person
) {
}
