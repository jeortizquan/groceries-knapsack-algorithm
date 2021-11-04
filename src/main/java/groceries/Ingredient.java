package groceries;

import org.jetbrains.annotations.*;

/**
 * Ingredient for use in a recipe.
 *
 * @author Gerrit Meinders
 */
public class Ingredient
{
	/**
	 * Name of the ingredient.
	 */
	private final @NotNull String _name;

	public Ingredient( final @NotNull String name )
	{
		_name = name;
	}

	public @NotNull String getName()
	{
		return _name;
	}

	@Override
	public String toString()
	{
		return _name;
	}

	@Override
	public boolean equals( final Object o )
	{
		if ( this == o ) return true;
		if ( !( o instanceof Ingredient ) ) return false;
		final Ingredient that = (Ingredient)o;
		return _name.equals( that._name );
	}

	@Override
	public int hashCode()
	{
		return _name.hashCode();
	}
}
