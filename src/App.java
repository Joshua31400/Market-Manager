import authentification.AuthService;
import authentification.Permission;
import data.UserDAO;
import models.User;
import commands.src.CommandInterpreter;
import utils.InputReader;
import utils.TerminalUtils;

public class App {
    private User currentUser = null;

    public void run() {
        TerminalUtils.clearTerminal();
        while (true) {
            if (currentUser == null) {
                requestAuth();
            } else {
                String input = InputReader.getInstance().readString( currentUser.getUsername() + "(" + currentUser.getPermission() + ")" + "> ");
                CommandInterpreter.interpretCommand(input, currentUser);
            }
        }
    }

    private void requestAuth() {
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
            case 1 -> requestLogin();
            case 2 -> requestRegister();
            case 3 -> {
                System.out.println("Exiting the application. Goodbye!");
                System.exit(0);
            }
        }

    }

    private void requestLogin() {
        InputReader reader = InputReader.getInstance();
        System.out.println("Please enter your login credentials:");
        System.out.println();
        String username = reader.readString("Username: ");
        String password = reader.readStringHidden("Password: ");
        System.out.println();

        User user = AuthService.login(username, password);

        if (user != null) {
            currentUser = user;
            System.out.println("Login successful!");
        } else {
            System.out.println("Login failed. Please try again.");
        }
    }

    private void requestRegister() {
        InputReader reader = InputReader.getInstance();
        System.out.println("Please enter your registration details:");
        System.out.println();
        String username = reader.readString("Choose a username: ");
        String password = reader.readStringHidden("Choose a password: ");
        String confirmPassword = reader.readStringHidden("Confirm password: ");
        System.out.println();

        User user = AuthService.register(username, password, confirmPassword, Permission.CLIENT);

        if (user != null) {
            currentUser = user;
            System.out.println("Registration successful! You can now log in.");
            System.out.println();
        } else {
            System.out.println("Registration failed. Please try again.");
            System.out.println();
        }
    }

}
