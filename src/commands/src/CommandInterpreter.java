package commands.src;

import authentification.User;
import commands.handlers.CommandHandler;
import commands.handlers.CommandHandlerFactory;

import java.util.List;

public class CommandInterpreter {
    public static void interpretCommand(String command, User user) {
        if (command.trim().isEmpty()) {
            return;
        }
        command = command.trim().toLowerCase();
        String[] parts = command.split("\\s+");
        List<String> args = new java.util.LinkedList<>(java.util.Arrays.asList(parts));

        CommandRequest request = new CommandRequest(user , args);

        CommandHandler handler = CommandHandlerFactory.create(request);

        if (handler != null) {
            handler.execute();
        } else {
            System.out.println("Unknown command: " + parts[0]);
        }

    }
}
