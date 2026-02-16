package commands.handlers.admin;

import commands.handlers.CommandHandler;
import commands.src.CommandRequest;
import data.UserDAO;
import models.User;
import authentification.Permission;

import java.util.List;

public class UserListCmd extends CommandHandler {
    public UserListCmd(CommandRequest request) {
        super(request);
    }

    @Override
    public void execute() {
        if (!validatePermission()) {
            return;
        }

        if (!validateArgs()) {
            return;
        }


        List<User> users = UserDAO.getInstance().selectAll();

        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }

        System.out.println("User List:");
        System.out.println("-".repeat(50));
        for (User user : users) {
            System.out.printf("ID: %d | Username: %s | Permission: %s%n",
                    user.getId(),
                    user.getUsername(),
                    user.getPermission());
        }
        System.out.println("-".repeat(50));
        System.out.println("Total users: " + users.size());
        System.out.println();
    }

    private boolean validatePermission() {
        if (user == null || user.getPermission() != Permission.ADMIN) {
            System.out.println("Access denied. This command is restricted to administrators.");
            System.out.println();
            return false;
        }
        return true;
    }

    private boolean validateArgs() {
        if (!args.isEmpty()) {
            System.out.println("Usage: userlist");
            System.out.println();
            return false;
        }
        return true;
    }
}
