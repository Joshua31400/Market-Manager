package commands.handlers.staff;

import authentification.Permission;
import commands.handlers.CommandHandler;
import commands.src.CommandRequest;
import data.ProductDAO;
import models.Product;

public class RemoveStockCmd extends CommandHandler {

    public RemoveStockCmd(CommandRequest request) {
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

        int id = Integer.parseInt(args.get(0));
        Product targetProduct = ProductDAO.getInstance().selectById(id);

        if (targetProduct == null) {
            System.out.println("Product not found.");
            return;
        }

        ProductDAO.getInstance().delete(id);
    }

    private boolean validatePermission() {
        if (user.getPermission() != Permission.STAFF && user.getPermission() != Permission.ADMIN) {
            System.out.println("Access denied. This command is restricted to staff and administrators.");
            return false;
        }
        return true;
    }

    private boolean validateArgs() {
        if (args.size() != 1) {
            System.out.println("Usage: removeitem <id>");
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