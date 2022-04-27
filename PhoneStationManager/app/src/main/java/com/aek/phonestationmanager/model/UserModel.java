package com.aek.phonestationmanager.model;

public class UserModel {

    public int id;
    public String username;
    public String password;
    public String token;

    public static UserModel userLoginData(String name, String password) {
        return new UserModel(0, name, password, "");
    }

    public UserModel(int id, String name, String password, String token) {
        this.id = id;
        this.username = name;
        this.password = password;
        this.token = token;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
