package grupo1.aps2.service;

import grupo1.aps2.dto.DTOCursoAluno;
import grupo1.aps2.dto.DTOCursoAluno.CursoAlunoBodyItem;
import grupo1.aps2.dto.DTOCursoAluno.CursoAlunoDTO;
import grupo1.aps2.dto.DTOUsuario;
import grupo1.aps2.exceptions.ExceptionUtil;
import grupo1.aps2.mapper.CursoAlunoMapper;
import grupo1.aps2.mapper.CursoMapper;
import grupo1.aps2.model.CursoAlunoEntity;
import grupo1.aps2.model.CursoEntity;
import grupo1.aps2.repository.ContentRepository;
import grupo1.aps2.service.estados.EstadoCursoEnum;
import grupo1.aps2.service.relatorios.DocumentoTemplate;
import grupo1.aps2.service.relatorios.EstadoCursoDocumentoTemplate;
import grupo1.aps2.service.relatorios.ListaCursosMatriculadoDocumentoTemplate;
import grupo1.aps2.service.relatorios.factory.DocumentoFactory;
import grupo1.aps2.service.relatorios.template.BodyItem;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@ApplicationScoped
public class ContentService {

    @Inject
    ContentRepository repository;

    @Inject
    CursoAlunoMapper cursoAlunoMapper;

    @Inject
    CursoMapper cursoMapper;

    public ByteArrayOutputStream gerarEstadoCurso(Long idCurso, ContainerRequestContext requestContext, String fileFormat) {
        CursoAlunoEntity qParams = new CursoAlunoEntity();
        DTOUsuario.UsuarioDTO usuario = (DTOUsuario.UsuarioDTO) requestContext.getProperty("usuario");

        qParams.setCurso(CursoEntity.findById(idCurso));
        if (qParams.getCurso() == null) {
            ExceptionUtil.throwException(400, "Curso não encontrado.");
        }
        qParams.setUsuarioId(usuario.getId());

        CursoAlunoEntity curso = CursoAlunoEntity
                .find("usuarioId = :usuarioId and curso.id = :cursoId",
                    Parameters.with("usuarioId", qParams.getUsuarioId()).and("cursoId", qParams.getCurso().getId()))
                .firstResult();

        DocumentoTemplate doc;

        if (curso == null || curso.getId() == null) {
            doc = new EstadoCursoDocumentoTemplate();
            doc.setUsuario(usuario);
            doc.setBodyContent(List.of((BodyItem)
                    new CursoAlunoBodyItem(null, usuario,
                            cursoMapper.map(qParams.getCurso()),
                            EstadoCursoEnum.NAO_MATRICULADO, null)));

            DocumentoFactory factory = DocumentoFactory.getFactoryForFileFormat(fileFormat);
            return factory.create(doc);

        }

        doc = curso.getStatus().toEstadoCurso().receberCertificado();
        doc.setUsuario(usuario);
        doc.setBodyContent(List.of((BodyItem) cursoAlunoMapper.toBodyItem(curso, usuario)));

        DocumentoFactory factory = DocumentoFactory.getFactoryForFileFormat(fileFormat);
        return factory.create(doc);
    }

    public ByteArrayOutputStream gerarListaCursosMatriculado(ContainerRequestContext requestContext, String fileFormat) {
        CursoAlunoEntity qParams = new CursoAlunoEntity();
        DTOUsuario.UsuarioDTO usuario = (DTOUsuario.UsuarioDTO) requestContext.getProperty("usuario");

        qParams.setUsuarioId(usuario.getId());

        List<CursoAlunoEntity> cursos = CursoAlunoEntity
                .find("usuarioId = :usuarioId",
                        Parameters.with("usuarioId", qParams.getUsuarioId())).list();

        DocumentoTemplate doc = new ListaCursosMatriculadoDocumentoTemplate(usuario,
                cursos.stream().map(c -> cursoAlunoMapper.toBodyItem(c, usuario)).toList());

        DocumentoFactory factory = DocumentoFactory.getFactoryForFileFormat(fileFormat);
        return factory.create(doc);
    }

    public CursoAlunoDTO assinarCurso (DTOUsuario.UsuarioDTO usuario, Long cursoId) {
        CursoEntity curso = repository.searchCursoById(cursoId);
        if (curso == null) {
            ExceptionUtil.throwException(400, "Curso não encontrado.");
        }
        System.out.println("Assinando curso: " + cursoId + " para o usuário: " + usuario.getId());

        CursoAlunoEntity entity = new CursoAlunoEntity();
        entity.setCurso(curso);
        entity.setUsuarioId(usuario.getId());
        entity.setStatus(EstadoCursoEnum.EM_ANDAMENTO);
        entity = repository.assinaCurso(entity);

        return cursoAlunoMapper.map(entity, usuario);
    }

    public CursoAlunoDTO concluirEtapa (DTOUsuario.UsuarioDTO usuario, CursoAlunoEntity curso) {
        System.out.println("Concluindo etapa: " + curso.getCurso().getNome() + " para o usuário: " + usuario.getId());

        curso.setStatus(curso.getStatus().toEstadoCurso().concluirEtapa().toEnum());

        validarCursoConcluido(curso);

        System.out.println(curso.getDataHoraConclusao());

        CursoAlunoEntity entity = repository.update(curso);

        return cursoAlunoMapper.map(entity, usuario);
    }

    private void validarCursoConcluido(CursoAlunoEntity curso) {
        if (curso.getStatus() == EstadoCursoEnum.CONCLUIDO) {
            curso.setDataHoraConclusao(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        }

    }
}
