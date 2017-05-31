package com.example.shubham11.ecraftindia;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.shubham11.ecraftindia.adapters.MainactivityAdapter;
import com.example.shubham11.ecraftindia.app.AppConfig;
import com.example.shubham11.ecraftindia.app.AppController;
import com.example.shubham11.ecraftindia.helper.SQLiteHandler;
import com.example.shubham11.ecraftindia.models.ProductModel;
import com.example.shubham11.ecraftindia.models.SearchListModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements RecyclerViewClickListener {

    LinearLayout search;
    RecyclerView recyclerView;
    MainactivityAdapter adapter;
    ArrayList<SearchListModel> al;
    String username;
    int count=0;
    SQLiteHandler handler;
    String uniqueid;
    private String imageurl,name,sku;
    private int sp;
    int visibleItemCount,totalItemCount,pastVisiblesItems;
    LinearLayoutManager layoutmanager;
    boolean loading=true;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        dialog=new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        handler=new SQLiteHandler(this);
        username=handler.getUserDetails().get("username");
        al=new ArrayList<>();
        recyclerView=(RecyclerView)findViewById(R.id.recycler);
        adapter=new MainactivityAdapter(this,al,this);
        uniqueid=handler.getUserDetails().get("id");
        recyclerView.setAdapter(adapter);
        layoutmanager=new LinearLayoutManager(this);
        final RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new Decoration(this, LinearLayoutManager.HORIZONTAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
/**************************Recycler Scroll Listener**************************************************/




        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                    layoutmanager=(LinearLayoutManager)mLayoutManager;
                if(dy > 0) //check for scroll down
                {
                    visibleItemCount = layoutmanager.getChildCount();
                    totalItemCount =layoutmanager.getItemCount();
                    pastVisiblesItems = layoutmanager.findFirstVisibleItemPosition();

                    if (loading)
                    {
                        if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount)
                        {
                            dialog.show();
                            loading = false;
                            loaddata();
                        }

                    }
                }
            }
        });

/****************************************************************************************************/



/**********************Recycler touch listenr********************************************************/









/**************************************************************************************************/

        loaddata();

        search = (LinearLayout) findViewById(R.id.searchtoolbar);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchlistActivity.class);
                startActivity(intent);
            }
        });
    }

    /*********************************Function to get data from server************************************/
    public void loaddata(){
        StringRequest request=new StringRequest(StringRequest.Method.POST, AppConfig.URL_GET_ALL_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    JSONArray array=object.getJSONArray("products");
                    for(int i=0;i<array.length();i++){
                        JSONObject object1=array.getJSONObject(i);
                        name=object1.getString("name");
                        sku=object1.getString("sku");
                        sp=0;
                        imageurl=object1.getString("images");
                        al.add(new SearchListModel(imageurl,name,sku,sp,sp));
                    }
                    adapter.notifyDataSetChanged();
                    dialog.hide();
                    loading=true;
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
            protected Map<String, String> getParams() {
                Log.e("myh","t");
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("username",username);
                params.put("uniqueid",uniqueid);
                params.put("count",String.valueOf(count));

                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(request,"get_data2");
    }

    @Override
    public void recycleritemClicked(View v, int position) {
        String sku=al.get(position).getSku();
        Intent intent=new Intent(this,ProductDetailsActivity.class);
        intent.putExtra("sku",sku);
        startActivity(intent);
    }
    /******************************************************************************************/



}
