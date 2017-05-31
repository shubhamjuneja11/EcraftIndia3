package com.example.shubham11.ecraftindia;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.shubham11.ecraftindia.app.AppConfig;
import com.example.shubham11.ecraftindia.app.AppController;
import com.example.shubham11.ecraftindia.helper.SQLiteHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProductDetailsActivity extends AppCompatActivity {
TextView t_sku,t_msku,t_primarycategory,t_category,t_cp,t_mrp,t_sp,t_material,t_color,t_size,t_inventory,t_inventorytype;
    String username,imei,sku;
    SQLiteHandler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        sku=getIntent().getStringExtra("sku");
        load_data();
        initializeTextFields();
        handler=new SQLiteHandler(this);
        username=handler.getUserDetails().get("username");
        imei=handler.getUserDetails().get("id");


    }

    public void initializeTextFields(){
        t_sku=(TextView)findViewById(R.id.sku);
        t_msku=(TextView)findViewById(R.id.msku);
        t_primarycategory=(TextView)findViewById(R.id.primarycategory);
        t_category=(TextView)findViewById(R.id.category);
        t_cp=(TextView)findViewById(R.id.cp);
        t_mrp=(TextView)findViewById(R.id.mrp);
        t_sp=(TextView)findViewById(R.id.sp);
        t_material=(TextView)findViewById(R.id.material);
        t_color=(TextView)findViewById(R.id.color);
        t_size=(TextView)findViewById(R.id.size);
        t_inventory=(TextView)findViewById(R.id.inventory);
        t_inventorytype=(TextView)findViewById(R.id.inventorytype);
    }

    public void load_data(){
        StringRequest request=new StringRequest(StringRequest.Method.POST, AppConfig.URL_GET_PRODUCT_DETAIL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    //JSONObject data=object.getJSONArray("detail").getJSONObject(0);
                    Log.e("myres",response);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }

        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("username",username);
                params.put("uniqueid",imei);
                params.put("sku",sku);
                Log.e("respo",imei);
                Log.e("respo",username);
                Log.e("respo",sku);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(request,"details");
    }

}
