package models;

import data.CartDAO;

import java.util.List;

public class Cart {
    private int id;
    private int userId;
    //private List<Product> products;

    public Cart(int id, int userId) {
        this.id = id;
        this.userId = userId;
    }

    public Cart(int userId) {
        this.id = CartDAO.getInstance().autoIncrement();
        this.userId = userId;
    }

    //public void addProduct(Product product) {
        //products.add(product);
    //}

    //public void removeProduct(Product product) {
        //products.remove(product);
    //}

    //public List<Products> getProducts() {
        //return products;
        //return null;
    //}

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }
}
