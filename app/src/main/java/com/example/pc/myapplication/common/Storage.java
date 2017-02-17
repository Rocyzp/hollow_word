package com.example.pc.myapplication.common;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by pc on 2016/12/19.
 */

public class Storage {
    private final static String STORAGE_FILE_NAME = "chatclient.config";

    public static void putString(Context context,String key,String value){
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        sharedPreferences.edit().putString(key,value).commit();
    }

    public static String getString(Context context,String key,String... value){
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        String dv = "";
        for(String v:value){
            dv = v;
            break;
        }
        return sharedPreferences.getString(key,dv);
    }

    public static void putBoolean(Context context,String key,boolean value){
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        sharedPreferences.edit().putBoolean(key,value).commit();
    }

    public static boolean getBoolean(Context context,String key,boolean... value){
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        boolean dv = false;
        for(boolean v:value){
            dv = v;
            break;
        }
        return sharedPreferences.getBoolean(key,dv);
    }

    private static SharedPreferences getSharedPreferences(Context context){
        return context.getSharedPreferences(STORAGE_FILE_NAME,Context.MODE_PRIVATE);
    }

}
