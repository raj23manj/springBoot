package guru.springframework.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by jt on 6/13/17.
 */
//@Data
@Getter
@Setter
/* gets circular refference created due to bi-directional relationships and
to avoid StackOverflowerror for realtionships mainly */
@EqualsAndHashCode(exclude = {"recipes"})
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

    // this is little different from chad's implementation
    @ManyToMany(mappedBy = "categories")
    private Set<Recipe> recipes;

}
