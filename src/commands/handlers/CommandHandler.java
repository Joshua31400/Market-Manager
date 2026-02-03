package commands.handlers;

import authentification.User;
import commands.src.CommandRequest;

import java.util.List;

public abstract class CommandHandler {

    protected User user;
    protected List<String> args;


    public CommandHandler(CommandRequest request) {
        List<String> args = request.getArgs();
        args.removeFirst();
        this.user = request.getUser();
        this.args = args;
    }

    public abstract void execute();
}
