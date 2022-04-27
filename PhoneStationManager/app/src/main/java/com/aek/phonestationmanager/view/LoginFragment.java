package com.aek.phonestationmanager.view;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.aek.phonestationmanager.R;
import com.aek.phonestationmanager.core.provider.AppProvider;
import com.aek.phonestationmanager.databinding.FragmentLoginBinding;
import com.aek.phonestationmanager.model.UserModel;
import com.aek.phonestationmanager.viewmodel.UserViewModel;

public class LoginFragment extends Fragment implements View.OnClickListener {

    FragmentLoginBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        Log.e(LoginFragment.class.getSimpleName(), "onDestroy()");
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        binding.btnLogin.setOnClickListener(this);

        return binding.getRoot();
    }

    public UserModel loginUser() {
        return UserViewModel.getInstance().userMutableLiveData.getValue();
    }

    @Override
    public void onClick(View view) {
        if (getContext() == null) return;
        if (view == binding.btnLogin) {
            String username = binding.editTextUsername.getText().toString();
            String password = binding.editTextPassword.getText().toString();
            if (username.isEmpty() || password.isEmpty()) {
                binding.editTextUsername.requestFocus();
                Toast.makeText(getContext(), "Username or password is null", Toast.LENGTH_SHORT).show();
                return;
            }

            UserViewModel.getInstance().doLogin(username, password, () -> {
                if (getContext() == null) return;

                if (loginUser() == null) {
                    Toast.makeText(getContext(), "Login info is null", Toast.LENGTH_SHORT).show();
                    return;
                }

                ContentValues userValues = new ContentValues();
                userValues.put(AppProvider.ConstantsUser.NAME, loginUser().username);
                userValues.put(AppProvider.ConstantsUser.PASS, loginUser().password);
                userValues.put(AppProvider.ConstantsUser.TOKEN, loginUser().token);

                Uri insertUri = getContext().getContentResolver().insert(AppProvider.ConstantsProvider.URI_USER_TABLE, userValues);
                if (insertUri == null)
                    Toast.makeText(getContext(), "Username or password is null", Toast.LENGTH_SHORT).show();

            });


        }
    }

}