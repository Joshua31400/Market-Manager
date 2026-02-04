package commands.src;

import models.User;

import java.util.List;

public class CommandRequest {
    private User user;
    private List<String> args;

    public CommandRequest(User user, List<String> args) {
        this.user = user;
        this.args = args;
    }

    public User getUser() {
        return user;
    }
    public List<String> getArgs() {
        return args;
    }
}
