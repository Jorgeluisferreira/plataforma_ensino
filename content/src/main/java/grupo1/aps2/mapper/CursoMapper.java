package grupo1.aps2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import grupo1.aps2.dto.DTOCurso;
import grupo1.aps2.model.CursoEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA,
    unmappedSourcePolicy = ReportingPolicy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CursoMapper {
    DTOCurso.CursoDTO map(CursoEntity source);
    CursoEntity map(DTOCurso.CadastroCursoDTO source);
}
