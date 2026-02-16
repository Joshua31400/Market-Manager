package commands.handlers.client;

import authentification.Permission;
import commands.handlers.CommandHandler;
import commands.src.CommandRequest;
import data.CartDAO;
import models.Cart;
import utils.Colors;

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
            System.out.println(Colors.warning("You have no cart."));
            boolean success = CartDAO.getInstance().insert(user.getId());
            if (!success) {
                System.out.println(Colors.error("Failed to create a cart for you. Please try again later."));
                System.out.println();
                return;
            }
            cart = CartDAO.getInstance().selectByUserId(user.getId());
        }

        if (cart.getProducts().isEmpty()) {
            System.out.println(Colors.warning("Your cart is empty."));
            System.out.println();
        } else {
            System.out.println(Colors.primary("Your Cart:"));
            System.out.println(Colors.primary("-".repeat(50)));
            cart.getProducts().forEach(product -> {
                System.out.printf(Colors.secondary("ID: ") + Colors.data("%d") + Colors.secondary(" | Name: ") + Colors.data("%s") + Colors.secondary(" | Quantity: ") + Colors.data("%d") + Colors.secondary(" | Price: $") + Colors.data("%.2f%n"),
                        product.getId(),
                        product.getName(),
                        product.getQuantity(),
                        product.getPrice());
            });
            System.out.println(Colors.primary("-".repeat(50)));
            int totalProducts = cart.getTotalProducts();
            double totalPrice = cart.getTotalPrice();
            System.out.printf(Colors.secondary("Total: ") + Colors.data("%s%n"), totalProducts);
            System.out.printf(Colors.secondary("Total Price: $") + Colors.data("%.2f%n"), totalPrice);
            System.out.println();
        }
    }

    private boolean validatePermission() {
        if (user.getPermission() != Permission.CLIENT) {
            System.out.println(Colors.warning("Access denied. This command is restricted to clients."));
            System.out.println();
            return false;
        }
        return true;
    }

    private boolean validateArgs() {
        if (!args.isEmpty()) {
            System.out.println(Colors.warning("Usage: cart"));
            System.out.println();
            return false;
        }
        return true;
    }
}