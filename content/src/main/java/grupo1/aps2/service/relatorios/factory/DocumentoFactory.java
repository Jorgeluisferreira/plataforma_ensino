package grupo1.aps2.service.relatorios.factory;

import grupo1.aps2.service.relatorios.Documento;
import jakarta.ws.rs.WebApplicationException;

import java.io.ByteArrayOutputStream;

public interface DocumentoFactory {

    public ByteArrayOutputStream create(Documento doc) throws WebApplicationException;

    public static DocumentoFactory getFactoryForFileFormat(String fileFormatAbreviation) {
        return switch (fileFormatAbreviation.toLowerCase()) {
            case "pdf" -> new DocumentoPDFFactory();
            case "html" -> new DocumentoHTMLFactory();
            default -> new DocumentoHTMLFactory();
        };
    }
}