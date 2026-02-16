package commands.handlers;

import commands.handlers.admin.AddUserCmd;
import commands.handlers.admin.RemoveUserCmd;
import commands.handlers.admin.UpdatePermissionCmd;
import commands.handlers.admin.UserListCmd;
import commands.handlers.client.AddCmd;
import commands.handlers.client.CartCmd;
import commands.handlers.client.EmptyCartCmd;
import commands.handlers.client.RemoveCmd;
import commands.handlers.common.ClearCmd;
import commands.handlers.common.HelpCmd;
import commands.handlers.common.QuitCmd;
import commands.handlers.common.ViewStockCmd;
import commands.handlers.staff.*;
import commands.src.CommandRequest;

public class CommandHandlerFactory {
    public static CommandHandler create(CommandRequest request) {
       String commandName = request.getArgs().getFirst();
        return switch (commandName) {
            // Common commands
            case "help" -> new HelpCmd(request);
            case "catalog" -> new ViewStockCmd(request);
            case "clear" -> new ClearCmd(request);
            case "quit" -> new QuitCmd(request);

            // Admin commands
            case "userlist" -> new UserListCmd(request);
            case "adduser" -> new AddUserCmd(request);
            case "updatepermission" -> new UpdatePermissionCmd(request);
            case "removeuser" -> new RemoveUserCmd(request);

            // Staff commands
            case "additem" -> new AddStockCmd(request);
            case "removeitem" -> new RemoveStockCmd(request);
            case "updateitemname" -> new UpdateStockNameCmd(request);
            case "updateitemquantity" -> new UpdateStockQuantityCmd(request);
            case "updateitemprice" -> new UpdateStockPriceCmd(request);

            // Client commands
            case "add" -> new AddCmd(request);
            case "remove" -> new RemoveCmd(request);
            case "emptycart" -> new EmptyCartCmd(request);
            case "cart" -> new CartCmd(request);

            default -> null;
        };
    }
}
