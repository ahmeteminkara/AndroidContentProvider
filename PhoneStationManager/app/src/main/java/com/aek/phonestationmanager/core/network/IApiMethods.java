package com.aek.phonestationmanager.core.network;

import androidx.core.util.Consumer;

import com.aek.phonestationmanager.model.UserModel;

public interface IApiMethods {

    void authUser(String username, String password, Consumer<UserModel> userModelConsumer);

}
