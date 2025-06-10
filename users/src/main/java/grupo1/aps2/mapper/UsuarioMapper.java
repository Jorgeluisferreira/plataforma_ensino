package grupo1.aps2.mapper;

import static grupo1.aps2.dto.DTOUsuario.*;

import grupo1.aps2.model.UsuarioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA,
    unmappedSourcePolicy = ReportingPolicy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UsuarioMapper {

    UsuarioDTO map(UsuarioEntity source);
    UsuarioEntity map(UsuarioDTO source);

}
