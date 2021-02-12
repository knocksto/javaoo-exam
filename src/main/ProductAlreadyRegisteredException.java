package main;

public class ProductAlreadyRegisteredException extends Exception {



    public ProductAlreadyRegisteredException() {
        super("There is already a product with the same Product ID");
    }

    public String getMessage() {
        return super.getMessage();
    }
}
