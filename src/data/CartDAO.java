package data;

import authentification.Permission;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import models.Cart;
import models.User;

import java.lang.reflect.Type;
import java.util.List;

public class CartDAO extends Database {
    private static CartDAO instance;

    private List<Cart> carts;

    private CartDAO() {
        this.filePath = "src/data/tables/carts.json";
        loadData();
    }

    public static CartDAO getInstance(){
        if (instance == null) {
            instance = new CartDAO();
        }
        return instance;
    }

    @Override
    protected void loadData() {
        try {
            Gson gson = new Gson();
            String jsonString = readFile();
            Type listType = new TypeToken<List<Cart>>(){}.getType();
            carts = gson.fromJson(jsonString, listType);
            updateAutoIncrementId();
        } catch (Exception e) {
            System.out.println("Error loading cart data: " + e.getMessage());
        }
    }

    @Override
    protected void saveData() {
        try {
            Gson gson = new Gson();
            String jsonString = gson.toJson(carts);
            writeFile(jsonString);
        } catch (Exception e) {
            System.out.println("Error saving user data: " + e.getMessage());
        }
    }

    @Override
    public void updateAutoIncrementId() {
        int maxId = 0;
        for (Cart cart : carts) {
            if (cart.getId() > maxId) {
                maxId = cart.getId();
            }
        }
        this.autoIncrementId = maxId;
    }

    public boolean insert(int userId) {
        try {
            Cart cart = new Cart(userId);
            carts.add(cart);
            saveData();
            System.out.println("Cart added successfully.");
            return true;
        } catch (Exception e) {
            System.out.println("Error inserting cart: " + e.getMessage());
            System.out.println("Aborting...");
            carts.removeLast();
            return false;
        }
    }

    public boolean update(Cart updatedCart){
        Cart existingUser = selectById(updatedCart.getId());
        if (existingUser == null) {
            System.out.println("Cart not found.");
            return false;
        }

        try {
            for (int i = 0; i < carts.size(); i++) {
                if (carts.get(i).getId() == updatedCart.getId()) {
                    carts.set(i, updatedCart);
                    saveData();
                    System.out.println("Cart updated successfully.");
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            System.out.println("Error updating cart: " + e.getMessage());
            System.out.println("Aborting...");
            carts.set(carts.indexOf(updatedCart), existingUser);
            return false;
        }
    }

    public boolean delete(int userId){
        Cart existingCart = selectByUserId(userId);
        if (existingCart == null) {
            System.out.println("Cart not found.");
            return false;
        }
        try {
            carts.removeIf(u -> u.getUserId() == userId);
            saveData();
            System.out.println("Cart deleted successfully.");
            return true;
        } catch (Exception e) {
            System.out.println("Error deleting cart: " + e.getMessage());
            System.out.println("Aborting...");
            carts.add(existingCart);
            return false;
        }
    }

    public Cart selectById(int id) {
        for (Cart cart : carts) {
            if (cart.getId() == id) {
                return cart;
            }
        }
        return null;
    }

    public Cart selectByUserId(int userId) {
        for (Cart cart : carts) {
            if (cart.getUserId() == userId) {
                return cart;
            }
        }
        return null;
    }
}
