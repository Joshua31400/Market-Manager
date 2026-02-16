package commands.handlers.client;

import commands.handlers.CommandHandler;
import commands.src.CommandRequest;
import data.CartDAO;
import models.Cart;
import models.Product;
import utils.Colors;

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
            System.out.println(Colors.warning("You have no cart."));
            boolean success = CartDAO.getInstance().insert(user.getId());
            if (!success) {
                System.out.println(Colors.error("Failed to create a cart for you. Please try again later."));
                System.out.println();
                return;
            }
            cart = CartDAO.getInstance().selectByUserId(user.getId());
        }

        Product product = data.ProductDAO.getInstance().selectById(productId);
        if (product == null) {
            System.out.println(Colors.warning("Product not found."));
            System.out.println();
            return;
        }

        for (Product cartProduct : cart.getProducts()) {
            if (cartProduct.getId() == productId) {
                if (product.getQuantity() < quantity + cartProduct.getQuantity()) {
                    System.out.println(Colors.warning("Not enough stock available. Current stock: ") + Colors.data(String.valueOf(product.getQuantity())));
                    System.out.println();
                } else {
                    Product updatedCartProduct = new Product(
                            cartProduct.getId(),
                            cartProduct.getName(),
                            cartProduct.getQuantity() + quantity,
                            cartProduct.getPrice()
                    );
                    cart.getProducts().remove(cartProduct);
                    cart.getProducts().add(updatedCartProduct);
                    boolean success = CartDAO.getInstance().update(cart);
                    if (success) {
                        System.out.println(Colors.success("Added ") + Colors.data(String.valueOf(quantity)) + Colors.success(" of '") + Colors.data(product.getName()) + Colors.success("' to your cart."));
                    } else {
                        System.out.println(Colors.error("Failed to update your cart. Please try again later."));
                    }
                    System.out.println();
                }
                return;
            }
        }

        if (product.getQuantity() < quantity) {
            System.out.println(Colors.warning("Not enough stock available. Current stock: ") + Colors.data(String.valueOf(product.getQuantity())));
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
        System.out.println(Colors.success("Added ") + Colors.data(String.valueOf(quantity)) + Colors.success(" of '") + Colors.data(product.getName()) + Colors.success("' to your cart."));
        System.out.println();
    }

    private boolean validatePermission() {
        if (user.getPermission() != authentification.Permission.CLIENT) {
            System.out.println(Colors.warning("Access denied. This command is restricted to clients."));
            System.out.println();
            return false;
        }
        return true;
    }

    private boolean validateArgs() {
        if (args.size() != 2) {
            System.out.println(Colors.warning("Usage: add <id> <quantity>"));
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

        try {
            int quantity = Integer.parseInt(args.get(1));
            if (quantity <= 0) {
                System.out.println(Colors.warning("Quantity cannot be negative."));
                System.out.println();
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println(Colors.warning("Invalid quantity. Must be a number."));
            System.out.println();
            return false;
        }

        return true;
    }
}