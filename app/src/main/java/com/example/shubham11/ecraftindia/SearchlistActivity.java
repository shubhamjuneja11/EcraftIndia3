package com.example.shubham11.ecraftindia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.shubham11.ecraftindia.app.AppConfig;
import com.example.shubham11.ecraftindia.models.SearchListModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchlistActivity extends AppCompatActivity {
RecyclerView recyclerView;

    EditText searchedit;
    ImageView clearall;
    String query;
    SearchListAdapter adapter;
    ArrayList<SearchListModel> al;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchlist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);


        al=new ArrayList<>();
        adapter=new SearchListAdapter(this,al);
       recyclerView=(RecyclerView)findViewById(R.id.recycler);
        recyclerView.setAdapter(adapter);



        searchedit=(EditText)findViewById(R.id.searchtext);
        clearall=(ImageView)findViewById(R.id.clearall);




        clearall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchedit.setText("");
            }
        });
       searchedit.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {

           }

           @Override
           public void afterTextChanged(Editable s) {
                query=s.toString();
                hitdatabase(query);
           }
       });


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });
    }
    public void hitdatabase(final String query){
        StringRequest request=new StringRequest(Request.Method.POST, AppConfig.URL_GET_SEARCH_DATA, new Response.Listener<String>() {
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
                params.put("query",query);

                return params;
            }
        };

    }


}
