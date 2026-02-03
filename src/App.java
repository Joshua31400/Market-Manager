import authentification.User;
import commands.src.CommandInterpreter;
import utils.InputReader;

import java.util.Scanner;

public class App {
    private User currentUser = null;

    public void run() {
        while (true) {
            InputReader inputReader = new InputReader();
            String input = inputReader.readCommand("> ");
            CommandInterpreter.interpretCommand(input, null);
        }
    }
}
