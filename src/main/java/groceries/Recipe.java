package groceries;

import java.util.ArrayList;
import java.util.List;

/**
 * Recipe and the ingredients needed to make it.
 *
 * @author Gerrit Meinders
 * @author Modified by Jorge Ortiz
 */
public class Recipe {
    /**
     * Name of the recipe.
     */
    private final String _name;

    /**
     * Ingredients needed for the recipe.
     */
    private final List<RecipeIngredient> _ingredients = new ArrayList<>();

    /**
     * Constructs a new instance.
     */
    public Recipe(final String name) {
        _name = name;
    }

    public String getName() {
        return _name;
    }

    public List<RecipeIngredient> getIngredients() {
        return _ingredients;
    }

    public void addIngredient(final RecipeIngredient ingredient) {
        _ingredients.add(ingredient);
    }

    public void addIngredient(final Ingredient ingredient, final int amount) {
        // TODO: With a small refactoring, this method could be a one-liner.
//		final RecipeIngredient recipeIngredient = new RecipeIngredient();
//		recipeIngredient.setIngredient( ingredient );
//		recipeIngredient.setAmount( amount );
        addIngredient(new RecipeIngredient() {{
            setIngredient(ingredient);
            setAmount(amount);
        }});
    }
}
