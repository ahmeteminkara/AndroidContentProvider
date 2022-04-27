package com.aek.phonestationmanager.core.network;

import androidx.core.util.Consumer;

import com.aek.phonestationmanager.model.UserModel;

public class ApiService implements IApiMethods {

    private ApiService() {
    }

    public final static ApiService getInstance;

    static {
        getInstance = new ApiService();
    }

    @Override
    public void authUser(String username, String password, Consumer<UserModel> userModelConsumer) {
        // can be retrofit request...

        new HttpTaskForFakeSleep(userModelConsumer)
                .execute(UserModel.userLoginData(username, password));


    }
}
