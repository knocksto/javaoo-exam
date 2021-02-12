package main;

import java.util.*;

public class MontrealTradedProductsImpl implements MontrealTradedProducts {
    public List<Product> productList = new ArrayList<Product>();
    Map<Product, Integer> mapOfTrackedTrades = new HashMap<Product, Integer>(); //map of products and their quantity

    @Override
    public void addNewProduct(Product product) throws ProductAlreadyRegisteredException {

        Optional<Product> optProduct = productList
                .stream()
                .filter(p -> p.getProductId() == product.getProductId()) //check for a product with the same product id
                .findFirst();

        if (optProduct.isPresent()) {
            throw new ProductAlreadyRegisteredException();
        }
        productList.add(product);
    }

    @Override
    public void trade(Product product, int quantity) {

        if (productList.contains(product)) { //check if it's a tracked product or trade

            if (mapOfTrackedTrades.containsKey(product)) {

                mapOfTrackedTrades.put(product, mapOfTrackedTrades.get(product) + quantity); //increase by quantity if it already exists
            } else {
                mapOfTrackedTrades.put(product, quantity);
            }

        }

    }

    @Override
    public int totalTradeQuantityForDay() {
        return mapOfTrackedTrades
                .entrySet()
                .stream()
                .map(trades -> trades.getValue()) //gets trade quantity
                .reduce(0, (quantity1, quantity2) -> quantity1 + quantity2);
    }

    @Override
    public double totalValueOfDaysTradedProducts() {
        System.out.println(mapOfTrackedTrades);
        return mapOfTrackedTrades
                .entrySet()
                .stream()
                .map(trades -> {
                    double totalValue;
                    totalValue = trades.getKey().getPrice() * trades.getValue(); //multiplies price and quantity
                    return totalValue;
                })
                .reduce(0.0, (value1,value2) -> value1 + value2);

    }

}
