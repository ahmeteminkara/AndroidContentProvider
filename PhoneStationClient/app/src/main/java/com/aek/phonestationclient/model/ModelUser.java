package com.aek.phonestationclient.model;

import java.util.Objects;

public class ModelUser {
    final public int id;
    final public String name, password, token;

    public ModelUser(int id, String name, String password, String token) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModelUser userModel = (ModelUser) o;
        return Objects.equals(name, userModel.name) &&
                Objects.equals(token, userModel.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, token);
    }
}
