package authentification;

import data.CartDAO;
import data.UserDAO;
import models.User;
import utils.InputReader;

public class AuthService {
    public static User requestAuth() {
        System.out.println();
        System.out.println("Welcome! Please choose an option:");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.println();

        InputReader reader = InputReader.getInstance();
        int choice = reader.readInt("Your choice: ", 1, 3);
        System.out.println();

        switch (choice) {
            case 1 -> {return requestLogin();}
            case 2 -> {return requestRegister();}
            case 3 -> {
                System.out.println("Exiting the application. Goodbye!");
                System.out.println();
                System.exit(0);
            }
        }
        return null;
    }

    private static User requestLogin() {
        InputReader reader = InputReader.getInstance();
        System.out.println("Please enter your login credentials:");
        System.out.println();
        String username = reader.readString("Username: ");
        String password = reader.readStringHidden("Password: ");
        System.out.println();

        User user = AuthService.login(username, password);

        if (user != null) {
            System.out.println("Login successful!");
            System.out.println();
            return user;
        } else {
            System.out.println("Login failed. Please try again.");
            System.out.println();
            return null;
        }
    }

    private static User requestRegister() {
        InputReader reader = InputReader.getInstance();
        System.out.println("Please enter your registration details:");
        System.out.println();
        String username = reader.readString("Choose a username: ");
        String password = reader.readStringHidden("Choose a password: ");
        String confirmPassword = reader.readStringHidden("Confirm password: ");
        System.out.println();

        User user = AuthService.register(username, password, confirmPassword, Permission.CLIENT);
        System.out.println();

        if (user != null) {
            System.out.println("Registration successful! You can now log in.");
            System.out.println();
            return user;
        } else {
            System.out.println("Registration failed. Please try again.");
            System.out.println();
            return null;
        }
    }

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

    private static User login(String username, String password) {
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
