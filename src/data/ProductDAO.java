package data;

import authentification.Permission;
import models.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import models.User;
import utils.Colors;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO extends Database {
    private static ProductDAO instance;
    private List<Product> products;

    private ProductDAO() {
        this.filePath = "src/data/tables/products.json";
        loadData();
    }

    public static ProductDAO getInstance() {
        if (instance == null) {
            instance = new ProductDAO();
        }
        return instance;
    }

    @Override
    protected void loadData() {
        try {
            String jsonString = readFile();
            if (jsonString == null || jsonString.isEmpty()) {
                products = new ArrayList<>();
            } else {
                Type listType = new TypeToken<List<Product>>(){}.getType();
                products = new Gson().fromJson(jsonString, listType);
            }
            updateAutoIncrementId();
        } catch (Exception e) {
            System.out.println(Colors.error("Error loading product data: " + e.getMessage()));
            products = new ArrayList<>();
        }
    }

    @Override
    public void saveData() {
        try {
            Gson gson = new Gson();
            String jsonString = gson.toJson(products);
            writeFile(jsonString);
        } catch (Exception e) {
            System.out.println(Colors.error("Error saving product data: " + e.getMessage()));
        }
    }

    @Override
    public void updateAutoIncrementId() {
        int maxId = 0;
        if (products != null) {
            for (Product p : products) {
                if (p.getId() > maxId) {
                    maxId = p.getId();
                }
            }
        }
        this.autoIncrementId = maxId;
    }


    public boolean insert(String name, int qty, double price){
        try {
            Product product = new Product(name, qty, price);
            products.add(product);
            saveData();
            System.out.println(Colors.success("Product added successfully."));
            return true;
        } catch (Exception e) {
            System.out.println(Colors.error("Error inserting product: " + e.getMessage()));
            System.out.println(Colors.error("Aborting..."));
            products.removeLast();
            return false;
        }
    }

    public boolean update(Product updatedProduct){
        Product existingProduct = selectById(updatedProduct.getId());
        try {
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getId() == updatedProduct.getId()) {
                    products.set(i, updatedProduct);
                    saveData();
                    System.out.println(Colors.success("Product updated successfully."));
                    return true;
                }
            }
            System.out.println(Colors.warning("Product not found."));
            return false;
        } catch (Exception e) {
            System.out.println(Colors.error("Error updating user: " + e.getMessage()));
            System.out.println(Colors.error("Aborting..."));
            products.set(products.indexOf(updatedProduct), existingProduct);
            return false;
        }
    }

    public boolean delete(int productId){
        Product existingProduct = selectById(productId);
        try {
            products.removeIf(u -> u.getId() == productId);
            saveData();
            System.out.println(Colors.success("Product deleted successfully."));
            return true;
        } catch (Exception e) {
            System.out.println(Colors.error("Error deleting Product: " + e.getMessage()));
            System.out.println(Colors.error("Aborting..."));
            products.add(existingProduct);
            return false;
        }
    }

    public List<Product> selectAll() {
        return products;
    }


    public Product selectById(int id) {
        for (Product p : products) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public Product selectByName(String name) {
        for (Product p : products) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }
}