package pojo;

public class CartProduct {
    private int productId;
    private int productQuantity;

    // Default constructor
    public CartProduct() {}

    // Parameterized constructor
    public CartProduct(int productId, int productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }

    // Getters and Setters
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    @Override
    public String toString() {
        return "CartProduct{" +
                "productId=" + productId +
                ", productQuantity=" + productQuantity +
                '}';
    }
}
