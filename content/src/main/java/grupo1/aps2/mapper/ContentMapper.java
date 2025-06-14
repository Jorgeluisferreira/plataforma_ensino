package grupo1.aps2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import grupo1.aps2.dto.DTOContent.ContentDTO;
import grupo1.aps2.model.ContentEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA,
    unmappedSourcePolicy = ReportingPolicy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ContentMapper {
    ContentDTO map(ContentEntity source);
    ContentEntity map(ContentDTO source);
}
