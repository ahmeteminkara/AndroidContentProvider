package com.aek.phonestationclient.viewmodel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aek.phonestationclient.constant.ConstantsProvider;
import com.aek.phonestationclient.constant.ConstantsUser;
import com.aek.phonestationclient.model.ModelUser;
import com.aek.phonestationclient.view.MainActivity;

@SuppressLint("StaticFieldLeak")
public class ViewModelUser extends ViewModel {
    private final Context context;

    private ViewModelUser(Context context) {
        this.context = context;
    }

    private static ViewModelUser instance;

    public static ViewModelUser getInstance(Context context) {
        if (instance == null) instance = new ViewModelUser(context);
        return instance;
    }

    public MutableLiveData<ModelUser> liveDataUser = new MutableLiveData<>();

    @SuppressLint("Recycle")
    public void checkUser() {
        try {

            Cursor cursor = context.getContentResolver().query(ConstantsProvider.URI_USER_TABLE, null, "", null, null);
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    liveDataUser.setValue(new ModelUser(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
                    return;
                }
            }
        } catch (Exception e) {
            Log.e(MainActivity.class.getName(), "Read Error: " + e);
        }
        liveDataUser.setValue(null);
    }

    public void logout() {
        if (liveDataUser.getValue() != null) {
            int deletedRow = context.getContentResolver().delete(
                    ConstantsProvider.URI_USER_TABLE,
                    ConstantsUser.ID + " = " + liveDataUser.getValue().id,
                    null);

            if (deletedRow == 0) {
                Toast.makeText(context, "Exit process failed", Toast.LENGTH_SHORT).show();
            }else{
                liveDataUser.setValue(null);
            }
        }
    }

}
