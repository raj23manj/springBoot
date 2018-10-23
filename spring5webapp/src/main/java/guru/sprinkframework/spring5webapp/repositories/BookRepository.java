package guru.sprinkframework.spring5webapp.repositories;

import guru.sprinkframework.spring5webapp.model.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
}
