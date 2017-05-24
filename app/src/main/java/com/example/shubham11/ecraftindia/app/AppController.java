package com.example.shubham11.ecraftindia.app;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by shubham11 on 24/5/17.
 */

public class AppController extends Application {

    private RequestQueue requestQueue;
    public static AppController instance;
    private static String TAG=AppController.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
    }

    public static synchronized AppController getInstance(){
        return instance;
    }
    public RequestQueue getRequestQueue(){
        if(requestQueue==null){
            requestQueue= Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }
    public <T> void addToRequestQueue(Request<T> req){
        req.setTag(TAG);
        getRequestQueue().add(req);
    }
    public <T> void addToRequestQueue(Request<T> req,String tag){
        req.setTag(TextUtils.isEmpty(tag)?TAG:tag);
        getRequestQueue().add(req);
    }
    public void cancelPendingReQuest(Object tag){
        if(requestQueue!=null)
                requestQueue.cancelAll(tag);
    }



}
