package com.aek.phonestationmanager.view;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.aek.phonestationmanager.R;
import com.aek.phonestationmanager.model.UserModel;
import com.aek.phonestationmanager.util.UtilDialog;
import com.aek.phonestationmanager.viewmodel.UserViewModel;

@SuppressLint("NonConstantResourceId")
public class MainActivity extends AppCompatActivity {

    FrameLayout mainFrameLayout;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainFrameLayout = findViewById(R.id.mainFrameLayout);

        UserViewModel.getInstance().userMutableLiveData.observe(this, userLiveData);
        UserViewModel.getInstance().loginProcessingMutableLiveData.observe(this, progressObserve);
        checkLocalUser();
    }

    private void showProgressDialog() {
        progressDialog = UtilDialog.showProgressDialog(this, "Loading, please wait...");
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkLocalUser();
    }


    private void checkLocalUser() {
        UserModel userModel = UserViewModel.getInstance().readSavedData(this);
        UserViewModel.getInstance().userMutableLiveData.setValue(userModel);
    }


    private final Observer<Boolean> progressObserve = isShowProgress -> {
        Log.e(MainActivity.class.getSimpleName(), "isShowProgress: " + isShowProgress);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (progressDialog != null) progressDialog.dismiss();
                if (isShowProgress)
                    showProgressDialog();
            }
        });
    };

    private final Observer<UserModel> userLiveData = userModel -> {
        if (userModel != null) {

            ProfileFragment profileFragment = new ProfileFragment(userModel);
            MainActivity.this.getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.mainFrameLayout, profileFragment, null)
                    .commit();
        } else {
            MainActivity.this.getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.mainFrameLayout, LoginFragment.class, null)
                    .commit();
        }


    };


}