package grupo1.aps2.service;

import grupo1.aps2.dto.DTOUsuario;
import grupo1.aps2.model.CursoAlunoEntity;
import grupo1.aps2.model.CursoEntity;
import grupo1.aps2.repository.ContentRepository;
import grupo1.aps2.service.relatorios.Documento;
import grupo1.aps2.service.relatorios.EstadoCursoDocumento;
import grupo1.aps2.service.relatorios.factory.DocumentoFactory;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;

import java.io.ByteArrayOutputStream;

@ApplicationScoped
public class ContentService {

    @Inject
    ContentRepository repository;

    public ByteArrayOutputStream gerarEstadoCurso(Long idCurso, ContainerRequestContext requestContext, String fileFormat) {
        CursoAlunoEntity qParams = new CursoAlunoEntity();
        DTOUsuario.UsuarioDTO usuario = (DTOUsuario.UsuarioDTO) requestContext.getProperty("usuario");

        qParams.setCurso(CursoEntity.findById(idCurso));

        qParams.setUsuarioId(usuario.getId());

        CursoAlunoEntity curso = CursoAlunoEntity
                .find("usuarioId = :usuarioId and curso.id = :cursoId",
                        Parameters.with("usuarioId", qParams.getUsuarioId())
                                .and("cursoId", qParams.getCurso().getId()))
                .firstResult();

        Documento doc = new EstadoCursoDocumento(usuario, curso);

        DocumentoFactory factory = DocumentoFactory.getFactoryForFileFormat(fileFormat);
        return factory.create(doc);
    }
}
