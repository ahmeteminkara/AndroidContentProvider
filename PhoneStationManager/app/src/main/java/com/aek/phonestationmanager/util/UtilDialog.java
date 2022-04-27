package com.aek.phonestationmanager.util;

import android.app.ProgressDialog;
import android.content.Context;

public class UtilDialog {

    public static ProgressDialog showProgressDialog(Context context, String message) {
        return showProgressDialog(context, null, message);
    }

    public static ProgressDialog showProgressDialog(Context context, String title, String message) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        if (title != null) progressDialog.setTitle(title);
        progressDialog.setCancelable(false);
        progressDialog.show();
        return progressDialog;
    }

}
