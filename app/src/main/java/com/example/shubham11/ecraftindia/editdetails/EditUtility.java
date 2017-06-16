package com.example.shubham11.ecraftindia.editdetails;

import android.app.ProgressDialog;
import android.content.Context;
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
    public static void sendAlldata(final String[] data){
        final ProgressDialog dialog=new ProgressDialog(context);
        dialog.show();
        dialog.setMessage("Submitting...");
        StringRequest request=new StringRequest(StringRequest.Method.POST, AppConfig.URL_EDIT_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    if(object.getInt("result")==1)
                        Toast.makeText(context, "Successfully submitted", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(context, "Error occured", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
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

            }
        }){
            @Override
            protected Map<String, String> getParams() {

                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("sku",data[0]);
                params.put("msku",data[1]);
                params.put("name",data[2]);
                params.put("primary_category",data[3]);
                params.put("cp",data[4]);
                params.put("mrp",data[5]);
                params.put("sp",data[6]);
                params.put("material",data[7]);
                params.put("color",data[8]);
                params.put("size",data[9]);
                params.put("inventory",data[10]);
                params.put("inventory_type",data[11]);
                params.put("comment",data[12]+"\n@"+SessionManager.username);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(request);
    }

}
