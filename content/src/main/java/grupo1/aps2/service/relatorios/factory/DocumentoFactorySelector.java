package grupo1.aps2.service.relatorios.factory;

import grupo1.aps2.exceptions.ExceptionUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;

@ApplicationScoped
public class DocumentoFactorySelector {
    @Inject
    Instance<DocumentoFactory> documentoFactories;

    public DocumentoFactory getFactoryForFileFormat(String fileFormatAbreviation) {
        return documentoFactories.stream()
                .filter(factory -> factory.getFileFormatAbreviation().equalsIgnoreCase(fileFormatAbreviation))
                .findFirst()
                .orElseThrow(() -> ExceptionUtil
                    .createWebApplicationException(400, "Formato de arquivo não suportado: " + fileFormatAbreviation));
    }
}
