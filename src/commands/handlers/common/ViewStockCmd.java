package commands.handlers.common;

import commands.handlers.CommandHandler;
import commands.src.CommandRequest;
import data.ProductDAO;
import models.Product;
import utils.Colors;

public class ViewStockCmd extends CommandHandler {

    public ViewStockCmd(CommandRequest request) {
        super(request);
    }

    @Override
    public void execute() {
        if (!validateArgs()) {
            return;
        }

        var products = ProductDAO.getInstance().selectAll();

        if (products.isEmpty()) {
            System.out.println(Colors.warning("No products in stock."));
            System.out.println();
            return;
        }

        System.out.println(Colors.primary("Current Stock:"));
        System.out.println(Colors.primary("-".repeat(60)));
        for (Product product : products) {
            System.out.printf(Colors.secondary("ID: ") + Colors.data("%d") + Colors.secondary(" | Name: ") + Colors.data("%s") + Colors.secondary(" | Quantity: ") + Colors.data("%d") + Colors.secondary(" | Price: $") + Colors.data("%.2f%n"),
                    product.getId(),
                    product.getName(),
                    product.getQuantity(),
                    product.getPrice());
        }
        System.out.println(Colors.primary("-".repeat(60)));
        System.out.println(Colors.secondary("Total products: ") + Colors.data(String.valueOf(products.size())));
        System.out.println();

    }

    private boolean validateArgs() {
        if (!args.isEmpty()) {
            System.out.println(Colors.warning("Usage: catalog"));
            System.out.println();
            return false;
        }
        return true;
    }
}