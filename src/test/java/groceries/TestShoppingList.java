package groceries;

import java.util.*;

import static groceries.TestTools.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import static java.util.stream.Collectors.*;
import org.jetbrains.annotations.*;
import static org.junit.Assert.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * Unit test for {@link ShoppingList}.
 *
 * @author Gerrit Meinders
 * @author Modified by Jorge Ortiz
 */
@RunWith(value = Parameterized.class)
public class TestShoppingList
{
	/**
	 * Whether the shopping list will re-optimize existing items when new items
	 * are added.
	 */
	protected boolean _optimized;

	protected @NotNull ShoppingList createShoppingList()
	{
		return new ShoppingList();
	}

	protected @NotNull Solver createSolver()
	{
		return new ShoppingSolver(_optimized);
	}

	public TestShoppingList(String text, boolean optimized) {
		this._optimized = optimized;
	}

	@Parameterized.Parameters(name = "{index}: {0}: test({1})")
	public static Collection<Object[]> data() {
		return asList( new Object[][] {
				{"Not Optimized",false},
				{"Optimized",true}
		});
	}

	@Test
	public void testAddItem()
	{
		final Ingredient egg = createEgg();
		final List<Product> products = createEggProducts( egg );
		final ShoppingList shoppingList = createShoppingList();

		shoppingList.addItem( new ShoppingListItem( products.get( 0 ), 1 ) );
		assertShoppingList( shoppingList, singletonList( products.get( 0 ) ), singletonList( 1 ) );

		shoppingList.addItem( new ShoppingListItem( products.get( 0 ), 2 ) );
		assertShoppingList( shoppingList, singletonList( products.get( 0 ) ), singletonList( 3 ) );

		shoppingList.addItem( new ShoppingListItem( products.get( 1 ), 4 ) );
		assertShoppingList( shoppingList, asList( products.get( 0 ), products.get( 1 ) ), asList( 3, 4 ) );
	}

	@Test
	public void testAddRecipe()
	{
		final Ingredient egg = createEgg();
		final List<Product> products = createEggProducts( egg );
		final ShoppingList shoppingList = createShoppingList();

		shoppingList.addRecipe( createOmeletRecipe(), products, createSolver() );
		assertShoppingList( shoppingList, singletonList( products.get( 0 ) ), singletonList( 3 ) );

		if ( _optimized )
		{
			shoppingList.addRecipe( createOmeletRecipe(), products, createSolver() );
			assertShoppingList( shoppingList, singletonList( products.get( 1 ) ), singletonList( 1 ) );
		}
		else
		{
			shoppingList.addRecipe( createOmeletRecipe(), products, createSolver() );
			assertShoppingList( shoppingList, singletonList( products.get( 0 ) ), singletonList( 6 ) );
		}
	}

	@Test
	public void testAddHamCheeseRecipe()
	{
		final Ingredient egg = createEgg();
		final Ingredient ham = createHam();
		final Ingredient cheese = createCheese();
		final List<Product> products = createMultipleProducts( egg , ham , cheese);
		final ShoppingList shoppingList = createShoppingList();

		shoppingList.addRecipe( createOmeletHamNChesseRecipe(), products, createSolver() );
		assertShoppingList( shoppingList,  asList(products.get( 0 ), products.get( 5 ), products.get( 7 )), asList( 3, 1, 1 ) );

		if ( _optimized )
		{
			shoppingList.addRecipe( createOmeletHamNChesseRecipe(), products, createSolver() );
			assertShoppingList( shoppingList, asList(products.get( 1 ), products.get( 5), products.get( 7 ) ), asList( 1, 2, 1 ) );
		}
		else
		{
			shoppingList.addRecipe( createOmeletHamNChesseRecipe(), products, createSolver() );
			assertShoppingList( shoppingList, asList(products.get( 0 ), products.get( 5), products.get( 7 ) ), asList( 6, 2, 2 ) );
		}
	}

	@Test
	public void testAddHamCheesePlusNormalOmeleteRecipe()
	{
		final Ingredient egg = createEgg();
		final Ingredient ham = createHam();
		final Ingredient cheese = createCheese();
		final List<Product> products = createMultipleProducts( egg , ham , cheese);
		final ShoppingList shoppingList = createShoppingList();

		shoppingList.addRecipe( createOmeletRecipe(), products, createSolver() );
		assertShoppingList( shoppingList, singletonList( products.get( 0 ) ), singletonList( 3 ) );

		if ( _optimized )
		{
			shoppingList.addRecipe( createOmeletHamNChesseRecipe(), products, createSolver() );
			assertShoppingList( shoppingList, asList(products.get( 1 ), products.get( 5), products.get( 7 ) ), asList( 1, 1, 1 ) );
		}
		else
		{
			shoppingList.addRecipe( createOmeletHamNChesseRecipe(), products, createSolver() );
			assertShoppingList( shoppingList, asList(products.get( 0 ), products.get( 5), products.get( 7 ) ), asList( 6, 1, 1 ) );
		}
	}

	@Test
	public void testGetTotalPrice()
	{
		final Ingredient egg = createEgg();
		final List<Product> products = createEggProducts( egg );

		final Product singleEgg = products.get( 0 );
		final Product sixEggs = products.get( 1 );

		final ShoppingList shoppingList = createShoppingList();
		shoppingList.addItem( new ShoppingListItem( singleEgg, 3 ) );
		shoppingList.addItem( new ShoppingListItem( sixEggs, 4 ) );

		assertEquals( "Unexpected total price.", singleEgg.getPrice() * 3 + sixEggs.getPrice() * 4, shoppingList.getTotalPrice() );
	}

	private void assertShoppingList( final ShoppingList shoppingList, final List<Product> expectedProducts, final List<Integer> expectedAmounts )
	{
		assertEquals( "Unexpected products.", expectedProducts, shoppingList.getItems().stream().map( ShoppingListItem::getProduct ).collect( toList() ) );
		assertEquals( "Unexpected amounts.", expectedAmounts, shoppingList.getItems().stream().map( ShoppingListItem::getAmount ).collect( toList() ) );
	}
}
