package com.aek.phonestationclient.constant;

import android.net.Uri;

public class ConstantsProvider {
    public static final String PROVIDER_NAME = "com.aek.phonestationmanager.core.provider.AppProvider";
    public static final String PATH = "user";
    public static final String URL = "content://" + PROVIDER_NAME + "/" + PATH;
    public static final Uri URI_USER_TABLE = Uri.parse(URL);

}
