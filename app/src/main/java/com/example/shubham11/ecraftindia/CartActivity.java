package com.example.shubham11.ecraftindia;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.annotation.AnyRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.example.shubham11.ecraftindia.util.IntentShareHelper;
import com.example.shubham11.ecraftindia.util.UtilityFile;

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
    ProgressBar progress;
    StringRequest request;
    String changedquantity,URL,quantity;
    int selected_item;
    String operation,images;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        email= SessionManager.username ;
        unique_id=SessionManager.userid;
        al=new ArrayList<>();
        adapter=new CartAdapter(this,al,this);
        recyclerView=(RecyclerView)findViewById(R.id.recycler);
        progress=(ProgressBar)findViewById(R.id.progress_bar);
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
    public void shareProduct(CartModel model, ImageView iv,String sku,String comment) {
        Uri uri= UtilityFile.getLocalBitmapUri(this,iv);
        String text="#"+sku+"\n"+comment;
        IntentShareHelper.shareOnWhatsapp(this,text,uri);

    }

    @Override
    public void removeProduct(String sku) {
        showDeleteDialog(sku);
   /* progress.setVisibility(View.VISIBLE);
        URL=AppConfig.URL_ADD_TO_CART;
        this.sku=sku;
        getStringRequest(2);*/
    }
    public void showDeleteDialog(final String sku){
        AlertDialog.Builder alert = new AlertDialog.Builder(
                this);
        alert.setTitle("Delete");
        alert.setMessage("Are you sure to delete item from cart");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                URL=AppConfig.URL_ADD_TO_CART;
                CartActivity.this.sku=sku;
                getStringRequest(2);
                dialog.dismiss();

            }
        });
        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        alert.show();
    }

    @Override
    public void changeQuantity(int position) {
        URL=AppConfig.URL_ADD_TO_CART;
        showChangeLangDialog();
    }

    public void showChangeLangDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.edit1);

        dialogBuilder.setTitle("Enter quantity/comment");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                changedquantity=edt.getText().toString().trim();
                getStringRequest(3);
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
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
           AppController.getInstance().addToRequestQueue(request);
        }

        public void getCartResponse(String response){
            try {
                JSONObject jsonObject=new JSONObject(response);
                int success=jsonObject.getInt("success");
                if(success==1){
                    JSONArray array=jsonObject.getJSONArray("products");
                    for(int i=0;i<array.length();i++){
                        JSONObject ob=array.getJSONObject(i);
                        sku=ob.getString("sku");
                        quantity=ob.getString("quantity");
                        images=ob.getString("images");
                        al.add(new CartModel("abc",sku,quantity,images));
                    }
                    adapter.notifyDataSetChanged();
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
                    al.get(selected_item).setQuantity(changedquantity);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(this, "Error occured", Toast.LENGTH_SHORT).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    public static final Uri getUriToDrawable(@NonNull Context context,
                                             @AnyRes int drawableId) {
        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + context.getResources().getResourcePackageName(drawableId)
                + '/' + context.getResources().getResourceTypeName(drawableId)
                + '/' + context.getResources().getResourceEntryName(drawableId) );
        return imageUri;
    }
}
