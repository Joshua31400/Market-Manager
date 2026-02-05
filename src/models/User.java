package models;

import authentification.Permission;
import data.UserDAO;

import java.io.IOException;

public class User {
    private final int id;
    private final String username;
    private final String passwordHash;
    private final Permission permission;

    public User(int id, String username, String passwordHash, Permission permission) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.permission = permission;
    }

    public User(String username, String passwordHash, Permission permission){
        this.id = UserDAO.getInstance().autoIncrement();
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
