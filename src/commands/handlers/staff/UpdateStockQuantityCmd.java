package commands.handlers.staff;

import authentification.Permission;
import commands.handlers.CommandHandler;
import commands.src.CommandRequest;
import data.ProductDAO;
import models.Product;

public class UpdateStockQuantityCmd extends CommandHandler {

    public UpdateStockQuantityCmd(CommandRequest request) {
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
        int newQuantity = Integer.parseInt(args.get(1));

        Product existingProduct = ProductDAO.getInstance().selectById(productId);
        if (existingProduct == null) {
            System.out.println("Product not found.");
            return;
        }


        Product updatedProduct = new Product(
                existingProduct.getId(),
                existingProduct.getName(),
                newQuantity,
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
            System.out.println("Usage: updateitemquantity <id> <quantity>");
            return false;
        }

        try {
            Integer.parseInt(args.get(0));
        } catch (NumberFormatException e) {
            System.out.println("Invalid product ID. Must be a number.");
            return false;
        }

        try {
            int newQuantity = Integer.parseInt(args.get(1));
            if (newQuantity < 0) {
                System.out.println("Quantity cannot be negative.");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid quantity. Must be a number.");
            return false;
        }

        return true;
    }
}