package commands.handlers.staff;

import authentification.Permission;
import commands.handlers.CommandHandler;
import commands.src.CommandRequest;
import data.ProductDAO;
import models.Product;

public class UpdateStockNameCmd extends CommandHandler {

    public UpdateStockNameCmd(CommandRequest request) {
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
        String newName = args.get(1);

        Product existingProduct = ProductDAO.getInstance().selectById(productId);
        if (existingProduct == null) {
            System.out.println("Product not found.");
            return;
        }

        if (ProductDAO.getInstance().selectByName(newName) != null) {
            System.out.println("The product name '" + newName + "' is already in use. Please choose a different name.");
            return;
        }

        Product updatedProduct = new Product(
                existingProduct.getId(),
                newName,
                existingProduct.getQuantity(),
                existingProduct.getPrice()
        );

        ProductDAO.getInstance().update(updatedProduct);

    }

    private boolean validatePermission() {
        if (user.getPermission() != Permission.STAFF && user.getPermission() != Permission.ADMIN) {
            System.out.println("Access denied. This command is restricted to staff and administrators.");
            return false;
        }
        return true;
    }

        private boolean validateArgs() {
            if (args.size() != 2) {
                System.out.println("Usage: updateitemname <id> <name>");
                return false;
            }

            try {
                Integer.parseInt(args.get(0));
            } catch (NumberFormatException e) {
                System.out.println("Invalid product ID. Must be a number.");
                return false;
            }

            return true;
        }
}