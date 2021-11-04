package groceries;

/**
 * Amount of an ingredient needed for a recipe.
 *
 * @author Gerrit Meinders
 */
public class RecipeIngredient
{
	/**
	 * Ingredient.
	 */
	private Ingredient _ingredient;

	/**
	 * Amount needed.
	 */
	private int _amount;

	/**
	 * Constructs a new instance.
	 */
	public RecipeIngredient()
	{
	}

	public Ingredient getIngredient()
	{
		return _ingredient;
	}

	public void setIngredient( final Ingredient ingredient )
	{
		_ingredient = ingredient;
	}

	public int getAmount()
	{
		return _amount;
	}

	public void setAmount( final int amount )
	{
		_amount = amount;
	}
}
