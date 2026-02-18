package org.javaguru.travel.insurance.core.validations.blacklist;

import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;

import java.util.List;

public interface PersonBlacklistValidator {

    List<ValidationErrorDTO> validate(PersonDTO person);

}
