package com.example.shubham11.ecraftindia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
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
import com.example.shubham11.ecraftindia.interfaces.CartEventListener;
import com.example.shubham11.ecraftindia.models.CartModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartActivity extends AppCompatActivity implements CartEventListener {
RecyclerView recyclerView;
    CartAdapter adapter;
    LinearLayoutManager layoutmanager;
    ArrayList<CartModel> al;
    String email,unique_id,sku;
    int quantity,price;
    TextView totalquantity_tv,totalprice_tv;
    int totalquantity=0,totalprice=0;
    ProgressBar progress;
    StringRequest request;
    String changedquantity,URL;
    int selected_item;
    String operation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        email= SessionManager.username ;
        unique_id=SessionManager.userid;
        al=new ArrayList<>();
        adapter=new CartAdapter(this,al,this);
        recyclerView=(RecyclerView)findViewById(R.id.recycler);
        totalquantity_tv=(TextView)findViewById(R.id.totalquantity);
        totalprice_tv=(TextView)findViewById(R.id.totalprice);
        progress=(ProgressBar)findViewById(R.id.progress_bar);
        if(totalquantity_tv==null)
            Toast.makeText(this, "quantity", Toast.LENGTH_SHORT).show();
        if(totalprice_tv==null)
            Toast.makeText(this, "price", Toast.LENGTH_SHORT).show();
        recyclerView.setAdapter(adapter);
        layoutmanager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutmanager);
        URL=AppConfig.URL_GET_CART_DATA;
        getCartData();
    }
    public void getCartData(){
        progress.setVisibility(View.VISIBLE);
        getStringRequest(1);
    }

    @Override
    public void viewProduct(String sku) {
        Intent intent=new Intent(this,ProductDetailsActivity.class);
        intent.putExtra("sku",sku);
        startActivity(intent);
    }

    @Override
    public void removeProduct(String sku) {
    progress.setVisibility(View.VISIBLE);
        URL=AppConfig.URL_ADD_TO_CART;
        this.sku=sku;
        getStringRequest(2);
    }

    @Override
    public void changeQuantity(String sku, int quantity) {
        progress.setVisibility(View.VISIBLE);
        URL=AppConfig.URL_ADD_TO_CART;
        changedquantity=quantity+"";
        this.sku=sku;
        getStringRequest(2);
    }

    @Override
    public void getSelecteditem(int position) {
        selected_item=position;
    }

    /******************     1 = getCartdata      2 = removeproduct     3 = changequantity     *********************/


        public void getStringRequest(final int whattodo){
         request=new StringRequest(StringRequest.Method.POST,URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                switch(whattodo){
                    case 1:
                        getCartResponse(response);
                        break;
                    case 2:
                        getRemoveProductResponse(response);
                        break;
                    case 3:
                        getChangeQuantityResponse(response);
                        break;
                }
                progress.setVisibility(View.INVISIBLE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("zomie",error.getMessage());
                progress.setVisibility(View.INVISIBLE);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
               Map<String, String> params = new HashMap<String, String>();
                params.put("username", email);
                params.put("uniqueid", unique_id);
               // params.put("sku",sku);
                switch (whattodo){
                    case 2:
                        operation="remove";
                        params.put("sku",sku);
                        break;
                    case 3:
                        operation="update";
                        params.put("sku",sku);
                        params.put("quantity",changedquantity);
                        break;
                }
                if(whattodo!=1)
                    params.put("operation",operation);

                return params;

            }
        };
            Log.e("zomie","d");
           AppController.getInstance().addToRequestQueue(request,"op");
            Log.e("zomie","e");
        }

        public void getCartResponse(String response){
            try { Log.e("zomie","b");
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
            finally {
                progress.setVisibility(View.INVISIBLE);
            }
        }


        public void  getRemoveProductResponse(String response){
            try {
                Log.e("repos",response);
                JSONObject object=new JSONObject(response);
                int res=object.getInt("success");
                if(res==1)
                    {
                        al.remove(selected_item);
                        adapter.notifyDataSetChanged();
                    }
                    
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        public void getChangeQuantityResponse(String response){
            try {
                JSONObject object=new JSONObject(response);
                int res=object.getInt("success");
                if(res==1)
                {
                    /*al.remove(selected_item);
                    adapter.notifyDataSetChanged();*/
                    Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
}
