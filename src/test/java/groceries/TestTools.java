package groceries;

import java.math.*;
import java.util.*;

import org.jetbrains.annotations.*;
import static org.junit.Assert.*;

/**
 * Provides methods for testing a {@link Solver} implementation.
 *
 * @author Gerrit Meinders
 * @author Modified by Jorge Ortiz
 */
public class TestTools
{
	public static void testSolver( final Solver solver )
	{
		final Ingredient egg = createEgg();
		final List<Product> products = createEggProducts( egg );

		assertEquals( "No products to buy.", Collections.emptyList(), solver.productsToBuy( 1, Collections.emptyList() ) );

		for ( int requiredAmount = 5; requiredAmount < 30; requiredAmount++ )
		{
			System.out.print( "itemsToBuy( " + requiredAmount + " ): " );

			final List<ShoppingListItem> shoppingListItems = solver.productsToBuy( requiredAmount, products );

			int totalPrice = 0;
			for ( final ShoppingListItem shoppingListItem : shoppingListItems )
			{
				totalPrice += shoppingListItem.getAmount() * shoppingListItem.getProduct().getPrice();
			}
			System.out.print( "€ " + BigDecimal.valueOf( totalPrice, 2 ).toPlainString() + " for items " );

			System.out.println( shoppingListItems );

			int actualAmount = 0;
			for ( final ShoppingListItem shoppingListItem : shoppingListItems )
			{
				final Product product = shoppingListItem.getProduct();
				assertNotNull( "Missing product.", product );
				assertEquals( "Unexpected product.", egg, product.getIngredient() );
				actualAmount += shoppingListItem.getAmount() * product.getAmount();
			}

			assertTrue( "Didn't get the required amount.", actualAmount >= requiredAmount );
		}
	}

	@NotNull
	public static Ingredient createEgg()
	{
		return new Ingredient( "egg" );
	}

	@NotNull
	public static Ingredient createHam()
	{
		return new Ingredient( "ham" );
	}

	@NotNull
	public static Ingredient createCheese()
	{
		return new Ingredient( "cheese" );
	}

	@NotNull
	public static List<Product> createEggProducts( final Ingredient egg )
	{
		final List<Product> products = new ArrayList<>();
		products.add( new Product( egg, 1, 35 ) );
		products.add( new Product( egg, 6, 199 ) );
		products.add( new Product( egg, 10, 299 ) );
		products.add( new Product( egg, 20, 399 ) );
		products.add( new Product( egg, 30, 485 ) );
		return products;
	}

	@NotNull
	public static List<Product> createMultipleProducts( final Ingredient egg,
														final Ingredient ham,
														final Ingredient cheese)
	{
		final List<Product> products = new ArrayList<>();
		products.add( new Product( egg, 1, 35 ) );
		products.add( new Product( egg, 6, 199 ) );
		products.add( new Product( egg, 10, 299 ) );
		products.add( new Product( egg, 20, 399 ) );
		products.add( new Product( egg, 30, 485 ) );
		products.add( new Product( ham, 1, 50 ) );
		products.add( new Product( ham, 5, 250 ) );
		products.add( new Product( cheese, 2, 300 ) );
		products.add( new Product( cheese, 8, 1200 ) );
		return products;
	}

	@NotNull
	public static Recipe createOmeletRecipe()
	{
		final Recipe recipe = new Recipe( "omelet" );
		recipe.addIngredient( createEgg(), 3 );
		return recipe;
	}

	@NotNull
	public static Recipe createOmeletHamNChesseRecipe()
	{
		final Recipe recipe = new Recipe( "omeletHamNChesse" );
		recipe.addIngredient( createEgg(), 3 );
		recipe.addIngredient( createHam(), 1 );
		recipe.addIngredient( createCheese(), 1 );
		return recipe;
	}

	private TestTools()
	{
	}
}
