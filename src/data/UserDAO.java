package data;

import authentification.Permission;
import models.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

public class UserDAO extends Database {
        private static UserDAO instance;

        private List<User> users;

        public List<User> getAll() {
            return users;
        }

        private UserDAO(){
            filePath = "src/data/tables/users.json";
            loadData();
        }

        public static UserDAO getInstance(){
            if (instance == null) {
                instance = new UserDAO();
            }
            return instance;
        }

        @Override
        protected void loadData(){
            try {
                Gson gson = new Gson();
                String jsonString = readFile();
                Type listType = new TypeToken<List<User>>(){}.getType();
                users = gson.fromJson(jsonString, listType);
                updateAutoIncrementId();
            } catch (Exception e) {
                System.out.println("Error loading user data: " + e.getMessage());
            }

        }

        @Override
        protected void saveData(){
            try {
                Gson gson = new Gson();
                String jsonString = gson.toJson(users);
                writeFile(jsonString);
            } catch (Exception e) {
                System.out.println("Error saving user data: " + e.getMessage());
            }
        }

        @Override
        public void updateAutoIncrementId() {
            int maxId = 0;
            for (User user : users) {
                if (user.getId() > maxId) {
                    maxId = user.getId();
                }
            }
            this.autoIncrementId = maxId;
        }

        public boolean insert(String username, String passwordHash, Permission permission){
            try {
                User user = new User(username, passwordHash, permission);
                users.add(user);
                saveData();
                System.out.println("User added successfully.");
                return true;
            } catch (Exception e) {
                System.out.println("Error inserting user: " + e.getMessage());
                System.out.println("Aborting...");
                users.removeLast();
                return false;
            }
        }

        public boolean update(User updatedUser){
            User existingUser = selectById(updatedUser.getId());
            try {
                for (int i = 0; i < users.size(); i++) {
                    if (users.get(i).getId() == updatedUser.getId()) {
                        users.set(i, updatedUser);
                        saveData();
                        System.out.println("User updated successfully.");
                        return true;
                    }
                }
                System.out.println("User not found.");
                return false;
            } catch (Exception e) {
                System.out.println("Error updating user: " + e.getMessage());
                System.out.println("Aborting...");
                users.set(users.indexOf(updatedUser), existingUser);
                return false;
            }
        }

        public boolean delete(int userId){
            User existingUser = selectById(userId);
            try {
                users.removeIf(u -> u.getId() == userId);
                saveData();
                System.out.println("User deleted successfully.");
                return true;
            } catch (Exception e) {
                System.out.println("Error deleting user: " + e.getMessage());
                System.out.println("Aborting...");
                users.add(existingUser);
                return false;
            }
        }

        public User selectById(int userId) {
            for (User user : users) {
                if (user.getId() == userId) {
                    return user;
                }
            }
            return null;
        }

        public User selectByUsername(String username) {
            for (User user : users) {
                if (user.getUsername().equals(username)) {
                    return user;
                }
            }
            return null;
        }
}
