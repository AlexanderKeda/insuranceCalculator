package org.javaguru.travel.insurance.core.validations;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.util.ErrorCodeUtil;
import org.javaguru.travel.insurance.core.util.Placeholder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ValidationErrorFactory {

    private final ErrorCodeUtil errorCodeUtil;

    public ValidationErrorDTO buildError(String errorCode) {
        return new ValidationErrorDTO(errorCode, errorCodeUtil.getErrorDescription(errorCode));
    }

    public ValidationErrorDTO buildError(String errorCode, List<Placeholder> placeholders) {
        return new ValidationErrorDTO(errorCode, errorCodeUtil.getErrorDescription(errorCode, placeholders));
    }
}
