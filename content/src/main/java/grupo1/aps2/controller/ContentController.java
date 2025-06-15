package grupo1.aps2.controller;

import java.util.List;

import org.jboss.resteasy.reactive.RestResponse.Status;

import grupo1.aps2.dto.DTOCourse;
import grupo1.aps2.dto.DTOLesson;
import grupo1.aps2.mapper.ContentMapper;
import grupo1.aps2.mapper.CourseMapper;
import grupo1.aps2.mapper.LessonMapper;
import grupo1.aps2.model.CourseEntity;
import grupo1.aps2.model.LessonEntity;
import grupo1.aps2.repository.ContentRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@Path("content")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ContentController {
    
    @Inject
    ContentMapper contentMapper;
    @Inject
    CourseMapper courseMapper;
    @Inject
    LessonMapper lessonMapper;


    @Inject
    ContentRepository repository;

    @POST
    @Path("/cadastrarCurso")
    public Response cadastrarCurso(@Valid DTOCourse.CadastroCursoDTO dto){
        CourseEntity conteudo = courseMapper.map(dto);
        repository.save(conteudo);
        return Response.status(Status.CREATED).build();
    }

    @Path("/cadastrarAula")
    public Response cadastrarAula(@Valid DTOLesson.CadastroAulaDTO dto){
        LessonEntity conteudo = lessonMapper.map(dto);
        repository.save(conteudo);
        return Response.status(Status.CREATED).build();
    }

    @GET
    @Path("/lerCursos")
    public List<DTOCourse.CadastroCursoDTO> listCourses() {
        return repository.listAllCourses()
                         .stream()
                         .map(courseMapper::map)
                         .toList();
    }

    @GET
    @Path("/lerAulas")
    public List<DTOLesson.CadastroAulaDTO> listLessons() {
        return repository.listAllLessons()
                         .stream()
                         .map(lessonMapper::map)
                         .toList();
    }
}
