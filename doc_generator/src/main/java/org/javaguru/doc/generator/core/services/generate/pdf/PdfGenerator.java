package org.javaguru.doc.generator.core.services.generate.pdf;

import org.javaguru.doc.generator.core.dto.AgreementDTO;

import java.io.IOException;

public interface PdfGenerator {

    void generate(AgreementDTO agreement) throws IOException;

}
