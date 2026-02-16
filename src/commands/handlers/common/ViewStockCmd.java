package commands.handlers.common;

import commands.handlers.CommandHandler;
import commands.src.CommandRequest;
import data.ProductDAO;
import models.Product;

public class ViewStockCmd extends CommandHandler {

    public ViewStockCmd(CommandRequest request) {
        super(request);
    }

    @Override
    public void execute() {
        if (!validateArgs()) {
            return;
        }

        var products = ProductDAO.getInstance().selectAll();

        if (products.isEmpty()) {
            System.out.println("No products in stock.");
            System.out.println();
            return;
        }

        System.out.println("Current Stock:");
        System.out.println("-".repeat(60));
        for (Product product : products) {
            System.out.printf("ID: %d | Name: %s | Quantity: %d | Price: $%.2f%n",
                    product.getId(),
                    product.getName(),
                    product.getQuantity(),
                    product.getPrice());
        }
        System.out.println("-".repeat(60));
        System.out.println("Total products: " + products.size());
        System.out.println();

    }

    private boolean validateArgs() {
        if (!args.isEmpty()) {
            System.out.println("Usage: catalog");
            System.out.println();
            return false;
        }
        return true;
    }
}