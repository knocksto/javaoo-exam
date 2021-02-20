package main;

public class Futures extends Product {
    private String exchange;
    private String contractCode;
    private Integer month;
    private Integer year;
    private ProductPricingService productPricingService;

    public Futures(String exchange, String contractCode, Integer month, Integer year, String productId, ProductPricingService productPricingService) {

        super(productId);
        super.setMonth(month);
        super.setYear(year);

        this.exchange = exchange;
        this.contractCode = contractCode;
        this.month = month;
        this.year = year;
        this.productPricingService = productPricingService;
    }

    @Override
    public double getPrice(){
        return productPricingService.price(exchange, contractCode, month, year);
    }
}
