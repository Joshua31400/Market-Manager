package commands.handlers.common;

import authentification.Permission;
import commands.handlers.CommandHandler;
import commands.src.CommandRequest;
import data.UserDAO;
import models.User;

public class RemoveUserCmd extends CommandHandler {
    public RemoveUserCmd(CommandRequest request) {
        super(request);
    }

    @Override
    public void execute() {
        if (!validateArgs()) {
            return;
        }

        if (!validatePermission()) {
            return;
        }

        int userId = Integer.parseInt(args.get(0));

        User targetUser = UserDAO.getInstance().selectById(userId);
        if (targetUser == null) {
            System.out.println("User not found.");
            return;
        }

        if (targetUser.getId() == user.getId()) {
            System.out.println("You cannot remove yourself.");
            return;
        }

        UserDAO.getInstance().delete(userId);
    }

    private boolean validatePermission() {
        if (user == null || user.getPermission() != Permission.ADMIN) {
            System.out.println("Access denied. This command is restricted to administrators.");
            return false;
        }
        return true;
    }

    private boolean validateArgs() {
        if (args.size() != 1) {
            System.out.println("Usage: removeuser <id>");
            return false;
        }

        try {
            Integer.parseInt(args.get(0));
        } catch (NumberFormatException e) {
            System.out.println("Invalid user ID. Must be a number.");
            return false;
        }

        return true;
    }
}
