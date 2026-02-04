package org.javaguru.doc.generator.core.services.generate.pdf;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.doc.generator.core.dto.AgreementDTO;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Component
@RequiredArgsConstructor(access = AccessLevel.MODULE)
class AgreementHtmlRenderer {

    private final SpringTemplateEngine templateEngine;

    String render(AgreementDTO agreement) {
        Context context = new Context();
        context.setVariable("agreement", agreement);
        return templateEngine.process("proposal", context);
    }

}
