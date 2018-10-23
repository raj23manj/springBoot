package guru.sprinkframework.spring5webapp.repositories;

import guru.sprinkframework.spring5webapp.model.Publisher;
import org.springframework.data.repository.CrudRepository;

public interface PublisherRepository extends CrudRepository<Publisher, Long> {
}
