package com.example.shubham11.ecraftindia.app;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by shubham11 on 24/5/17.
 */

public class SessionManager {
    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    String pref_name="ecraftindia",log_status="Log_status";
    private static String TAG=SessionManager.class.getSimpleName();

    public SessionManager(Context context){
        this.context=context;
        sharedPreferences=context.getSharedPreferences(pref_name,0);
        editor=sharedPreferences.edit();

    }

    public void setLogin(boolean f){
        editor.putBoolean(log_status,f);
        editor.apply();
    }
    public boolean isLoggedin(){
        boolean f=sharedPreferences.getBoolean(log_status,false);
        return f;
    }
}
