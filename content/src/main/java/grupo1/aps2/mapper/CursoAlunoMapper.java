package grupo1.aps2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import grupo1.aps2.dto.DTOCursoAluno;
import grupo1.aps2.model.CursoAlunoEntity;


@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA,
    unmappedSourcePolicy = ReportingPolicy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CursoAlunoMapper {
    DTOCursoAluno.CursoAlunoDTO map(CursoAlunoEntity source);
    CursoAlunoEntity map(DTOCursoAluno.CursoAlunoDTO source);
}
