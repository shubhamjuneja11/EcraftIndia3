package com.example.shubham11.ecraftindia.editdetails;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.shubham11.ecraftindia.app.AppConfig;
import com.example.shubham11.ecraftindia.app.AppController;
import com.example.shubham11.ecraftindia.app.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shubhamjuneja11 on 15/6/17.
 */

public class EditUtility {
    static Context context;
    static Map<String, String> params;
    static String commentstatus="0";
    public static boolean validatedata(String[] data){
        for(int i=0;i<data.length;i++)
        {
            if(data[i].isEmpty())
                return false;

        }
        return true;
    }
    public static void setContext(Context context){
        EditUtility.context=context;
    }
    public static void sendAlldata(){
        Log.e("hello","b");
        final ProgressDialog dialog=new ProgressDialog(context);
        dialog.setMessage("Submitting...");
        dialog.show();

        StringRequest request=new StringRequest(StringRequest.Method.POST, AppConfig.URL_EDIT_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("hello",response);
                    JSONObject object=new JSONObject(response);
                    if(object.getInt("result")==1)
                        Toast.makeText(context, "Successfully submitted", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(context, "Error occured", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Log.e("hello",e.getMessage());
                    e.printStackTrace();
                    Toast.makeText(context, "Error occured.", Toast.LENGTH_SHORT).show();
                }
            finally {
                    dialog.dismiss();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("hello","ggg");
            }
        }){
            @Override
            protected Map<String, String> getParams() {

                // Posting parameters to login url
                params.put("username",SessionManager.username);
                Log.e("myuser",SessionManager.username);
                params.put("commentstatus",commentstatus);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(request);
    }
    public static void sendmanagerdata(String [] data){
        params.put("size",data[0]);
        params.put("inventory",data[1]);
        params.put("inventory_type",data[2]);
        params.put("comment",data[3]);

        sendAlldata();
    }
    public static void sendadmindata(String[] data,String originalsku,String originalcomment,String mycomment){
        Log.e("hello","c");

        params = new HashMap<String, String>();
        params.put("sku",data[0]);
        params.put("msku",data[1]);
        params.put("name",data[2]);
        params.put("primary_category",data[3]);
        params.put("category",data[4]);
        params.put("cp",data[5]);
        params.put("mrp",data[6]);
        params.put("sp",data[7]);
        params.put("material",data[8]);
        params.put("color",data[9]);
        params.put("size",data[10]);
        params.put("inventory",data[11]);
        params.put("inventory_type",data[12]);
        params.put("comment",data[13]);
        params.put("originalsku",originalsku);
        getCommentStatus(originalcomment,mycomment);
        Log.e("hello","d");
        sendAlldata();
        Log.e("hello","e");

    }
    public static void sendownerdata(String[] data){
        params = new HashMap<String, String>();
        params.put("name",data[0]);
        params.put("primary_category",data[1]);
        params.put("cp",data[2]);
        params.put("mrp",data[3]);
        params.put("sp",data[4]);
        params.put("material",data[5]);
        params.put("color",data[6]);
        params.put("size",data[7]);
        params.put("inventory",data[8]);
        params.put("inventory_type",data[9]);
        params.put("comment",data[10]);
        sendAlldata();
    }
    public static void getCommentStatus(String modelcomment,String mycomment ){
        if(!modelcomment.equals(mycomment))
            commentstatus="1";
        else commentstatus="0";
    }

}
