package commands.handlers.common;

import authentification.Permission;
import commands.handlers.CommandHandler;
import commands.src.CommandRequest;
import data.UserDAO;
import models.User;

public class UpdatePermissionCmd extends CommandHandler {
    public UpdatePermissionCmd(CommandRequest request) {
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
        String permissionStr = args.get(1).toUpperCase();

        User targetUser = UserDAO.getInstance().selectById(userId);
        if (targetUser == null) {
            System.out.println("User not found.");
            return;
        }

        User updatedUser = new User(
                targetUser.getId(),
                targetUser.getUsername(),
                targetUser.getPasswordHash(),
                Permission.valueOf(permissionStr)
        );
        UserDAO.getInstance().update(updatedUser);

    }

    private boolean validatePermission() {
        if (user == null || user.getPermission() != Permission.ADMIN) {
            System.out.println("Access denied. This command is restricted to administrators.");
            return false;
        }
        return true;
    }

    private boolean validateArgs() {
        if (args.size() != 2) {
            System.out.println("Usage: updatepermission <id> <permission>");
            return false;
        }

        try {
            Integer.parseInt(args.get(0));
        } catch (NumberFormatException e) {
            System.out.println("Invalid user ID. Must be a number.");
            return false;
        }

        String permissionStr = args.get(1).toUpperCase();
        if (!permissionStr.equals("CLIENT") && !permissionStr.equals("STAFF") && !permissionStr.equals("ADMIN")) {
            System.out.println("Invalid permission. Use CLIENT, STAFF or ADMIN.");
            return false;
        }

        return true;
    }
}
