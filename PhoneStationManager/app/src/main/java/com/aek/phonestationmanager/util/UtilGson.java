package com.aek.phonestationmanager.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class UtilGson {

    private final static Gson gson;

    static {
        GsonBuilder builder = new GsonBuilder();
        gson = builder.create();
    }

    public static String gsonEncode(Object object) {
        return gson.toJson(object);
    }

    public static <T> T gsonEncode( String jsonStr,Class<T> type) {
        return gson.fromJson(jsonStr, type);
    }
}
