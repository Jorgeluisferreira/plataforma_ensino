package grupo1.aps2.mapper;

import static grupo1.aps2.dto.DTOUsuario.*;

import grupo1.aps2.model.UsuarioEntity;
import io.quarkus.elytron.security.common.BcryptUtil;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA,
    unmappedSourcePolicy = ReportingPolicy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UsuarioMapper {

    CadastroUsuarioDTO toCadastro(UsuarioEntity source);
    LoginUsuarioDTO toLogin(UsuarioEntity source);

    @Mapping(target = "senha", qualifiedByName = "BcryptHash")
    UsuarioEntity fromCadastro(CadastroUsuarioDTO source);
    UsuarioEntity fromLogin(LoginUsuarioDTO source);

    @Named("BcryptHash")
    default String hashSource(String source) {
        return BcryptUtil.bcryptHash(source);
    }
}
