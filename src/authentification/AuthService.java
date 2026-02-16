package authentification;

import data.CartDAO;
import data.UserDAO;
import models.User;

public class AuthService {
    private static boolean validateLogin(String username, String password) {
        boolean success = false;
        User user = UserDAO.getInstance().selectByUsername(username);
        if (user != null) {
            success = PasswordHasher.verifyPassword(password, user.getPasswordHash());
        }

        if (!success) {
            System.out.println("Invalid username or password.");
        }
        return success;
    }

    public static User login(String username, String password) {
        if (validateLogin(username, password)) {
            return UserDAO.getInstance().selectByUsername(username);
        }
        return null;
    }

    private static boolean validateRegister(String username, String password, String confirmPassword) {
        if (UserDAO.getInstance().selectByUsername(username) != null) {
            System.out.println("Username already exists.");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            System.out.println("Passwords do not match.");
            return false;
        }
        return true;
    }

    public static User register(String username, String password, String confirmPassword, Permission permission) {
        if (validateRegister(username, password, confirmPassword)) {
            String passwordHash = PasswordHasher.hashPassword(password);

            boolean success1 = UserDAO.getInstance().insert(username, passwordHash, permission);

            boolean success2 = true;

            if (permission == Permission.CLIENT && success1) {
                success2 = CartDAO.getInstance().insert(UserDAO.getInstance().selectByUsername(username).getId());
            }

            boolean success = success1 && success2;

            if (success) {
                return UserDAO.getInstance().selectByUsername(username);
            }
        }
        return null;
    }
}
