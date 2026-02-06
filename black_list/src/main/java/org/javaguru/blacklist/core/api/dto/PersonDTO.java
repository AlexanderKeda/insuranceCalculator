package org.javaguru.blacklist.core.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PersonDTO(
        @NotBlank(message = "{person.firstName.notBlank}") String personFirstName,
        @NotBlank(message = "{person.lastName.notBlank}") String personLastName,
        @NotBlank(message = "{person.code.notBlank}") String personCode,
        @NotNull(message = "{person.birthDate.notNull}") LocalDate personBirthDate
) {
}
