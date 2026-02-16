package commands.handlers.client;

import commands.handlers.CommandHandler;
import commands.src.CommandRequest;
import data.CartDAO;
import models.Cart;

public class EmptyCartCmd extends CommandHandler {
    public EmptyCartCmd(CommandRequest request) {
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

        Cart cart = CartDAO.getInstance().selectByUserId(user.getId());
        if (cart == null) {
            System.out.println("You have no cart.");
            boolean success = CartDAO.getInstance().insert(user.getId());
            if (!success) {
                System.out.println("Failed to create a cart for you. Please try again later.");
                System.out.println();
                return;
            }
            cart = CartDAO.getInstance().selectByUserId(user.getId());
        }

        cart.getProducts().clear();

        boolean success = CartDAO.getInstance().update(cart);
        if (!success) {
            System.out.println("Failed to empty your cart. Please try again later.");
            System.out.println();
        }
        System.out.println("Your cart has been emptied.");
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
        if (!args.isEmpty()) {
            System.out.println("Usage: emptycart");
            System.out.println();
            return false;
        }
        return true;
    }
}
