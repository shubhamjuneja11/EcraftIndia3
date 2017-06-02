package com.example.shubham11.ecraftindia;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.shubham11.ecraftindia.adapters.SearchListAdapter;
import com.example.shubham11.ecraftindia.app.AppConfig;
import com.example.shubham11.ecraftindia.app.AppController;
import com.example.shubham11.ecraftindia.models.SearchListModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchlistActivity extends AppCompatActivity implements RecyclerViewClickListener{
RecyclerView recyclerView;

    EditText searchedit;
    ImageView clearall;
    String query;
    SearchListAdapter adapter;
    ArrayList<SearchListModel> al;
    LinearLayoutManager layoutmanager;
    int visibleItemCount,totalItemCount,pastVisiblesItems;
    boolean loading=true;
    int count=0;
    private String imageurl,name,sku;
    private int sp;
    ProgressBar dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchlist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });
        dialog=(ProgressBar)findViewById(R.id.progressbar);


        al=new ArrayList<>();
        adapter=new SearchListAdapter(this,al,this);
       recyclerView=(RecyclerView)findViewById(R.id.recycler);
        recyclerView.setAdapter(adapter);
        layoutmanager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutmanager);

        View.OnFocusChangeListener ofcListener = new MyFocusChangeListener();

        searchedit=(EditText)findViewById(R.id.searchtext);
        searchedit.setOnFocusChangeListener(ofcListener);
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
                al.clear();
               adapter.notifyDataSetChanged();
           }

           @Override
           public void afterTextChanged(Editable s) {
               count=0;
                query=s.toString();
               if(query.length()>2)
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

                if(dy > 0) //check for scroll down
                {
                    visibleItemCount = layoutmanager.getChildCount();
                    totalItemCount = layoutmanager.getItemCount();
                    pastVisiblesItems = layoutmanager.findFirstVisibleItemPosition();

                    if (loading)
                    {
                        if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount)
                        {
                            dialog.setVisibility(View.VISIBLE);
                            loading = false;
                            loaddata();
                        }
                    }
                }
            }
        });
    }
    public void loaddata(){
       count++;
        hitdatabase(query);
    }
    public void hitdatabase(final String query){

        StringRequest request=new StringRequest(Request.Method.POST, AppConfig.URL_GET_SEARCH_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("response",response);
                    JSONObject object=new JSONObject(response);
                    JSONArray array1=object.getJSONArray("products");
                    for(int i=0;i<array1.length();i++){
                       JSONObject object1=array1.getJSONObject(i);
                        name=object1.getString("name");
                        sku=object1.getString("sku");
                       sp=0;
                        imageurl=object1.getString("images");
                        al.add(new SearchListModel(imageurl,name,sku,sp,sp));

                    } adapter.notifyDataSetChanged();

                    dialog.setVisibility(View.INVISIBLE);
                    loading=true;

                } catch (JSONException e) {
                    Log.e("response",e.getMessage());
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
                params.put("query",query.trim());
                params.put("count",String.valueOf(count));

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(request,"get_data");
    }


    @Override
    public void recycleritemClicked(View v, int position) {
        String sku=al.get(position).getSku();
        Toast.makeText(this,sku, Toast.LENGTH_SHORT).show();
    }
    private class MyFocusChangeListener implements View.OnFocusChangeListener {

        public void onFocusChange(View v, boolean hasFocus){

            if(v.getId() == R.id.searchtext && !hasFocus) {

                InputMethodManager imm =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            }
        }
    }
}
