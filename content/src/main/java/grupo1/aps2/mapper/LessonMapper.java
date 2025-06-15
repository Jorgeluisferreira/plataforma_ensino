package grupo1.aps2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import grupo1.aps2.dto.DTOLesson;
import grupo1.aps2.model.LessonEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA,
    unmappedSourcePolicy = ReportingPolicy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LessonMapper {
    DTOLesson.CadastroAulaDTO map(LessonEntity source);
    LessonEntity map(DTOLesson.CadastroAulaDTO source);
}
