package commands.handlers.client;

import commands.handlers.CommandHandler;
import commands.src.CommandRequest;
import data.CartDAO;
import models.Cart;
import models.Product;

public class AddCmd extends CommandHandler {
    public AddCmd(CommandRequest request) {
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
        int quantity = Integer.parseInt(args.get(1));

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

        Product product = data.ProductDAO.getInstance().selectById(productId);
        if (product == null) {
            System.out.println("Product not found.");
            System.out.println();
            return;
        }

        if (product.getQuantity() < quantity) {
            System.out.println("Not enough stock available. Current stock: " + product.getQuantity());
            System.out.println();
            return;
        }
        Product productToAdd = new Product(
                product.getId(),
                product.getName(),
                quantity,
                product.getPrice()
        );
        cart.getProducts().add(productToAdd);

        CartDAO.getInstance().update(cart);
        System.out.println("Added " + quantity + " of '" + product.getName() + "' to your cart.");
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
        if (args.size() != 2) {
            System.out.println("Usage: add <id> <quantity>");
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
            int quantity = Integer.parseInt(args.get(1));
            if (quantity <= 0) {
                System.out.println("Quantity cannot be negative.");
                System.out.println();
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid quantity. Must be a number.");
            System.out.println();
            return false;
        }

        return true;
    }
}
