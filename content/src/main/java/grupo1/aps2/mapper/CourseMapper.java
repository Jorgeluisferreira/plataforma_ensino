package grupo1.aps2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import grupo1.aps2.dto.DTOCourse;
import grupo1.aps2.model.CourseEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA,
    unmappedSourcePolicy = ReportingPolicy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CourseMapper {
    DTOCourse.CadastroCursoDTO map(CourseEntity source);
    CourseEntity map(DTOCourse.CadastroCursoDTO source);
}
