package commands.handlers.common;

import commands.handlers.CommandHandler;
import commands.src.CommandRequest;

public class QuitCmd extends CommandHandler {
    public QuitCmd(CommandRequest request) {
        super(request);
    }

    @Override
    public void execute() {
        System.out.println("Exiting the application. Goodbye!");
        System.exit(0);
    }
}
