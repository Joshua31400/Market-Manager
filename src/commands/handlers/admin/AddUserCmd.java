package commands.handlers.admin;

import authentification.AuthService;
import commands.handlers.CommandHandler;
import commands.src.CommandRequest;
import authentification.Permission;
import models.User;

public class AddUserCmd extends CommandHandler {
    public AddUserCmd(CommandRequest request) {
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

        String username = args.get(0);
        String password = args.get(1);
        String permissionStr = args.get(2).toUpperCase();

        AuthService.register(username, password, password, Permission.valueOf(permissionStr));
    }

    private boolean validatePermission() {
        if (user.getPermission() != Permission.ADMIN) {
            System.out.println("Access denied. This command is restricted to administrators.");
            return false;
        }

        return true;
    }

    private boolean validateArgs() {
        if (args.size() != 3) {
            System.out.println("Usage: adduser <username> <password> <permission>");
            return false;
        }

        String permissionStr = args.get(2).toUpperCase();
        if (!permissionStr.equals("CLIENT") && !permissionStr.equals("STAFF") && !permissionStr.equals("ADMIN")) {
            System.out.println("Invalid permission. Use CLIENT, STAFF or ADMIN.");
            return false;
        }

        return true;
    }
}
