package commands.handlers.staff;

import commands.handlers.CommandHandler;
import commands.src.CommandRequest;
import data.ProductDAO;
import models.Product;

/**
 * Command handler used to display the current state of the inventory.
 * Lists all products with their ID, Name, Quantity, and Price.
 */
public class ViewStockCmd extends CommandHandler {

    public ViewStockCmd(CommandRequest request) {
        super(request);
    }

    @Override
    public void execute() {
        System.out.println("--- INVENTORY STATUS ---");

        // Iterate through all products retrieved from the DAO
        for (Product p : ProductDAO.getInstance().getAll()) {
            System.out.println(
                    "ID: " + p.getId() +
                            " | Name: " + p.getName() +
                            " | Qty: " + p.getQuantity() +
                            " | Price: " + p.getPrice() + "€"
            );
        }

        // Optional: Check if the inventory is empty to provide user feedback
        if (ProductDAO.getInstance().getAll().isEmpty()) {
            System.out.println("The inventory is currently empty.");
        }
    }
}