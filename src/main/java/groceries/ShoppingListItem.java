package groceries;

/**
 * Specifies how much of a product to buy.
 *
 * @author Gerrit Meinders
 */
public class ShoppingListItem
{
	/**
	 * Product to buy.
	 */
	private final Product _product;

	/**
	 * Amount of the product to buy.
	 */
	private int _amount;

	public ShoppingListItem( final Product product, final int amount )
	{
		_product = product;
		_amount = amount;
	}

	public Product getProduct()
	{
		return _product;
	}

	public int getAmount()
	{
		return _amount;
	}

	public void setAmount( final int amount )
	{
		_amount = amount;
	}

	/**
	 * Increases the amount by 1.
	 */
	public void incrementAmount()
	{
		_amount++;
	}

	@Override
	public String toString()
	{
		return _amount + "× " + _product;
	}
}
