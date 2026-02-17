package commands.handlers.admin;

import authentification.PasswordHasher;
import authentification.Permission;
import commands.handlers.CommandHandler;
import data.UserDAO;
import models.User;
import utils.Colors;

public class UpdatePasswordCmd extends CommandHandler {
    public UpdatePasswordCmd(commands.src.CommandRequest request) {
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
        String newPassword = args.get(1);

        User targetUser = UserDAO.getInstance().selectById(userId);
        if (targetUser == null) {
            System.out.println(Colors.warning("User not found."));
            System.out.println();
            return;
        }

        User updatedUser = new User(
                targetUser.getId(),
                targetUser.getUsername(),
                PasswordHasher.hashPassword(newPassword),
                targetUser.getPermission()
        );

        UserDAO.getInstance().update(updatedUser);
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
            System.out.println(Colors.warning("Usage: updatepassword <id> <new_password>"));
            System.out.println();
            return false;
        }
        try {
            Integer.parseInt(args.get(0));
        } catch (NumberFormatException e) {
            System.out.println(Colors.warning("Invalid user ID. It must be a number."));
            System.out.println();
            return false;
        }
        return true;
    }
}
