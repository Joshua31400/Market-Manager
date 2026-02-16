package commands.handlers.staff;

import commands.handlers.CommandHandler;
import commands.src.CommandRequest;
import data.ProductDAO;
import models.Product;

/**
 * Command handler responsible for removing a product from the stock.
 * Users can delete by ID or by Name.
 */
public class DeleteStockCmd extends CommandHandler {

    public DeleteStockCmd(CommandRequest request) {
        super(request);
    }

    @Override
    public void execute() {
        // Ensure an identifier (ID or Name) is provided
        if (args.isEmpty()) {
            System.out.println("Usage: delete <product_id_or_name>");
            return;
        }

        String identifier = args.get(0);
        ProductDAO dao = ProductDAO.getInstance();

        // Find the product using the hybrid search (ID or Name)
        Product product = dao.findByIdOrName(identifier);

        if (product != null) {
            // If found, delete the product using its unique ID
            if (dao.delete(product.getId())) {
                System.out.println("Product '" + product.getName() + "' has been successfully deleted.");
            } else {
                System.out.println("Error: Could not delete the product.");
            }
        } else {
            // If no match is found in the JSON data
            System.out.println("Error: Product '" + identifier + "' not found.");
        }
    }
}