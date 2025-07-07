package grupo1.aps2.service.relatorios.factory;

import grupo1.aps2.exceptions.ExceptionUtil;
import grupo1.aps2.service.relatorios.DocumentoTemplate;
import jakarta.ws.rs.WebApplicationException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;

public class DocumentoPDFFactory implements DocumentoFactory {


    @Override
    public ByteArrayOutputStream create(DocumentoTemplate doc) throws WebApplicationException {
        DocumentoHTMLFactory htmlFactory = new DocumentoHTMLFactory();
        if (doc == null) {
            throw new WebApplicationException("DocumentoTemplate não pode ser nulo", 400);
        }
        String htmlContent = htmlFactory.generateHtml(doc);
        Document document = Jsoup.parse(htmlContent);
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            SharedContext sharedContext = renderer.getSharedContext();
            sharedContext.setPrint(true);
            sharedContext.setInteractive(false);
            renderer.setDocumentFromString(document.html());
            renderer.layout();
            renderer.createPDF(outputStream);
            return outputStream;
        } catch (IOException e) {
            ExceptionUtil.throwException(400, "Erro ao gerar PDF: " + e.getMessage());
            throw new IllegalArgumentException();
        }
    }
}
