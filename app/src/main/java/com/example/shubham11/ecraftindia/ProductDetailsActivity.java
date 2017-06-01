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
TextView t_sku,t_msku,t_primarycategory,t_category,t_cp,t_mrp,t_sp,t_material,t_color,t_size,t_inventory,t_inventorytype,t_name;
    String msku,primarycategory,category,material,color,size,inventory,inventorytype,name;
    int cp,mrp,sp;
    String username,imei,sku;
    SQLiteHandler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        sku=getIntent().getStringExtra("sku");
        handler=new SQLiteHandler(this);
        username=handler.getUserDetails().get("username");
        imei=handler.getUserDetails().get("id");
        load_data();
        initializeTextFields();

    }

    public void initializeTextFields(){
        t_sku=(TextView)findViewById(R.id.sku);
        t_msku=(TextView)findViewById(R.id.msku);
        t_name=(TextView)findViewById(R.id.name);
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
                    JSONArray array=object.getJSONArray("detail");
                    msku=array.getString(1);
                    name=array.getString(3);
                    primarycategory=array.getString(4);
                    category=array.getString(5);
                    try {
                        cp = array.getInt(6);
                    }
                    catch (Exception e){
                     cp=0;
                    }
                    try {
                        mrp = array.getInt(7);
                    }
                    catch (Exception e){
                        mrp=0;
                    }
                    try {
                        sp = array.getInt(8);
                    }
                    catch (Exception e){
                        sp=0;
                    }
                    material=array.getString(9);
                    color=array.getString(10);
                    size=array.getString(11);
                    inventory=array.getString(12);
                    inventorytype=array.getString(13);
                    Log.e("resp",response);

                } catch (JSONException e) {
                    Log.e("respo",e.getMessage());
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
