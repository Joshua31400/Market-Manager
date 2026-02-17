package commands.handlers.common;

import authentification.Permission;
import commands.handlers.CommandHandler;
import commands.src.CommandRequest;
import utils.Colors;

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
            System.out.println(Colors.warning("Usage: help"));
            return false;
        }
        return true;
    }

    private String getCommonHelpMessage() {
        return Colors.primary("Common commands:") + "\n" +
                Colors.primary(" - help") + Colors.secondary(" - Show this help message.\n") +
                Colors.primary(" - catalog") + Colors.secondary(" - List all items in the catalog.\n") +
                Colors.primary(" - clear") + Colors.secondary(" - Clear the terminal.\n") +
                Colors.primary(" - logout") + Colors.secondary(" - Log out of your account.\n") +
                Colors.primary(" - quit") + Colors.secondary(" - Exit the application.\n");
    }

    private String getClientHelpMessage() {
        return Colors.primary("Client commands:") + "\n" +
                Colors.primary(" - add <id> <quantity>") + Colors.secondary(" - Add item to cart by id.\n") +
                Colors.primary(" - remove <id>") + Colors.secondary(" - Remove item from cart by id.\n") +
                Colors.primary(" - emptycart") + Colors.secondary(" - Empty your shopping cart.\n") +
                Colors.primary(" - cart") + Colors.secondary(" - View items in your shopping cart.\n") +
                Colors.primary(" - buy") + Colors.secondary(" - Purchase items in your cart.\n");
    }

    private String getStaffHelpMessage() {
        return Colors.primary("Staff commands:") + "\n" +
                Colors.primary(" - additem <name> <quantity> <price>") + Colors.secondary(" - Add a new item to the catalog.\n") +
                Colors.primary(" - updateitemname <id> <name>") + Colors.secondary(" - Update an existing item's name in the catalog by its ID.\n") +
                Colors.primary(" - updateitemprice <id> <price>") + Colors.secondary(" - Update an existing item's price in the catalog by its ID.\n") +
                Colors.primary(" - updateitemquantity <id> <quantity>") + Colors.secondary(" - Update an existing item's quantity in the catalog.\n") +
                Colors.primary(" - removeitem <id>") + Colors.secondary(" - Remove an item from the catalog by its ID.\n");
    }

    private String getAdminHelpMessage() {
        return Colors.primary("Admin commands:") + "\n" +
                Colors.primary(" - userlist") + Colors.secondary(" - List all users in the system.\n") +
                Colors.primary(" - adduser <username> <password> <permission>") + Colors.secondary(" - Add a new user with the specified username, password, and permission level (CLIENT, STAFF, ADMIN).\n") +
                Colors.primary(" - updateusername <id> <new_username>") + Colors.secondary(" - Update the username of an user by its ID.\n") +
                Colors.primary(" - updatepassword <id> <new_password>") + Colors.secondary(" - Update the password of an user by its ID.\n") +
                Colors.primary(" - updatepermission <id> <status>") + Colors.secondary(" - Update the permission of an user by its ID. Status can be CLIENT, STAFF or ADMIN.\n") +
                Colors.primary(" - removeuser <id>") + Colors.secondary(" - Remove a user by their username.\n");
    }
}