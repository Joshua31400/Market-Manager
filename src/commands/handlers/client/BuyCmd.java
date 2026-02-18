package commands.handlers.client;

import authentification.Permission;
import commands.handlers.CommandHandler;
import commands.src.CommandRequest;
import data.CartDAO;
import data.ProductDAO;
import models.Cart;
import models.Product;
import transaction.CreditCard;
import transaction.PaymentMethod;
import transaction.Paypal;
import utils.Colors;
import utils.InputReader;

import java.util.List;

public class BuyCmd extends CommandHandler {
    public BuyCmd(CommandRequest request) {
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

        System.out.println(Colors.primary("Please choose a payment method:"));
        System.out.println();
        System.out.println(Colors.colorize("1", Colors.CYAN_BOLD) + Colors.secondary(". credit card"));
        System.out.println(Colors.colorize("2", Colors.CYAN_BOLD) + Colors.secondary(". paypal"));
        System.out.println(Colors.colorize("3", Colors.CYAN_BOLD) + Colors.secondary(". cancel"));
        System.out.println();

        InputReader reader = InputReader.getInstance();
        int choice = reader.readInt("Your choice: ", 1, 3);
        System.out.println();

        PaymentMethod paymentMethod = null;

        switch (choice) {
            case 1 -> {paymentMethod = new CreditCard();}
            case 2 -> {paymentMethod = new Paypal();}
            case 3 -> {
                System.out.println(Colors.primary("Purchase cancelled."));
                System.out.println();
                return;
            }
        }

        double budget = paymentMethod.budget();

        if (budget <= 0) {
            System.out.println(Colors.error("Purchase failed."));
            System.out.println();
            return;
        }

        Cart cart = CartDAO.getInstance().selectByUserId(user.getId());

        System.out.printf(Colors.primary("Total to pay: $") + Colors.data("%.2f%n"), cart.getTotalPrice());
        System.out.println();

        if (budget < cart.getTotalPrice()) {
            System.out.println(Colors.error("Insufficient funds. Purchase failed."));
            System.out.println();
            return;
        }

        if (!validateStock(cart)) {
            return;
        }

        for (Product product : cart.getProducts()) {
            Product stockProduct = ProductDAO.getInstance().selectById(product.getId());
            Product newProduct = new Product(stockProduct.getId(), stockProduct.getName(), stockProduct.getQuantity() - product.getQuantity(), stockProduct.getPrice());
            ProductDAO.getInstance().update(newProduct);
        }
        System.out.println();

        cart.getProducts().clear();
        CartDAO.getInstance().update(cart);

        System.out.println(Colors.success("Purchase successful!"));
        System.out.println();
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
            System.out.println("Usage: buy");
            System.out.println();
            return false;
        }
        return true;
    }

    private boolean validateStock(Cart cart) {
        ProductDAO dao = ProductDAO.getInstance();

        boolean success = true;

        for (Product product : cart.getProducts()) {
            if (dao.selectById(product.getId()) == null) {
                System.out.println(Colors.warning("Product " + product.getName() + " is not in stock."));
                success = false;
            }

            if (dao.selectById(product.getId()).getQuantity() < product.getQuantity()) {
                System.out.println(Colors.warning("Not enough " + product.getName() + " in stock."));
                success = false;
            }
        }
        System.out.println();
        return success;
    }
}
