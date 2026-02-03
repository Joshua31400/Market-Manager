package commands.handlers.common;

import java.util.List;
import commands.handlers.CommandHandler;
import commands.src.CommandRequest;

public class HelpCmd extends CommandHandler {
    public HelpCmd(CommandRequest request) {
        super(request);
    }
    @Override
    public void execute() {
        System.out.println("Available commands:");
        System.out.println("help - Show this help message");
        System.out.println("quit - Exit the application");
    }

}
