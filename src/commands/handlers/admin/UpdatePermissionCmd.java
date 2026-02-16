package commands.handlers.admin;

import authentification.Permission;
import commands.handlers.CommandHandler;
import commands.src.CommandRequest;
import data.CartDAO;
import data.UserDAO;
import models.User;
import utils.Colors;

public class UpdatePermissionCmd extends CommandHandler {
    public UpdatePermissionCmd(CommandRequest request) {
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
        String permissionStr = args.get(1).toUpperCase();

        User targetUser = UserDAO.getInstance().selectById(userId);
        if (targetUser == null) {
            System.out.println(Colors.warning("User not found."));
            System.out.println();
            return;
        }

        User updatedUser = new User(
                targetUser.getId(),
                targetUser.getUsername(),
                targetUser.getPasswordHash(),
                Permission.valueOf(permissionStr)
        );
        UserDAO.getInstance().update(updatedUser);

        if (targetUser.getId() == user.getId()) {
            System.out.println(Colors.success("Your permissions have been updated. Please log in again to see the changes."));
            System.out.println();
        }

        if (permissionStr.equals("CLIENT")) {
            CartDAO.getInstance().insert(targetUser.getId());
        } else {
            CartDAO.getInstance().delete(targetUser.getId());
        }
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
        if (args.size() != 2) {
            System.out.println(Colors.warning("Usage: updatepermission <id> <permission>"));
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

        String permissionStr = args.get(1).toUpperCase();
        if (!permissionStr.equals("CLIENT") && !permissionStr.equals("STAFF") && !permissionStr.equals("ADMIN")) {
            System.out.println(Colors.warning("Invalid permission. Use CLIENT, STAFF or ADMIN."));
            System.out.println();
            return false;
        }

        return true;
    }
}
