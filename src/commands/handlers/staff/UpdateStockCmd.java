package commands.handlers.staff;

import commands.handlers.CommandHandler;
import commands.src.CommandRequest;
import data.ProductDAO;
import models.Product;

/**
 * Command handler for updating an existing product's details.
 * Allows modification of both quantity and price using either ID or Name.
 */
public class UpdateStockCmd extends CommandHandler {

    public UpdateStockCmd(CommandRequest request) {
        super(request);
    }

    @Override
    public void execute() {
        // Expected usage: update <id_or_name> <new_quantity> <new_price>
        if (args.size() < 3) {
            System.out.println("Usage: update <id_or_name> <new_quantity> <new_price>");
            return;
        }

        String identifier = args.get(0);
        ProductDAO dao = ProductDAO.getInstance();

        // HYBRID SEARCH (Find by ID first, then by Name if ID parsing fails)
        Product product = dao.findByIdOrName(identifier);

        if (product == null) {
            System.out.println("Error: Product '" + identifier + "' not found.");
            return;
        }

        try {
            // Parsing new values from arguments
            int newQty = Integer.parseInt(args.get(1));
            double newPrice = Double.parseDouble(args.get(2));

            // UPDATING PRODUCT ATTRIBUTES
            product.setQuantity(newQty);
            product.setPrice(newPrice);

            // Persist changes to the JSON database
            dao.saveData();

            System.out.println("Success: Product '" + product.getName() + "' has been updated.");
            System.out.println("New inventory: " + newQty + " | New price: " + newPrice + "€");

        } catch (NumberFormatException e) {
            // Error handling for non-numeric input in quantity or price fields
            System.out.println("Error: Quantity and Price must be valid numbers.");
        }
    }
}