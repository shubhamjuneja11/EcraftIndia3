package com.example.shubham11.ecraftindia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.shubham11.ecraftindia.adapters.CartAdapter;
import com.example.shubham11.ecraftindia.app.AppConfig;
import com.example.shubham11.ecraftindia.app.AppController;
import com.example.shubham11.ecraftindia.app.SessionManager;
import com.example.shubham11.ecraftindia.models.CartModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartActivity extends AppCompatActivity {
RecyclerView recyclerView;
    CartAdapter adapter;
    LinearLayoutManager layoutmanager;
    ArrayList<CartModel> al;
    String email,unique_id,sku;
    int quantity,price;
    TextView totalquantity_tv,totalprice_tv;
    int totalquantity=0,totalprice=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        email= SessionManager.username ;
        unique_id=SessionManager.userid;
        al=new ArrayList<>();
        adapter=new CartAdapter(this,al);
        recyclerView=(RecyclerView)findViewById(R.id.recycler);
        totalquantity_tv=(TextView)findViewById(R.id.totalquantity);
        totalprice_tv=(TextView)findViewById(R.id.totalprice);
        if(totalquantity_tv==null)
            Toast.makeText(this, "quantity", Toast.LENGTH_SHORT).show();
        if(totalprice_tv==null)
            Toast.makeText(this, "price", Toast.LENGTH_SHORT).show();
        recyclerView.setAdapter(adapter);
        layoutmanager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutmanager);
        getCartData();
    }
    public void getCartData(){
        StringRequest request=new StringRequest(StringRequest.Method.POST, AppConfig.URL_GET_CART_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("respo",response);
                    JSONObject jsonObject=new JSONObject(response);
                    int success=jsonObject.getInt("success");
                    if(success==1){
                        JSONArray array=jsonObject.getJSONArray("products");
                       for(int i=0;i<array.length();i++){
                           JSONObject ob=array.getJSONObject(i);
                           sku=ob.getString("sku");
                           quantity=ob.getInt("quantity");
                           price=0;
                           al.add(new CartModel("abc",sku,23,quantity));
                           totalquantity+=quantity;
                           totalprice+=price;
                       }
                       adapter.notifyDataSetChanged();
                        if(totalquantity_tv!=null)
                        totalquantity_tv.setText(totalquantity+"");
                        if (totalprice_tv!=null)
                        totalprice_tv.setText(totalprice+"");
                    }

                    else Toast.makeText(CartActivity.this, "Error occured", Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", email);
                params.put("uniqueid", unique_id);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(request);
    }
}
