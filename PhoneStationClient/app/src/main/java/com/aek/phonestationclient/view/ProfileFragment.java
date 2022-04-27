package com.aek.phonestationclient.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.aek.phonestationclient.R;
import com.aek.phonestationclient.databinding.FragmentProfileBinding;
import com.aek.phonestationclient.model.ModelUser;
import com.aek.phonestationclient.viewmodel.ViewModelUser;

public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;

    private final ModelUser modelUser;

    public ProfileFragment(ModelUser modelUser) {
        // Required empty public constructor
        this.modelUser = modelUser;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        binding.btnExit.setOnClickListener(view -> doLogout());
        binding.setUser(modelUser);
        return binding.getRoot();
    }

    private void doLogout() {
        ViewModelUser.getInstance(getContext()).logout();
    }
}