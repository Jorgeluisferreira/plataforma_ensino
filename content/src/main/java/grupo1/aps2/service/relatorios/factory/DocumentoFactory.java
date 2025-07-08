package grupo1.aps2.service.relatorios.factory;

import grupo1.aps2.dto.DTODocumento;
import grupo1.aps2.dto.DTODocumento.DocumentoDTO;
import grupo1.aps2.service.relatorios.DocumentoTemplate;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.WebApplicationException;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public interface DocumentoFactory {

    public DocumentoDTO create(DocumentoTemplate doc) throws WebApplicationException;
    String getFileFormatAbreviation();
    String getMediaType();
}