package commands.handlers.admin;

import authentification.Permission;
import commands.handlers.CommandHandler;
import commands.src.CommandRequest;
import data.CartDAO;
import data.UserDAO;
import models.User;
import utils.Colors;

public class RemoveUserCmd extends CommandHandler {
    public RemoveUserCmd(CommandRequest request) {
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


        int userId = Integer.parseInt(args.get(0));

        User targetUser = UserDAO.getInstance().selectById(userId);
        if (targetUser == null) {
            System.out.println(Colors.warning("User not found."));
            System.out.println();
            return;
        }

        if (targetUser.getId() == user.getId()) {
            System.out.println(Colors.warning("You cannot remove yourself."));
            System.out.println();
            return;
        }

        UserDAO.getInstance().delete(userId);
        CartDAO.getInstance().delete(userId);
        System.out.println();
    }

    private boolean validatePermission() {
        if (user.getPermission() != Permission.ADMIN) {
            System.out.println(Colors.warning("Access denied. This command is restricted to administrators."));
            System.out.println();
            return false;
        }
        return true;
    }

    private boolean validateArgs() {
        if (args.size() != 1) {
            System.out.println(Colors.warning("Usage: removeuser <id>"));
            System.out.println();
            return false;
        }

        try {
            Integer.parseInt(args.get(0));
        } catch (NumberFormatException e) {
            System.out.println(Colors.warning("Invalid user ID. Must be a number."));
            System.out.println();
            return false;
        }

        return true;
    }
}
