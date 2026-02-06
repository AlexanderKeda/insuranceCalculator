package org.javaguru.blacklist.core.api.command;

import org.javaguru.blacklist.core.api.dto.PersonDTO;

public record PersonCheckCommand(
        PersonDTO person
) {
}
