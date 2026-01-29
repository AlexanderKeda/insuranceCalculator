package org.javaguru.travel.insurance.core.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

@Component
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ErrorCodeUtil {

    private final Properties errorDescriptions;

    public ErrorCodeUtil() throws IOException {
        errorDescriptions = PropertiesLoaderUtils.loadAllProperties("errorCodes.properties");
    }

    public String getErrorDescription(String errorCode) {
        return errorDescriptions.containsKey(errorCode)
                ? errorDescriptions.getProperty(errorCode)
                : "Unknown error code!";
    }

    public String getErrorDescription(String errorCode, List<Placeholder> placeholders) {
        return errorDescriptions.containsKey(errorCode)
                ? replacePlaceholders(errorDescriptions.getProperty(errorCode), placeholders)
                : "Unknown error code!";
    }

    private String replacePlaceholders(String description, List<Placeholder> placeholders) {
        for (Placeholder placeholder : placeholders) {
            description = description.replace("{" + placeholder.getPlaceholderName() + "}",
                    placeholder.getPlaceholderValue());
        }
        return description;
    }
}
