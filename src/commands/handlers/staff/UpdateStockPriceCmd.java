package commands.handlers.staff;

import authentification.Permission;
import commands.handlers.CommandHandler;
import commands.src.CommandRequest;
import data.Database;
import data.ProductDAO;
import models.Product;

public class UpdateStockPriceCmd extends CommandHandler {

    public UpdateStockPriceCmd(CommandRequest request) {
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

        int productId = Integer.parseInt(args.get(0));
        double newPrice = Double.parseDouble(args.get(1));

        Product existingProduct = ProductDAO.getInstance().selectById(productId);
        if (existingProduct == null) {
            System.out.println("Product not found.");
            System.out.println();
            return;
        }


        Product updatedProduct = new Product(
                existingProduct.getId(),
                existingProduct.getName(),
                existingProduct.getQuantity(),
                newPrice
        );

        ProductDAO.getInstance().update(updatedProduct);
        System.out.println();

    }

    private boolean validatePermission() {
        if (user.getPermission() != Permission.STAFF && user.getPermission() != Permission.ADMIN) {
            System.out.println("Access denied. This command is restricted to staff and administrators.");
            System.out.println();
            return false;
        }
        return true;
    }

    private boolean validateArgs() {
        if (args.size() != 2) {
            System.out.println("Usage: updateitemprice <id> <price>");
            System.out.println();
            return false;
        }

        try {
            Integer.parseInt(args.get(0));
        } catch (NumberFormatException e) {
            System.out.println("Invalid product ID. Must be a number.");
            System.out.println();
            return false;
        }

        try {
            double newPrice = Double.parseDouble(args.get(1));
            if (newPrice < 0) {
                System.out.println("Price cannot be negative.");
                System.out.println();
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid price format. Must be a number.");
            System.out.println();
            return false;
        }

        return true;
    }
}