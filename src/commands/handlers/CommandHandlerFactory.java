package commands.handlers;

import authentification.Permission;
import commands.handlers.common.HelpCmd;
import commands.handlers.common.QuitCmd;
import commands.handlers.staff.AddStockCmd;
import commands.handlers.staff.DeleteStockCmd;
import commands.handlers.staff.ViewStockCmd;
import commands.handlers.staff.UpdateStockCmd;
import commands.src.CommandRequest;
import models.User;

/**
 * Factory class responsible for instantiating the appropriate CommandHandler
 * based on the user's input and permissions.
 */
public class CommandHandlerFactory {

    /**
     * Creates a CommandHandler based on the command name provided in the request.
     * Checks for user permissions before granting access to staff-specific commands.
     * * @param request The object containing the command name, arguments, and user session.
     * @return A specific CommandHandler instance, or null if the command is unknown or unauthorized.
     */
    public static CommandHandler create(CommandRequest request) {
        String commandName = request.getArgs().getFirst();
        User user = request.getUser();

        return switch (commandName) {
            // --- PUBLIC COMMANDS (Accessible by everyone) ---
            case "help" -> new HelpCmd(request);
            case "quit" -> new QuitCmd(request);

            // --- STAFF COMMANDS (Permission restricted) ---
            case "add" -> {
                if (user.getPermission() == Permission.STAFF || user.getPermission() == Permission.ADMIN) {
                    yield new AddStockCmd(request);
                }
                yield null; // Access denied
            }
            case "delete" -> {
                if (user.getPermission() == Permission.STAFF || user.getPermission() == Permission.ADMIN) {
                    yield new DeleteStockCmd(request);
                }
                yield null;
            }
            case "update" -> {
                if (user.getPermission() == Permission.STAFF || user.getPermission() == Permission.ADMIN) {
                    yield new UpdateStockCmd(request);
                }
                yield null;
            }
            case "stock" -> {
                // Anyone can view the stock; no permission check required
                yield new ViewStockCmd(request);
            }

            // --- UNKNOWN COMMAND ---
            default -> null;
        };
    }
}