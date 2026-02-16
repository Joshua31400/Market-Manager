package commands.handlers.common;

import commands.handlers.CommandHandler;
import commands.src.CommandRequest;
import utils.Colors;
import utils.TerminalUtils;

public class ClearCmd extends CommandHandler {
    public ClearCmd(CommandRequest request) {
        super(request);
    }

    @Override
    public void execute() {
        if (!validateArgs()) {
            return;
        }
        TerminalUtils.clearTerminal();
    }

    private boolean validateArgs() {
        if (!args.isEmpty()) {
            System.out.println(Colors.warning("Usage: clear"));
            return false;
        }
        return true;
    }
}
