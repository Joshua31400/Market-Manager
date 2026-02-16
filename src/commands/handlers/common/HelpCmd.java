package commands.handlers.common;

import authentification.Permission;
import commands.handlers.CommandHandler;
import commands.src.CommandRequest;

public class HelpCmd extends CommandHandler {
    public HelpCmd(CommandRequest request) {super(request);}
    @Override
    public void execute() {
        if (!validateArgs()) {
            return;
        }
        System.out.println(getCommonHelpMessage());

        Permission permission = user.getPermission();

        if (permission == Permission.CLIENT) {
            System.out.println(getClientHelpMessage());
        } else if (permission == Permission.STAFF) {
            System.out.println(getClientHelpMessage());
            System.out.println(getStaffHelpMessage());
        } else if (permission == Permission.ADMIN) {
            System.out.println(getStaffHelpMessage());
            System.out.println(getAdminHelpMessage());
        }
    }

    private boolean validateArgs() {
        if (!args.isEmpty()) {
            System.out.println("Usage: help");
            return false;
        }
        return true;
    }

    private String getCommonHelpMessage() {
        return """
               Common commands:
               - help: Show this help message.
               - catalog - List all items in the catalog.
               - clear: Clear the terminal.
               - quit: Exit the application.
               """;
    }

    private String getClientHelpMessage() {
        return """
               Client commands:
                - add <id> - Add item to cart by id.
                - remove <id> - Remove item from cart by id.
                - cart - View items in your shopping cart.
                - buy - Purchase items in your cart.
               """;
    }

    private String getStaffHelpMessage() {
        return """
               Staff commands:
                - additem <name> <quantity> <price> - Add a new item to the catalog.
                - updateitemname <id> <name> - Update an existing item's name in the catalog by its ID.
                - updateitemprice <id> <price> - Update an existing item's price in the catalog by its ID.
                - updateitemquantity <id> <quantity> - Update an existing item's quantity in the
                - removeitem <id> - Remove an item from the catalog by its ID.
               """;
    }

    private String getAdminHelpMessage() {
        return """
               Admin commands:
                - userlist - List all users in the system.
                - adduser <username> <password> <permission> - Add a new user with the specified username, password, and permission level (CLIENT, STAFF, ADMIN).
                - updatepermission <id> <status> - Update the permission of an user by its ID. Status can be CLIENT, STAFF or ADMIN.
                - removeuser <id> - Remove a user by their username.
               """;
    }
}
