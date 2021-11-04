package groceries;

/**
 * A product is a specific amount of an ingredient sold by shops.
 *
 * @author Gerrit Meinders
 */
public class Product
{
	/**
	 * Ingredient.
	 */
	private final Ingredient _ingredient;

	/**
	 * Amount of the ingredient.
	 */
	private final int _amount;

	/**
	 * Price of the product.
	 */
	private int _price;

	public Product( final Ingredient ingredient, final int amount, final int price )
	{
		_ingredient = ingredient;
		_amount = amount;
		_price = price;
	}

	public Ingredient getIngredient()
	{
		return _ingredient;
	}

	public int getAmount()
	{
		return _amount;
	}

	public int getPrice()
	{
		return _price;
	}

	@Override
	public String toString()
	{
		return _amount + "× " + _ingredient + " for € " + _price / 100.0;
	}

	@Override
	public boolean equals( final Object o )
	{
		if ( this == o ) return true;
		if ( !( o instanceof Product ) ) return false;

		final Product product = (Product)o;

		if ( _amount != product._amount ) return false;
		return _ingredient.equals( product._ingredient );
	}

	@Override
	public int hashCode()
	{
		int result = _ingredient.hashCode();
		result = 31 * result + _amount;
		return result;
	}
}
