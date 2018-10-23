package guru.sprinkframework.spring5webapp.repositories;

import guru.sprinkframework.spring5webapp.model.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Long> {
}
