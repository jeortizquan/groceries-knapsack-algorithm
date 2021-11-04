package groceries;

import java.util.*;

/**
 * Solves the problem of buying ingredients from a shop in an efficient way.
 * Solutions must provide a required amount of an ingredient and should minimize
 * the cost (and/or possibly some other relevant metric).
 *
 * @author Gerrit Meinders
 */
public interface Solver
{
	/**
	 * Returns which (amounts of) products to buy based on the required amount
	 * of an ingredient and the available products for that ingredient.
	 *
	 * @param requiredAmount    Required amount of an ingredient.
	 * @param availableProducts Available products for that ingredient.
	 *
	 * @return Shopping list items with products to buy.
	 */
	List<ShoppingListItem> productsToBuy( int requiredAmount, List<Product> availableProducts );
}
