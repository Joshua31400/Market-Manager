package models;

import data.CartDAO;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private int id;
    private int userId;
    private List<Product> products = new ArrayList<>();

    public Cart(int id, int userId, List<Product> products) {
        this.id = id;
        this.userId = userId;
        this.products = products;
    }

    public Cart(int userId) {
        this.id = CartDAO.getInstance().autoIncrement();
        this.userId = userId;
    }

    public int getTotalProducts() {
        int total = 0;
        for (Product product : products) {
            total += product.getQuantity();
        }
        return total;
    }

    public double getTotalPrice() {
        double total = 0;
        for (Product product : products) {
            total += product.getPrice() * product.getQuantity();
        }
        return total;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public List<Product> getProducts() {
        return products;
    }
}
