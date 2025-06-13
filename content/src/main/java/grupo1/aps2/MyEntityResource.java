package grupo1.aps2;

import grupo1.aps2.model.ContentEntity;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;

public interface MyEntityResource extends PanacheEntityResource<ContentEntity, Long> {
}