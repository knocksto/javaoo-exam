package test;

import main.*;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MontrealTradedProductsImplTest {

    ProductPricingService productPricingServiceMock;
    MontrealTradedProducts montrealTradedProductsImpl;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        productPricingServiceMock = Mockito.mock(ProductPricingService.class);
        montrealTradedProductsImpl = new MontrealTradedProductsImpl();

        Mockito.when(productPricingServiceMock.price(Mockito.anyString(), Mockito.anyString())).thenReturn(400.0); //mock price for stock
        Mockito.when(productPricingServiceMock.price(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(200.0); //mock price for futures

    }


    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void addNewProduct() throws ProductAlreadyRegisteredException {
        Product stockProduct = new Stock("AAPL", "US", "aapl234", productPricingServiceMock);

        Product futureProduct = new Futures("AAPL", "c432234", 02, 2020, "ft2343", productPricingServiceMock);
        Product futureProduct2 = new Futures("AAPL", "c432234", 02, 2020, "ft2343", productPricingServiceMock);


        montrealTradedProductsImpl.addNewProduct(stockProduct);
        montrealTradedProductsImpl.addNewProduct(futureProduct);

        assertThrows(ProductAlreadyRegisteredException.class,() -> montrealTradedProductsImpl.addNewProduct(futureProduct2));

    }

    @org.junit.jupiter.api.Test
    void trade() {
        Product stProduct = new Stock("GOOG", "US", "aapl234", productPricingServiceMock);

        Product ftProduct = new Futures("TSLA", "c432234", 02, 2020, "ft2343", productPricingServiceMock);

        try {
            montrealTradedProductsImpl.addNewProduct(stProduct);
            montrealTradedProductsImpl.addNewProduct(ftProduct);
        } catch (ProductAlreadyRegisteredException p) {
            System.err.println(p.getMessage());

        }

        montrealTradedProductsImpl.trade(stProduct, 100);
        montrealTradedProductsImpl.trade(ftProduct, 200);

    }

    @org.junit.jupiter.api.Test
    void totalTradeQuantityForDay() {
        Product stockProd = new Stock("HOG", "US", "aapl234", productPricingServiceMock);

        Product futureProd = new Futures("HPQ", "c432234", 02, 2020, "ft2343", productPricingServiceMock);

        try {
            montrealTradedProductsImpl.addNewProduct(stockProd);
            montrealTradedProductsImpl.addNewProduct(futureProd);
        } catch (ProductAlreadyRegisteredException p) {
            System.err.println(p.getMessage());

        }

        montrealTradedProductsImpl.trade(stockProd, 100);
        montrealTradedProductsImpl.trade(futureProd, 200);

        int expectedQuantity = 300;

        assertEquals(expectedQuantity, montrealTradedProductsImpl.totalTradeQuantityForDay());
    }

    @org.junit.jupiter.api.Test
    void totalValueOfDaysTradedProducts() {
        Product stockProd = new Stock("C", "US", "aapl234", productPricingServiceMock);

        Product futureProd = new Futures("INTC", "c432234", LocalDate.now().getMonthValue(), LocalDate.now().getYear(), "ft2343", productPricingServiceMock);

        try {
            montrealTradedProductsImpl.addNewProduct(stockProd);
            montrealTradedProductsImpl.addNewProduct(futureProd);
        } catch (ProductAlreadyRegisteredException p) {
            System.err.println(p.getMessage());

        }

        montrealTradedProductsImpl.trade(stockProd, 100);
        montrealTradedProductsImpl.trade(futureProd, 200);

//        mock price for stock = 400 and future = 400;
        int expectedValue = 80000; // (400 *100) + (200*200) = 80,000

        assertEquals(expectedValue, montrealTradedProductsImpl.totalValueOfDaysTradedProducts());
    }
}