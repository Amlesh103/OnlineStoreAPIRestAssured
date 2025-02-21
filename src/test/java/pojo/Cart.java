package pojo;

import java.util.Date;
import java.util.List;

public class Cart {
    private int userId;
    private String date;
    private List<CartProduct> products;

    // Default constructor
    public Cart() {}

    // Parameterized constructor
    public Cart(int userId, String date, List<CartProduct> products) {
        this.userId = userId;
        this.date = date;
        this.products = products;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<CartProduct> getProducts() {
        return products;
    }

    public void setProducts(List<CartProduct> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "userId=" + userId +
                ", date=" + date +
                ", products=" + products +
                '}';
    }
}

