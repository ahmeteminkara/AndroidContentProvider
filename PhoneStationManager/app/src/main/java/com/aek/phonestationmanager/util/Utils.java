package com.aek.phonestationmanager.util;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import com.aek.phonestationmanager.view.MainActivity;

import java.util.UUID;

public class Utils {

    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    public static void resetLauncherAndOpenChooser(Context context) {
        PackageManager packageManager = context.getPackageManager();
        ComponentName componentName = new ComponentName(context, MainActivity.class);
        packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

        Intent selector = new Intent(Intent.ACTION_MAIN);
        selector.addCategory(Intent.CATEGORY_HOME);
        selector.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(selector);
        packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT, PackageManager.DONT_KILL_APP);
    }


    @SuppressLint("QueryPermissionsNeeded")
    public static void openAnotherApp(Context context, String packageName) {

        try {

            Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
            if (intent == null) return;
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
            } else {
                Toast.makeText(context, "Admin app not installed", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e(MainActivity.class.getName(), "Redirect App Error: " + e.toString());

        }
    }
}
