package grupo1.aps2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import grupo1.aps2.dto.DTOAula;
import grupo1.aps2.model.AulaEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA,
    unmappedSourcePolicy = ReportingPolicy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AulaMapper {
    DTOAula.CadastroAulaDTO map(AulaEntity source);
    AulaEntity map(DTOAula.CadastroAulaDTO source);
}
