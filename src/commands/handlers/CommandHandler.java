package commands.handlers;

import authentification.Permission;
import authentification.User;
import commands.src.CommandRequest;

import java.util.List;

public abstract class CommandHandler {

    protected User user;
    protected List<String> args;

    protected Permission requiredPermission = null;

    public CommandHandler(CommandRequest request) {
        List<String> args = request.getArgs();
        args.removeFirst();
        this.user = request.getUser();
        this.args = args;
    }

    public abstract void execute();
}
