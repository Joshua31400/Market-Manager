import authentification.AuthService;
import authentification.Permission;
import data.UserDAO;
import models.User;
import commands.src.CommandInterpreter;
import utils.InputReader;
import utils.TerminalUtils;

public class App {
    private User currentUser = null;

    public void run() {
        TerminalUtils.clearTerminal();
        while (true) {
            if (currentUser == null) {
                currentUser = AuthService.requestAuth();
            } else {
                String input = InputReader.getInstance().readString(currentUser.getUsername() + " (" + currentUser.getPermission() + ") " + "> ");
                if (input.trim().equalsIgnoreCase("logout")) {
                    currentUser = null;
                    TerminalUtils.clearTerminal();
                    System.out.println("You have been logged out.");
                    System.out.println();
                    continue;
                }
                if (!input.trim().isEmpty()) System.out.println();
                CommandInterpreter.interpretCommand(input, currentUser);
            }
        }
    }

}
