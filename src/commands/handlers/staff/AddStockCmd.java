package commands.handlers.staff;

import commands.handlers.CommandHandler;
import commands.src.CommandRequest;
import data.ProductDAO;

/**
 * Command handler for adding a new product to the inventory.
 * Restricted to Staff and Admin users.
 */
public class AddStockCmd extends CommandHandler {

    public AddStockCmd(CommandRequest request) {
        super(request);
    }

    @Override
    public void execute() {
        // Validate that all required arguments are provided: <name> <quantity> <price>
        if (args.size() < 3) {
            System.out.println("Usage: add <name> <quantity> <price>");
            return;
        }

        String name = args.get(0);
        ProductDAO dao = ProductDAO.getInstance();

        // CHECK IF PRODUCT ALREADY EXISTS
        // We prevent duplicate entries by searching for the product name (case-insensitive)
        if (dao.selectByName(name) != null) {
            System.out.println("Error: The product '" + name + "' already exists in the stock.");
            return;
        }

        try {
            // Parse numerical arguments from the command request
            int qty = Integer.parseInt(args.get(1));
            double price = Double.parseDouble(args.get(2));

            // Persist the new product to the database (JSON file)
            dao.addProduct(name, qty, price);
            System.out.println("Product successfully added!");

        } catch (NumberFormatException e) {
            // Handle cases where quantity or price are not valid numbers
            System.out.println("Error: Invalid number format for quantity or price.");
        }
    }
}