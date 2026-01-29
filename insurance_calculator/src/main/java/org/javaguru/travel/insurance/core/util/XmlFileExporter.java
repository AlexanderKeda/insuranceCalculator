package org.javaguru.travel.insurance.core.util;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;

@Component
public class XmlFileExporter {

    private final XmlMapper xmlMapper;

    XmlFileExporter() {
        xmlMapper = new XmlMapper();
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        xmlMapper.registerModule(new JavaTimeModule());
        xmlMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public void writeToXmlFile(Object data, Path filePath) throws IOException {
        xmlMapper.writeValue(filePath.toFile(), data);
    }

}
