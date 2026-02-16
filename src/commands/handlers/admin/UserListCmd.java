package commands.handlers.admin;

import commands.handlers.CommandHandler;
import commands.src.CommandRequest;
import data.UserDAO;
import models.User;
import authentification.Permission;
import utils.Colors;

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
            System.out.println(Colors.warning("No users found."));
            return;
        }

        System.out.println(Colors.primary("User List:"));
        System.out.println(Colors.primary("-".repeat(50)));
        for (User user : users) {
            System.out.printf(Colors.secondary("ID: ") + Colors.data("%d") + Colors.secondary(" | Username: ") + Colors.data("%s") + Colors.secondary(" | Permission: ") + Colors.data("%s%n"),
                    user.getId(),
                    user.getUsername(),
                    user.getPermission());
        }
        System.out.println(Colors.primary("-".repeat(50)));
        System.out.println(Colors.secondary("Total users: ") + Colors.data(String.valueOf(users.size())));
        System.out.println();
    }

    private boolean validatePermission() {
        if (user == null || user.getPermission() != Permission.ADMIN) {
            System.out.println(Colors.warning("Access denied. This command is restricted to administrators."));
            System.out.println();
            return false;
        }
        return true;
    }

    private boolean validateArgs() {
        if (!args.isEmpty()) {
            System.out.println(Colors.warning("Usage: userlist"));
            System.out.println();
            return false;
        }
        return true;
    }
}