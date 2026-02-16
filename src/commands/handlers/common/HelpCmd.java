package commands.handlers.common;

import commands.handlers.CommandHandler;
import commands.src.CommandRequest;

public class HelpCmd extends CommandHandler {
    public HelpCmd(CommandRequest request) {
        super(request);
    }
    @Override
    public void execute() {
        if (!validateArgs()) {
            return;
        }
        System.out.println(getCommonHelpMessage());
    }

    private boolean validateArgs() {
        if (!args.isEmpty()) {
            System.out.println("The help command does not take any arguments.");
            return false;
        }
        return true;
    }

    private String getCommonHelpMessage() {
        return """
               Common commands:
               - help: Show this help message.
               - catalog - List all items in the catalog.
               - info <id> - Show information about an item by its ID.
               - clear: Clear the terminal.
               - quit: Exit the application.
               """;
    }

    private String getClientHelpMessage() {
        return """
               Available commands:
                - add <id> - Add item to cart by id.
                - remove <id> - Remove item from cart by id.
                - cart - View items in your shopping cart.
                - buy - Purchase items in your cart.
               """;
    }

    private String getStaffHelpMessage() {
        return """
               Available commands:
                - additem <name> <price> <quantity> - Add a new item to the catalog.
                - removeitem <id> - Remove an item from the catalog by its ID.
               """;
    }

    private String getAdminHelpMessage() {
        return """
               Available commands:
                - userlist - List all users in the system.
                - adduser <username> <password> <permission> - Add a new user with the specified username, password, and permission level (CLIENT, STAFF, ADMIN).
                - updatepermission <id> <status> - Update the permission of an user by its ID. Status can be CLIENT, STAFF or ADMIN.
                - removeuser <id> - Remove a user by their username.
               """;
    }

}
