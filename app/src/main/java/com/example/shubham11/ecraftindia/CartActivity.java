package com.example.shubham11.ecraftindia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.shubham11.ecraftindia.adapters.CartAdapter;
import com.example.shubham11.ecraftindia.app.AppConfig;
import com.example.shubham11.ecraftindia.app.SessionManager;
import com.example.shubham11.ecraftindia.models.CartModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartActivity extends AppCompatActivity {
RecyclerView recyclerView;
    CartAdapter adapter;
    LinearLayoutManager layoutmanager;
    ArrayList<CartModel> al;
    String email,unique_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        email= SessionManager.username ;
        unique_id=SessionManager.userid;
        al=new ArrayList<>();
        adapter=new CartAdapter(this,al);
        recyclerView=(RecyclerView)findViewById(R.id.recycler);
        recyclerView.setAdapter(adapter);
        layoutmanager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutmanager);
        for(int i=0;i<3;i++)
            al.add(new CartModel("gf","ere",3,5));
    }
    public void getCartData(){
        StringRequest request=new StringRequest(StringRequest.Method.POST, AppConfig.URL_GET_CART_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

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
                params.put("unique_id", unique_id);
                return super.getParams();
            }
        };
    }
}
