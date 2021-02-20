package main;

import java.time.LocalDate;
import java.util.UUID;

public abstract class Product {
    private String productId;
    private Integer year = LocalDate.now().getYear();
    private Integer month = LocalDate.now().getMonthValue();
    public Product(String productId) {
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }

    public abstract double getPrice();

    public void setYear(int year){
        this.year = year;
    }

    public void setMonth(int month){
        this.month = month;
    }

    public Integer getYear() {
        return this.year;
    }

    public Integer getMonth(){
        return this.month;
    }

}


;