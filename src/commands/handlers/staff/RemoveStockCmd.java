package commands.handlers.staff;

import authentification.Permission;
import commands.handlers.CommandHandler;
import commands.src.CommandRequest;
import data.ProductDAO;
import models.Product;
import utils.Colors;

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
            System.out.println(Colors.warning("Product not found."));
            System.out.println();
            return;
        }

        ProductDAO.getInstance().delete(id);
        System.out.println();
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
        if (args.size() != 1) {
            System.out.println(Colors.warning("Usage: removeitem <id>"));
            System.out.println();
            return false;
        }

        try {
            Integer.parseInt(args.get(0));
        } catch (NumberFormatException e) {
            System.out.println(Colors.warning("Invalid product ID. Must be a number."));
            System.out.println();
            return false;
        }

        return true;
    }
}