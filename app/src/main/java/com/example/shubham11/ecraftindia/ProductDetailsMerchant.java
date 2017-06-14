package com.example.shubham11.ecraftindia;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.shubham11.ecraftindia.app.AppConfig;
import com.example.shubham11.ecraftindia.app.AppController;
import com.example.shubham11.ecraftindia.app.SessionManager;
import com.example.shubham11.ecraftindia.carousel.ViewPagerCarouselView;
import com.example.shubham11.ecraftindia.helper.SQLiteHandler;
import com.example.shubham11.ecraftindia.util.UtilityFile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProductDetailsMerchant extends AppCompatActivity {
    TextView t_sku,t_msku,t_primarycategory,t_category,t_cp,t_mrp,t_sp,t_material,t_color,t_size,t_inventory,t_inventorytype,t_name;
    String msku,primarycategory,category,material,color,size,inventory,inventorytype,name;
    int cp,mrp,sp;
    String sku,username,imei,access,images;
    String cpr,mrpr,spr,rupee;
    ViewPagerCarouselView viewPagerCarouselView;
    SQLiteHandler handler;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler=new SQLiteHandler(this);
        setContentView(R.layout.activity_product_details_merchant);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        sku=getIntent().getStringExtra("sku");
        initializeTextFields();
        username=SessionManager.username;
        imei=SessionManager.userid;
        rupee=getString(R.string.Rs)+" ";
        load_data();
    }





    public void initializeTextFields(){
        t_sku=(TextView)findViewById(R.id.sku);
        t_msku=(TextView)findViewById(R.id.msku);
        t_name=(TextView)findViewById(R.id.name);
        t_primarycategory=(TextView)findViewById(R.id.primarycategory);
        t_category=(TextView)findViewById(R.id.category);
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
                    access=object.getString("editable_access");
                    msku=array.getString(1);
                    images=array.getString(2);

                    /**************************************/

                    String [] imageResourceIds = UtilityFile.getallImages(images);
                    viewPagerCarouselView = (ViewPagerCarouselView) findViewById(R.id.carousel_view);
                    viewPagerCarouselView.setData(getSupportFragmentManager(), imageResourceIds);

                    /*****************************************/
                    name=array.getString(3);

                } catch (JSONException e) {
                    Log.e("ab","duetome");
                    e.printStackTrace();
                }
                loadViews();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("helloz",error.toString());
            }
        }

        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("username",username);
                params.put("uniqueid",imei);
                params.put("sku",sku);
                Log.e("abcde",username);
                Log.e("abcde",imei);
                Log.e("abcde",sku);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(request,"details");
    }
    public void loadViews(){
        t_sku.setText(sku);
        t_msku.setText(msku);//t_primarycategory,t_category,t_cp,t_mrp,t_sp,t_material,t_color,t_size,t_inventory,t_inventorytype,t_name;
        t_name.setText(name);
    }


    public void addToCart(View view){
        StringRequest request=new StringRequest(StringRequest.Method.POST, AppConfig.URL_ADD_TO_CART, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    int success=object.getInt("success");
                    if(success==1){
                        Intent intent=new Intent(ProductDetailsMerchant.this,CartActivity.class);
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(ProductDetailsMerchant.this, "Some error occured", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Intent intent=new Intent(ProductDetailsMerchant.this,CartActivity.class);
                    startActivity(intent);
                    e.printStackTrace();
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
                params.put("username", username);
                params.put("uniqueid",imei);
                params.put("operation","add");
                params.put("sku",sku);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.detailsmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.cart)
            intent=new Intent(this,CartActivity.class);
        startActivity(intent);
        return true;
    }
    public void editProduct(View view){
        Intent intent=new Intent(this,EditDetailsActivity.class);
        intent.putExtra("access",access);
        startActivity(intent);

    }
}
