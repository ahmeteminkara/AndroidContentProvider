package com.aek.phonestationmanager.view;

import android.content.ContentValues;
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
import com.aek.phonestationmanager.databinding.FragmentProfileBinding;
import com.aek.phonestationmanager.model.UserModel;
import com.aek.phonestationmanager.util.Utils;
import com.aek.phonestationmanager.viewmodel.UserViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ProfileFragment extends Fragment implements View.OnClickListener {
    private FragmentProfileBinding binding;

    private final UserModel userModel;

    public ProfileFragment(UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        Log.e(ProfileFragment.class.getSimpleName(), "onDestroy()");
        super.onDestroy();
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //final View view = inflater.inflate(R.layout.fragment_login, container, false);
        //.inflate(R.layout.fragment_profile, container, false)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        binding.setLifecycleOwner(getActivity());


        binding.btnRefreshToken.setOnClickListener(this);
        binding.btnOpenClientApp.setOnClickListener(this);
        binding.setData(userModel);

        return binding.getRoot();
    }

    @Override
    public void onClick(View view) {
        if (getContext() == null) return;


        if (view == binding.btnOpenClientApp) {
            Utils.openAnotherApp(getContext(), "com.aek.phonestationclient");
        } else if (view == binding.btnRefreshToken) {
            UserViewModel.getInstance().doRefreshToken(() -> {
                if (getContext() == null) return;
                ContentValues contentValues = new ContentValues();
                contentValues.put(AppProvider.ConstantsUser.TOKEN, Objects.requireNonNull(UserViewModel.getInstance().userMutableLiveData.getValue()).token);
                int updateRow = getContext().getContentResolver().update(AppProvider.ConstantsProvider.URI_USER_TABLE, contentValues, AppProvider.ConstantsUser.ID + " = " + userModel.id, null);
                if (updateRow == 0) {
                    Toast.makeText(getContext(), "Update process failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}