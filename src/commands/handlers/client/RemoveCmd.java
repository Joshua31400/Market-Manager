package commands.handlers.client;

import commands.handlers.CommandHandler;
import commands.src.CommandRequest;
import data.CartDAO;
import data.ProductDAO;
import models.Cart;
import models.Product;

public class RemoveCmd extends CommandHandler {
    public RemoveCmd(CommandRequest request) {
        super(request);
    }

    @Override
    public void execute() {
        if (validatePermission()) {
            return;
        }

        if (!validateArgs()) {
            return;
        }
        int productId = Integer.parseInt(args.get(0));
        Cart cart = CartDAO.getInstance().selectByUserId(user.getId());
        if (cart == null) {
            System.out.println("You have no cart.");

            boolean success = CartDAO.getInstance().insert(user.getId());
            if (!success) {
                System.out.println("Failed to create a cart for you. Please try again later.");
                System.out.println();
                return;
            }
            System.out.println();
            return;
        }

        for (Product product : cart.getProducts()) {
            if (product.getId() == productId) {
                cart.getProducts().remove(product);
                boolean success = CartDAO.getInstance().update(cart);
                System.out.println();
                if (!success) {
                    System.out.println("Failed to remove product from cart. Please try again later.");
                    System.out.println();
                    return;
                }
                System.out.println("Product removed successfully from cart.");
                System.out.println();
                return;
            }
        }

        System.out.println("Product not found in your cart.");
        System.out.println();
    }

    private boolean validatePermission() {
        if (user.getPermission() != authentification.Permission.CLIENT) {
            System.out.println("Access denied. This command is restricted to clients.");
            System.out.println();
            return false;
        }
        return true;
    }

    private boolean validateArgs() {
        if (args.size() != 1) {
            System.out.println("Usage: remove <id>");
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

        return true;
    }
}
