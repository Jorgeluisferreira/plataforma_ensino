package grupo1.aps2.controller;

import grupo1.aps2.dto.DTOUsuario;
import grupo1.aps2.mapper.UsuarioMapper;
import grupo1.aps2.model.UsuarioEntity;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("users")
public class UsuarioResource {

    @Inject
    UsuarioMapper usuarioMapper;

    @GET
    @Path("/{idUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public DTOUsuario.UsuarioDTO getUsuarioById(@PathParam("idUsuario") Long idUsuario) {
        UsuarioEntity user = new UsuarioEntity();
        user.setId(1L);
        user.setNome("Gay");
        user.setEmail("gay@emailgay.com");
        return usuarioMapper.map(user);
    }
}
