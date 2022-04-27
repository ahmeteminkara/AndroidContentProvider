package com.aek.phonestationmanager.core.network;

import android.os.AsyncTask;

import androidx.core.util.Consumer;

import com.aek.phonestationmanager.model.UserModel;
import com.aek.phonestationmanager.util.Utils;

public class HttpTaskForFakeSleep extends AsyncTask<UserModel, Integer, UserModel> {


    private final Consumer<UserModel> userModelConsumer;

    public HttpTaskForFakeSleep(Consumer<UserModel> userModelConsumer) {
        this.userModelConsumer = userModelConsumer;
    }

    @Override
    protected UserModel doInBackground(UserModel... userModels) {

        try {

            Thread.sleep(2000);

            if (userModels[0].username.equals("admin") && userModels[0].password.equals("1234")) {
                return new UserModel(1, userModels[0].username, userModels[0].password, Utils.generateUUID());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(UserModel userModel) {
        super.onPostExecute(userModel);

        userModelConsumer.accept(userModel);
    }
}
