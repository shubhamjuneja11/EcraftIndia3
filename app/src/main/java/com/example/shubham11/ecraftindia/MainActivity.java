package com.example.shubham11.ecraftindia;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.shubham11.ecraftindia.models.ProductModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    LinearLayout search;
    RecyclerView recyclerView;
    MainactivityAdapter adapter;
    ArrayList<ProductModel> al;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        al=new ArrayList<>();
        recyclerView=(RecyclerView)findViewById(R.id.recycler);
        adapter=new MainactivityAdapter(this,al);

        recyclerView.setAdapter(adapter);
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

        };
    }
}
