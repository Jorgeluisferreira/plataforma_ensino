package grupo1.aps2.controller;

import java.util.List;

import org.jboss.resteasy.reactive.RestResponse.Status;

import grupo1.aps2.dto.DTOAula;
import grupo1.aps2.dto.DTOCurso;
import grupo1.aps2.dto.DTOCursoAluno;
import grupo1.aps2.mapper.AulaMapper;
import grupo1.aps2.mapper.CursoAlunoMapper;
import grupo1.aps2.mapper.CursoMapper;
import grupo1.aps2.model.AulaEntity;
import grupo1.aps2.model.CursoAlunoEntity;
import grupo1.aps2.model.CursoEntity;
import grupo1.aps2.repository.ContentRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
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
    ContentRepository repository;

    @POST
    @Path("/cadastrarCurso")
    public Response cadastrarCurso(@Valid DTOCurso.CadastroCursoDTO dto){
        CursoEntity conteudo = cursoMapper.map(dto);
        repository.save(conteudo);
        return Response.status(Status.CREATED).build();
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
    public List<DTOCurso.CadastroCursoDTO> listCourses() {
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
    @Path("/lerAulas")
    public List<DTOAula.CadastroAulaDTO> listLessons() {
        return repository.listAllLessons()
                         .stream()
                         .map(aulaMapper::map)
                         .toList();
    }

    @POST
    @Path("/assinarCurso/{curso_id}")
    public Response assinarCurso (@Valid DTOCursoAluno.CursoAlunoDTO dto){
        CursoAlunoEntity entity = cursoAlunoMapper.map(dto);
        repository.assinaCurso(entity);
        return Response.status(Status.CREATED).build();
    }

}   
