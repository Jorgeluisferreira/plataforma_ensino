package grupo1.aps2.mapper;

import grupo1.aps2.dto.DTOCursoAluno;
import grupo1.aps2.dto.DTOCursoAluno.CursoAlunoBodyItem;
import grupo1.aps2.dto.DTOCursoAluno.CursoAlunoDTO;
import grupo1.aps2.dto.DTOUsuario;
import grupo1.aps2.dto.DTOUsuario.UsuarioDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import grupo1.aps2.model.CursoAlunoEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA,
    unmappedSourcePolicy = ReportingPolicy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class CursoAlunoMapper {
    @Inject
    CursoMapper cursoMapper;

    @Inject
    AulaMapper aulaMapper;

    public CursoAlunoDTO map(CursoAlunoEntity source, UsuarioDTO usuario) {
        if (source == null) {
            return null;
        }

        CursoAlunoDTO dto = new CursoAlunoDTO();
        dto.setId(source.getId());
        dto.setCurso(cursoMapper.map(source.getCurso()));
        dto.setUsuario(usuario);
        dto.setStatus(source.getStatus());
        if (source.getDataHoraConclusao() != null && !source.getDataHoraConclusao().isBlank())
            dto.setDataHoraConclusao(LocalDateTime.parse(source.getDataHoraConclusao(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

        return dto;
    }

    public CursoAlunoBodyItem toBodyItem(CursoAlunoEntity source, UsuarioDTO usuario) {
        if (source == null) {
            return null;
        }

        CursoAlunoBodyItem dto = new CursoAlunoBodyItem();
        dto.setId(source.getId());
        dto.setCurso(cursoMapper.map(source.getCurso()));
        dto.setUsuario(usuario);
        dto.setStatus(source.getStatus());
        if (source.getDataHoraConclusao() != null && !source.getDataHoraConclusao().isBlank())
            dto.setDataHoraConclusao(LocalDateTime.parse(source.getDataHoraConclusao(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

        return dto;
    }

    public CursoAlunoDTO map(CursoAlunoEntity source, ContainerRequestContext ctx) {
        if (source == null) {
            return null;
        }

        UsuarioDTO usuario = ctx.getProperty("usuario") != null ? (UsuarioDTO) ctx.getProperty("usuario")
                : null;

        CursoAlunoDTO dto = new CursoAlunoDTO();
        dto.setId(source.getId());
        dto.setCurso(cursoMapper.map(source.getCurso()));
        dto.setUsuario(usuario);
        dto.setStatus(source.getStatus());
        dto.setDataHoraConclusao(LocalDateTime.parse(source.getDataHoraConclusao(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

        return dto;
    }

    public abstract CursoAlunoEntity map(CursoAlunoDTO source);
}
