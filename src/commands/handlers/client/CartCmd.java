package commands.handlers.client;

import authentification.Permission;
import commands.handlers.CommandHandler;
import commands.src.CommandRequest;
import data.CartDAO;
import models.Cart;

public class CartCmd extends CommandHandler {
    public CartCmd(CommandRequest request) {
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

        if (cart.getProducts().isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            System.out.println("Your Cart:");
            System.out.println("-".repeat(50));
            cart.getProducts().forEach(product -> {
                System.out.printf("ID: %d | Name: %s | Quantity: %d | Price: $%.2f%n",
                        product.getId(),
                        product.getName(),
                        product.getQuantity(),
                        product.getPrice());
            });
            System.out.println("-".repeat(50));
            int totalProducts = cart.getTotalProducts();
            double totalPrice = cart.getTotalPrice();
            System.out.printf("Total: %s%n", totalProducts);
            System.out.printf("Total Price: $%.2f%n", totalPrice);
            System.out.println();
        }
    }

    private boolean validatePermission() {
        if (user.getPermission() != Permission.CLIENT) {
            System.out.println("Access denied. This command is restricted to clients.");
            System.out.println();
            return false;
        }
        return true;
    }

    private boolean validateArgs() {
        if (!args.isEmpty()) {
            System.out.println("Usage: cart");
            System.out.println();
            return false;
        }
        return true;
    }
}
