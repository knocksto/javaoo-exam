package main;

public class Stock extends Product {
    private String ticker;
    private String exchange;
    private ProductPricingService productPricingService;

    public Stock(String ticker, String exchange, String productId, ProductPricingService productPricingService) {
        super(productId);
        this.ticker = ticker;
        this.exchange = exchange;
        this.productPricingService = productPricingService;
    }


    @Override
    public double getPrice() {
        return productPricingService.price(exchange,ticker);
    }
}
