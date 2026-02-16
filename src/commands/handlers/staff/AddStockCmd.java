package commands.handlers.staff;

import authentification.Permission;
import commands.handlers.CommandHandler;
import commands.src.CommandRequest;
import data.ProductDAO;
import utils.Colors;

public class AddStockCmd extends CommandHandler {

    public AddStockCmd(CommandRequest request) {
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

        String name = args.get(0);
        int qty = Integer.parseInt(args.get(1));
        double price = Double.parseDouble(args.get(2));


        if (ProductDAO.getInstance().selectByName(name) != null) {
            System.out.println(Colors.warning("The product '") + Colors.data(name) + Colors.warning("' already exists in the stock."));
            System.out.println();
            return;
        }

        ProductDAO.getInstance().insert(name, qty, price);
    }

    private boolean validatePermission() {
        if (user.getPermission() != Permission.STAFF && user.getPermission() != Permission.ADMIN) {
            System.out.println(Colors.warning("Access denied. This command is restricted to staff and administrators."));
            System.out.println();
            return false;
        }
        return true;
    }

    private boolean validateArgs() {
        if (args.size() != 3) {
            System.out.println(Colors.warning("Usage: add <name> <quantity> <price>"));
            System.out.println();
            return false;
        }

        try {
            int newQuantity = Integer.parseInt(args.get(1));
            if (newQuantity < 0) {
                System.out.println(Colors.warning("Quantity cannot be negative."));
                System.out.println();
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println(Colors.warning("Invalid quantity. Must be a number."));
            System.out.println();
            return false;
        }

        try {
            double newPrice = Double.parseDouble(args.get(2));
            if (newPrice < 0) {
                System.out.println(Colors.warning("Price cannot be negative."));
                System.out.println();
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println(Colors.warning("Invalid price format. Must be a number."));
            System.out.println();
            return false;
        }

        return true;
    }
}