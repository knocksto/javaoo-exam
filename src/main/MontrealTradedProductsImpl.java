package main;

import java.time.LocalDate;
import java.util.*;

public class MontrealTradedProductsImpl implements MontrealTradedProducts {
    public List<Product> productList = new ArrayList<Product>();
    Map<Product, Integer> mapOfTrackedTrades = new HashMap<Product, Integer>(); //map of products and their quantity
    int year = LocalDate.now().getYear();
    int month = LocalDate.now().getMonthValue();

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
                .filter(trade -> trade.getKey().getYear() == year && trade.getKey().getMonth() == month)
                .map(trades -> trades.getValue()) //gets trade quantity
                .reduce(0, Integer::sum);
    }

    @Override
    public double totalValueOfDaysTradedProducts() {

        return mapOfTrackedTrades
                .entrySet()
                .stream()
                .filter(trade -> trade.getKey().getYear() == year && trade.getKey().getMonth() == month)
                .map(trade -> trade.getKey().getPrice() * trade.getValue()) //multiplies price and quantity
                .reduce(0.0, Double::sum);

    }

}
