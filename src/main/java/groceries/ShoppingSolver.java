package groceries;

import java.util.*;

/**
 * Implementation of the Solver Interface to solve which items need to be bought
 *
 * @author Jorge Ortiz
 */
public class ShoppingSolver implements Solver {
    /**
     * Optimize buy or not
     */
    private Boolean _optimized;

    /**
     * Initialize the shopping with or without optimization
     * @param optimized
     */
    public ShoppingSolver(boolean optimized) {
        this._optimized = optimized;
    }

    @Override
    public List<ShoppingListItem> productsToBuy(int requiredAmount, List<Product> availableProducts) {
        List<ShoppingListItem> productList = new ArrayList<>();
        if (this._optimized) {
            if (requiredAmount != 0) {
                OptionalInt minimumAvailableAmount = availableProducts.stream()
                        .sorted(Comparator.comparingInt(Product::getAmount))
                        .filter(product -> requiredAmount >= product.getAmount())
                        .mapToInt(product -> product.getAmount())
                        .findFirst();
                if (minimumAvailableAmount.isPresent()) {
                    if (requiredAmount >= minimumAvailableAmount.getAsInt()) {
                        Optional<Product> itemToBuy = availableProducts.stream()
                                .sorted(Comparator.comparingInt(Product::getAmount).reversed())
                                .filter(product -> requiredAmount >= product.getAmount())
                                .findFirst();
                        processExactItemToBuy(productList, itemToBuy, itemToBuy.isPresent());
                        productList.addAll(productsToBuy(requiredAmount - itemToBuy.get().getAmount(), availableProducts));

                    } else {
                        Optional<Product> itemToBuy = availableProducts.stream()
                                .sorted(Comparator.comparingInt(Product::getAmount).reversed())
                                .filter(product -> requiredAmount >= product.getAmount())
                                .findFirst();
                        processExactItemToBuy(productList, itemToBuy, itemToBuy.isPresent());
                        productList.addAll(productsToBuy(requiredAmount - 1, availableProducts));
                    }
                } else {
                    Optional<Product> itemToBuy = availableProducts.stream()
                            .sorted(Comparator.comparingInt(Product::getAmount))
                            .filter(product -> requiredAmount <= product.getAmount())
                            .findFirst();
                    processExactItemToBuy(productList, itemToBuy, itemToBuy.isPresent());
                    productList.addAll(productsToBuy(requiredAmount - 1, availableProducts));
                }
            }
            return productList;
        } else {
            processItemToBuy(requiredAmount, availableProducts, productList);
        }
        return productList;
    }

    private void processExactItemToBuy(List<ShoppingListItem> productList, Optional<Product> itemToBuy, boolean condition) {
        if (condition) {
            productList.add(new ShoppingListItem(itemToBuy.get(), 1));
        }
    }

    private void processItemToBuy(int requiredAmount, List<Product> availableProducts, List<ShoppingListItem> productList) {
        Optional<Product> itemToBuy;
        itemToBuy = availableProducts.stream()
                .filter(product -> product.getAmount() <= requiredAmount)
                .findFirst();
        if (itemToBuy.isPresent()) {
            if (itemToBuy.get().getAmount() <= requiredAmount) {
                for (int i = 0; i < requiredAmount; i++) {
                    productList.add(new ShoppingListItem(itemToBuy.get(), 1));
                }
            }
        } else {
            itemToBuy = availableProducts.stream()
                    .filter(product -> requiredAmount <= product.getAmount())
                    .findFirst();
            processExactItemToBuy(productList, itemToBuy, itemToBuy.isPresent());
        }
    }
}
