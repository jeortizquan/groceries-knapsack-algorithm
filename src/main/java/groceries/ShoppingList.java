package groceries;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.*;

/**
 * A shopping list specifies which products to buy in which amounts.
 *
 * @author Gerrit Meinders
 * @author Modified by Jorge Ortiz
 */
public class ShoppingList {
    /**
     * Items on the shopping list.
     */
    protected final List<ShoppingListItem> _items = new ArrayList<>();

    /**
     * Returns the items on the shopping list.
     *
     * @return Shopping list items.
     */
    public List<ShoppingListItem> getItems() {
        return unmodifiableList(_items);
    }

    /**
     * Adds an item to the shopping list. If the product is already on the
     * shopping list, the amount of the existing item is increased instead.
     *
     * @param item Item to add.
     */
    public void addItem(final ShoppingListItem item) {
        boolean found = false;
        for (int i = 0; !found && i < _items.size(); i++) {
            if (alreadyExists(item, i)) {
                updateShoppingList(item, i);
                found = true;
            }
        }
        if (!found) {
            addItemToShoppingList(item);
        }
    }

    private void addItemToShoppingList(ShoppingListItem item) {
        _items.add(item);
    }

    private void updateShoppingList(ShoppingListItem item, int i) {
        _items.get(i).setAmount(_items.get(i).getAmount() + item.getAmount());
    }

    private boolean alreadyExists(ShoppingListItem item, int i) {
        return _items.get(i).getProduct().equals(item.getProduct());
    }

    /**
     * Adds items needed for the given recipe to the shopping list.
     *
     * @param recipe   Recipe to add shopping list items for.
     * @param products Available products.
     * @param solver   Solver that determines which products to buy.
     */
    public void addRecipe(final Recipe recipe, final List<Product> products, final Solver solver) {
        // solution:
        //  add all ingredients of recipe
        recipe.getIngredients().stream()
                .forEach(recipeIngredient -> {
                    // It could be many products that come in "Available products" parameter
                    // So we choose the ones that have the same ingredient from all the products catalog
                    // we buy the amount of the ingredient and solver can be optimized or not
                    List<Product> availableProducts = products.stream()
                            .filter(p -> p.getIngredient().equals(recipeIngredient.getIngredient()))
                            .collect(Collectors.toList());
                    // get current items
                    Integer actualItemsOfIngredient = _items.stream()
                            .filter( p -> p.getProduct().getIngredient().equals(recipeIngredient.getIngredient()))
                            .mapToInt( p -> p.getAmount())
                            .sum();
                    // remove existing items
                    _items.removeIf( shoppingListItem -> shoppingListItem.getProduct().getIngredient().equals(recipeIngredient.getIngredient()));
                    // get the new items
                    List<ShoppingListItem> groceries = solver.productsToBuy(recipeIngredient.getAmount() + actualItemsOfIngredient, availableProducts);
                    // update them to the list
                    groceries.stream().forEach(item-> this.addItem(item));
                });
    }

    /**
     * Returns the total price of the items on the shopping list.
     *
     * @return Total price.
     */
    public int getTotalPrice() {
        return this._items.stream()
                .map( item -> item.getAmount() * item.getProduct().getPrice() )
                .mapToInt(Integer::intValue)
                .sum();
    }
}
