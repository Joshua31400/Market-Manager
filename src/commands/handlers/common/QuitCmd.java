package commands.handlers.common;

import commands.handlers.CommandHandler;
import commands.src.CommandRequest;
import utils.Colors;

public class QuitCmd extends CommandHandler {
    public QuitCmd(CommandRequest request) {
        super(request);
    }

    @Override
    public void execute() {
        if (!validateArgs()) {
            return;
        }
        System.out.println(Colors.primary("Exiting the application. Goodbye!"));
        System.out.println();
        System.exit(0);
    }

    private boolean validateArgs() {
        if (!args.isEmpty()) {
            System.out.println(Colors.warning("Usage: quit"));
            System.out.println();
            return false;
        }
        return true;
    }
}