package org.javaguru.travel.insurance.core.api.command;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;

public record TravelCalculatePremiumCoreCommand(AgreementDTO agreement) {

}
