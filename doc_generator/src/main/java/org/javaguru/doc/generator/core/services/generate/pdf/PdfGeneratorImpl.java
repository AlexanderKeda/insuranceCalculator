package org.javaguru.doc.generator.core.services.generate.pdf;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import lombok.extern.slf4j.Slf4j;
import org.javaguru.doc.generator.core.dto.AgreementDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;


@Component
@Slf4j
class PdfGeneratorImpl implements PdfGenerator {

    private final AgreementHtmlRenderer htmlRenderer;
    private final String folderPath;

    PdfGeneratorImpl(AgreementHtmlRenderer htmlRenderer,
                            @Value("${proposals.directory.path}") String folderPath) {
        this.htmlRenderer = htmlRenderer;
        this.folderPath = folderPath;
    }

    @Override
    public void generate(AgreementDTO agreement) throws IOException {
        try (FileOutputStream os = new FileOutputStream(pathCreate(agreement));
                BufferedOutputStream bos = new BufferedOutputStream(os)) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withHtmlContent(
                    htmlRenderer.render(agreement),
                    "/"
                    );
            builder.toStream(bos);
            builder.run();
        } catch (IOException e) {
            log.error("Failed to generate pdf", e);
            throw e;
        }
    }

    private String pathCreate(AgreementDTO agreement) {
        return folderPath +
                "/agreement-proposal-" +
                agreement.uuid() +
                ".pdf";
    }
}
