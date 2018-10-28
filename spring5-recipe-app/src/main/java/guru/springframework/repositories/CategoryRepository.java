package guru.springframework.repositories;

import guru.springframework.domain.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Created by jt on 6/13/17.
 */

// note here no Stereotype declarations for this like @ component, @service ...ect JPA inmplements it so it is avaliable in the
// Bean Container
public interface CategoryRepository extends CrudRepository<Category, Long> {
    // addition to spring 5, it returns an optional instead of null
    // these dynamic queries like in rails, here they are called JPA Query methods
    // jpa takes care of all implementation
    Optional<Category> findByDescription(String description);
}