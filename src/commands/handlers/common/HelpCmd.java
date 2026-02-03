package commands.handlers.common;

import commands.handlers.CommandHandler;
import commands.src.CommandRequest;

public class HelpCmd extends CommandHandler {
    public HelpCmd(CommandRequest request) {
        super(request);
    }
    @Override
    public void execute() {
        if (!validateArgs()) {
            return;
        }
        System.out.println(getCommonHelpMessage());
    }

    private boolean validateArgs() {
        if (!args.isEmpty()) {
            System.out.println("The help command does not take any arguments.");
            return false;
        }
        return true;
    }

    private String getCommonHelpMessage() {
        return """
               Common commands:
               - help: Show this help message.
               - clear: Clear the terminal.
               - exit: Exit the application.
               """;
    }

    private String getClientHelpMessage() {
        return """
               Available commands:
                - catalog - List all items in the catalog.
                - info <id> - Show information about an item by its ID.
                - add <id> - Add item to cart by id.
                - remove <id> - Remove item from cart by id.
                - cart - View items in your shopping cart.
                - buy - Purchase items in your cart.
               """;
    }

}
