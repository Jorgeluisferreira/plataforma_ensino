package grupo1.aps2.controller;

import grupo1.aps2.dto.DTOContent;
import grupo1.aps2.mapper.ContentMapper;
import grupo1.aps2.model.ContentEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@ApplicationScoped
@Path("content")
public class ContentController {
    
    @Inject
    ContentMapper contentMapper;

    @GET
    @Path("/{idContent}")
    @Produces(MediaType.APPLICATION_JSON)
    public DTOContent.ContentDTO getContentById(@PathParam("idContent") Long idContent) {
        ContentEntity content = new ContentEntity();
        content.setId(1L);
        content.setNome("Curso xyz");

        return contentMapper.map(content);
    }
}
