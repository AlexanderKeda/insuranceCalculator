package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;

import java.math.BigDecimal;

interface MedicalRiskElement {

    BigDecimal calculate(AgreementDTO agreement, PersonDTO person);

}