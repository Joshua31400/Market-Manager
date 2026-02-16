package commands.handlers;


import commands.handlers.common.HelpCmd;
import commands.handlers.common.QuitCmd;
import commands.handlers.common.UserListCmd;
import commands.handlers.common.AddUserCmd;
import commands.handlers.common.UpdatePermissionCmd;
import commands.src.CommandRequest;

public class CommandHandlerFactory {
    public static CommandHandler create(CommandRequest request) {
       String commandName = request.getArgs().getFirst();
        return switch (commandName) {
            case "help" -> new HelpCmd(request);
            case "quit" -> new QuitCmd(request);
            case "userlist" -> new UserListCmd(request);
            case "adduser" -> new AddUserCmd(request);
            case "updatepermission" -> new UpdatePermissionCmd(request);
            default -> null;
        };
    }
}
