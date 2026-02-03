package authentification;

public class User {
    private int id;
    private String username;
    private String passwordHash;
    private Permission permission;

    public User(int id, String username, String passwordHash, Permission permission) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.permission = permission;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public Permission getPermission() {
        return permission;
    }
}
