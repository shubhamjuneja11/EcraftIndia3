package com.example.shubham11.ecraftindia;

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
import com.example.shubham11.ecraftindia.helper.SQLiteHandler;
import com.example.shubham11.ecraftindia.models.ProductModel;
import com.example.shubham11.ecraftindia.models.SearchListModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    LinearLayout search;
    RecyclerView recyclerView;
    MainactivityAdapter adapter;
    ArrayList<SearchListModel> al;
    String username;
    int count=0;
    SQLiteHandler handler;
    String uniqueid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        handler=new SQLiteHandler(this);
        username=handler.getUserDetails().get("username");
        al=new ArrayList<>();
        recyclerView=(RecyclerView)findViewById(R.id.recycler);
        adapter=new MainactivityAdapter(this,al);
        uniqueid= Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        recyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new Decoration(this, LinearLayoutManager.HORIZONTAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        loaddata();
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        search = (LinearLayout) findViewById(R.id.searchtoolbar);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchlistActivity.class);
                startActivity(intent);
            }
        });
    }
    public void loaddata(){
        StringRequest request=new StringRequest(StringRequest.Method.POST, AppConfig.URL_GET_ALL_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

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
                params.put("count",String.valueOf(count));

                return params;
            }

        };
    }
}
