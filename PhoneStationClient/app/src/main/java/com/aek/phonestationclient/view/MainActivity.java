package com.aek.phonestationclient.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.aek.phonestationclient.R;
import com.aek.phonestationclient.model.ModelUser;
import com.aek.phonestationclient.viewmodel.ViewModelUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewModelUser.getInstance(this).liveDataUser.observe(this, observeUserModel);
        checkUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkUI();
    }

    private void checkUI() {
        ViewModelUser.getInstance(this).checkUser();
    }

    private final Observer<ModelUser> observeUserModel = modelUser -> {
        if (modelUser != null) {

            ProfileFragment profileFragment = new ProfileFragment(modelUser);
            MainActivity.this.getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.frameLayout, profileFragment, null)
                    .commit();
        } else {
            MainActivity.this.getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.frameLayout, AppRedirectFragment.class, null)
                    .commit();
        }

    };


}