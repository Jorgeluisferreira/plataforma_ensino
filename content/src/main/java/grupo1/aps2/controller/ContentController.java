package grupo1.aps2.controller;

import java.util.List;

import org.jboss.resteasy.reactive.RestResponse.Status;

import grupo1.aps2.dto.DTOAula;
import grupo1.aps2.dto.DTOCurso;
import grupo1.aps2.dto.DTOCurso.CursoDTO;
import grupo1.aps2.dto.DTOCursoAluno.CursoAlunoDTO;
import grupo1.aps2.dto.DTODocumento.DocumentoDTO;
import grupo1.aps2.dto.DTOUsuario.UsuarioDTO;
import grupo1.aps2.exceptions.ExceptionUtil;
import grupo1.aps2.mapper.AulaMapper;
import grupo1.aps2.mapper.CursoAlunoMapper;
import grupo1.aps2.mapper.CursoMapper;
import grupo1.aps2.model.AulaEntity;
import grupo1.aps2.model.CursoAlunoEntity;
import grupo1.aps2.model.CursoEntity;
import grupo1.aps2.repository.ContentRepository;
import grupo1.aps2.service.ContentService;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@Path("content")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ContentController {
    
    @Inject
    CursoMapper cursoMapper;
    @Inject
    AulaMapper aulaMapper;
    @Inject
    CursoAlunoMapper cursoAlunoMapper;

    @Inject
    ContentService contentService;
    @Inject
    ContentRepository repository;

    @POST
    @Path("/cadastrarCurso")
    public Response cadastrarCurso(@Valid DTOCurso.CadastroCursoDTO dto){
        CursoEntity conteudo = cursoMapper.map(dto);

        conteudo = repository.save(conteudo);

        return Response.status(Status.CREATED).entity(cursoMapper.map(conteudo)).build();
    }

    @POST
    @Path("/{curso_id}/adicionarAula")
    public Response adicionarAula(@Valid DTOAula.CadastroAulaDTO dto){
        AulaEntity conteudo = aulaMapper.map(dto);
        repository.save(conteudo);
        return Response.status(Status.CREATED).build();
    }

    @GET
    @Path("/lerCursos")
    public List<CursoDTO> listCourses() {
        return repository.listAllCourses()
                         .stream()
                         .map(cursoMapper::map)
                         .toList();
    }

    @GET
    @Path("/lerCurso/{curso_id}")
    public CursoEntity procuraCursoPorId(@PathParam("curso_id") Long curso_id){
        return repository.searchCursoById(curso_id);
    }

    @GET
    @Path("/lerAula/{aula_id}")
    public AulaEntity procuraAulaPorId(@PathParam("aula_id") Long aula_id){
        return repository.searchAulaById(aula_id);
    }

    @GET
    @Path("/lerAulaPorCurso/{Curso_id}")
    public List<AulaEntity> procuraAulaPorCurso(@PathParam("Curso_id") Long curso_id){
        return repository.searchAulaByCurso(curso_id);
    }

    @GET
    @Path("/lerAulas")
    public List<DTOAula.CadastroAulaDTO> listLessons() {
        return repository.listAllLessons()
                         .stream()
                         .map(aulaMapper::map)
                         .toList();
    }

    @GET
    @Path("/lerCursosUsuario")
    public List<CursoEntity> listarCursosPorUsuario(@Context ContainerRequestContext requestContext) {
        UsuarioDTO usuario = (UsuarioDTO) requestContext.getProperty("usuario");
        return CursoAlunoEntity.find("usuarioId", usuario.getId())
                .stream()
                .map(ca -> ((CursoAlunoEntity) ca).getCurso())
                .toList();
    }


    @POST
    @Path("/assinarCurso/{curso_id}")
    public Response assinarCurso (@Context ContainerRequestContext requestContext, @PathParam("curso_id") Long cursoId) {

        UsuarioDTO usuario = (UsuarioDTO) requestContext.getProperty("usuario");

        List<CursoAlunoEntity> l = CursoAlunoEntity.find("usuarioId = :usuarioId and curso.id = :cursoId",
                        Parameters.with("usuarioId", usuario.getId()).and("cursoId", cursoId))
                .list();

        if (!l.isEmpty()) ExceptionUtil.throwException(400, "Usuário já está matriculado neste curso.");

        CursoAlunoDTO entity = contentService.assinarCurso(usuario, cursoId);

        return Response.status(Status.CREATED).entity(entity).build();
    }

    @POST
    @Path("/concluirEtapa/{curso_id}")
    public Response concluirEtapa(@Context ContainerRequestContext requestContext, @PathParam("curso_id") Long cursoId) {

        UsuarioDTO usuario = (UsuarioDTO) requestContext.getProperty("usuario");

        List<CursoAlunoEntity> l = CursoAlunoEntity.find("usuarioId = :usuarioId and curso.id = :cursoId",
                        Parameters.with("usuarioId", usuario.getId()).and("cursoId", cursoId))
                .list();

        CursoAlunoDTO entity = null;
        if (l.isEmpty()) {
             entity = contentService.assinarCurso(usuario, cursoId);
        } else {
            entity = contentService.concluirEtapa(usuario, l.getFirst());
        }

        return Response.status(Status.CREATED).entity(entity).build();
    }

    @GET
    @Path("/gerarEstadoCurso/{idCurso}/{fileFormat}")
    @Produces()
    public Response gerarEstadoCurso(@PathParam("idCurso") Long idCurso, @Context ContainerRequestContext requestContext, @PathParam("fileFormat") String fileFormat) {
        DocumentoDTO dto = contentService.gerarEstadoCurso(idCurso, requestContext,fileFormat);
        byte[] content = dto.getBaos().toByteArray();
        return Response
                .ok(content)
                .type(dto.getMediaType())
                .build();
    }

    @GET
    @Path("/gerarListaMatriculas/{fileFormat}")
    @Produces(MediaType.TEXT_HTML)
    public Response gerarListaMatriculasHtml(@Context ContainerRequestContext requestContext, @PathParam("fileFormat") String fileFormat) {
        DocumentoDTO dto = contentService.gerarListaCursosMatriculado(requestContext, fileFormat);
        byte[] content = dto.getBaos().toByteArray();
        return Response
                .ok(content)
                .type(dto.getMediaType())
                .build();
    }

}   
