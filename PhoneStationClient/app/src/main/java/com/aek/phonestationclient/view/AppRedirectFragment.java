package com.aek.phonestationclient.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.aek.phonestationclient.R;
import com.aek.phonestationclient.databinding.FragmentAppRedirectBinding;

public class AppRedirectFragment extends Fragment {

    FragmentAppRedirectBinding binding;

    public AppRedirectFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_app_redirect, container, false);
        binding.btnRedirectApp.setOnClickListener(view -> redirectApp(getContext()));

        return binding.getRoot();
    }

    @SuppressLint("QueryPermissionsNeeded")
    private void redirectApp(Context context) {
        try {

            Intent intent = context.getPackageManager().getLaunchIntentForPackage("com.aek.phonestationmanager");
            if (intent == null) return;
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(context, "Admin app not installed", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e(MainActivity.class.getName(), "Redirect App Error: " + e.toString());
        }
    }
}