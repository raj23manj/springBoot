package guru.springframework.domain;

import lombok.*;

import javax.persistence.*;

/**
 * Created by jt on 6/13/17.
 */
@Getter
@Setter
/* gets circular refference created due to bi-directional relationships and
to avoid StackOverflowerror for realtionships mainly */
@EqualsAndHashCode(exclude = {"recipe"})
@Entity
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Recipe recipe;

    @Lob // large objects, clob , blob more that 255 characters here as clob changed by jpa
    private String recipeNotes;


/* gets circular refference created due to bi-directional relationships and
to avoid StackOverflowerror for realtionships mainly see here you can see */
//    public boolean equals(final Object o) {
//        if (o == this) return true;
//        if (!(o instanceof Notes)) return false;
//        final Notes other = (Notes) o;
//        if (!other.canEqual((Object) this)) return false;
//        final Object this$id = this.getId();
//        final Object other$id = other.getId();
//        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
//        final Object this$recipe = this.getRecipe();
//        final Object other$recipe = other.getRecipe();
//        if (this$recipe == null ? other$recipe != null : !this$recipe.equals(other$recipe)) return false;
//        final Object this$recipeNotes = this.getRecipeNotes();
//        final Object other$recipeNotes = other.getRecipeNotes();
//        if (this$recipeNotes == null ? other$recipeNotes != null : !this$recipeNotes.equals(other$recipeNotes))
//            return false;
//        return true;
//    }
//
//    protected boolean canEqual(final Object other) {
//        return other instanceof Notes;
//    }
//
//    public int hashCode() {
//        final int PRIME = 59;
//        int result = 1;
//        final Object $id = this.getId();
//        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
//        final Object $recipe = this.getRecipe();
//        result = result * PRIME + ($recipe == null ? 43 : $recipe.hashCode());
//        final Object $recipeNotes = this.getRecipeNotes();
//        result = result * PRIME + ($recipeNotes == null ? 43 : $recipeNotes.hashCode());
//        return result;
//    }

}
