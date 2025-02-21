package pojo;

public class Product {
    private String title;
    private double price;
    private String description;
    private String image;
    private String categories;

    // Default constructor
    public Product() {}

    // Parameterized constructor
    public Product(String title, double price, String description, String image, String categories) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.image = image;
        this.categories = categories;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    // To string method for easy display
    @Override
    public String toString() {
        return "Product{" +
                "title='" + title + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", categories='" + categories + '\'' +
                '}';
    }
}
