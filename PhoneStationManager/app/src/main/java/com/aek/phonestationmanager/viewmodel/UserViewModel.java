package com.aek.phonestationmanager.viewmodel;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aek.phonestationmanager.core.network.ApiService;
import com.aek.phonestationmanager.core.provider.AppProvider;
import com.aek.phonestationmanager.model.UserModel;
import com.aek.phonestationmanager.view.MainActivity;

import java.util.Objects;

public class UserViewModel extends ViewModel {

    private static UserViewModel userViewModel;

    public static UserViewModel getInstance() {
        if (userViewModel == null)
            userViewModel = new UserViewModel();

        return userViewModel;
    }


    public final MutableLiveData<Boolean> loginProcessingMutableLiveData = new MutableLiveData<>();

    public final MutableLiveData<UserModel> userMutableLiveData = new MutableLiveData<>();


    public void doLogin(String username, String password,Runnable onProcessFinish) {
        loginProcessingMutableLiveData.setValue(true);

        ApiService.getInstance.authUser(username, password, userModel -> {

            loginProcessingMutableLiveData.setValue(false);
            userMutableLiveData.setValue(userModel);

            if (onProcessFinish != null)onProcessFinish.run();
        });
    }

    public void doRefreshToken(Runnable callback) {
        UserModel temp = userMutableLiveData.getValue();
        if (temp == null) return;
        Log.e("isShowProgress", UserViewModel.class.getSimpleName() + " doRefreshToken");
        doLogin(temp.username, temp.password, callback);

        temp = null;
    }

    @SuppressLint("Recycle")
    public UserModel readSavedData(Context context) {
        try {

            Cursor cursor = context.getContentResolver().query(AppProvider.ConstantsProvider.URI_USER_TABLE, null, "", null, null);
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    return new UserModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
                }
            }
        } catch (Exception e) {
            Log.e(MainActivity.class.getName(), "Read Error: " + e.getMessage());
        }

        return null;
    }
}
