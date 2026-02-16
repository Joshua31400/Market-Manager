package data;

import models.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Product entities.
 * Manages the persistence of products in a JSON file using the Singleton pattern.
 */
public class ProductDAO extends Database {
    private static ProductDAO instance;
    private List<Product> products;

    private ProductDAO() {
        this.filePath = "src/data/tables/products.json";
        loadData();
    }

    /**
     * Standard Singleton provider.
     * @return The unique instance of ProductDAO.
     */
    public static ProductDAO getInstance() {
        if (instance == null) {
            instance = new ProductDAO();
        }
        return instance;
    }

    /**
     * Loads product data from the JSON file into the memory list.
     * Initializes an empty list if the file is empty or missing.
     */
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
            System.out.println("Error loading product data: " + e.getMessage());
            products = new ArrayList<>();
        }
    }

    /**
     * Serializes the current product list to JSON and writes it to the disk.
     */
    @Override
    public void saveData() {
        try {
            Gson gson = new Gson();
            String jsonString = gson.toJson(products);
            writeFile(jsonString);
        } catch (Exception e) {
            System.out.println("Error saving product data: " + e.getMessage());
        }
    }

    /**
     * Scans the current list to find the highest ID and sets the auto-increment starting point.
     */
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

    // --- SEARCH METHODS ---

    /**
     * Retrieves a product by its unique integer ID.
     */
    public Product selectById(int id) {
        for (Product p : products) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    /**
     * Retrieves a product by its name (case-insensitive).
     */
    public Product selectByName(String name) {
        for (Product p : products) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Hybrid search: attempts to find a product by parsing the identifier as an ID,
     * otherwise searches by name.
     */
    public Product findByIdOrName(String identifier) {
        try {
            int id = Integer.parseInt(identifier);
            return selectById(id);
        } catch (NumberFormatException e) {
            return selectByName(identifier);
        }
    }

    // --- DATA MANIPULATION METHODS ---

    /**
     * Creates and adds a new product to the list, then saves the changes.
     */
    public void addProduct(String name, int qty, double price) {
        // Uses the autoIncrement() method from the Database parent class
        products.add(new Product(autoIncrement(), name, qty, price));
        saveData();
    }

    /**
     * Returns the complete list of products.
     */
    public List<Product> getAll() {
        return products != null ? products : new ArrayList<>();
    }

    /**
     * Removes a product by ID and saves the updated list.
     * @return true if a product was removed, false otherwise.
     */
    public boolean delete(int id) {
        boolean removed = products.removeIf(p -> p.getId() == id);
        if (removed) {
            saveData();
        }
        return removed;
    }
}