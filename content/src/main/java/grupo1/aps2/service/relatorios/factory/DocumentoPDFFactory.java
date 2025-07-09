package grupo1.aps2.service.relatorios.factory;

import grupo1.aps2.dto.DTODocumento;
import grupo1.aps2.dto.DTODocumento.DocumentoDTO;
import grupo1.aps2.exceptions.ExceptionUtil;
import grupo1.aps2.service.relatorios.DocumentoTemplate;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;

@ApplicationScoped
public class DocumentoPDFFactory implements DocumentoFactory {

    @Inject
    DocumentoHTMLFactory htmlFactory;

    @Override
    public DocumentoDTO create(DocumentoTemplate doc) throws WebApplicationException {
        if (doc == null) {
            throw new WebApplicationException("DocumentoTemplate não pode ser nulo", 400);
        }
        String htmlContent = htmlFactory.generateHtml(doc);
        Document document = Jsoup.parse(htmlContent);
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            SharedContext sharedContext = renderer.getSharedContext();
            sharedContext.setPrint(true);
            sharedContext.setInteractive(false);
            renderer.setDocumentFromString(document.html());
            renderer.layout();
            renderer.createPDF(baos);
            return new DocumentoDTO(baos, getMediaType());
        } catch (IOException e) {
            throw ExceptionUtil.createWebApplicationException(400, "Erro ao gerar PDF: " + e.getMessage());
        }
    }

    @Override
    public String getFileFormatAbreviation() {
        return "pdf";
    }

    public String getMediaType() {
        return "application/pdf";
    }
}
