package grupo1.aps2.service.relatorios.factory;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import grupo1.aps2.exceptions.ExceptionUtil;
import grupo1.aps2.service.relatorios.Documento;
import jakarta.ws.rs.WebApplicationException;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class DocumentoPDFFactory implements DocumentoFactory {


    @Override
    public ByteArrayOutputStream create(Documento doc) throws WebApplicationException {
        DocumentoHTMLFactory htmlFactory = new DocumentoHTMLFactory();
        if (doc == null) {
            throw new WebApplicationException("Documento não pode ser nulo", 400);
        }

        ByteArrayOutputStream htmlBaos = htmlFactory.create(doc);
        String htmlContent = htmlBaos.toString(StandardCharsets.UTF_8);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withHtmlContent(htmlContent, "");
            builder.toStream(baos);
            builder.run();
            return baos;
        } catch (IOException e) {
            ExceptionUtil.throwException(400, "Erro ao gerar PDF: " + e.getMessage());
            throw new IllegalArgumentException();
        }
    }
}
