package com.example.shubham11.ecraftindia;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.shubham11.ecraftindia.adapters.MainactivityAdapter;
import com.example.shubham11.ecraftindia.app.AppConfig;
import com.example.shubham11.ecraftindia.app.AppController;
import com.example.shubham11.ecraftindia.helper.SQLiteHandler;
import com.example.shubham11.ecraftindia.interfaces.RecyclerViewClickListener;
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
    ProgressBar dialog;
    String values[],searchalpha;
    boolean fromalpha=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fromalpha=false;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        dialog=(ProgressBar)findViewById(R.id.progressbar);
        dialog.setVisibility(View.INVISIBLE);
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
        //recyclerView.addItemDecoration(new Decoration(this, LinearLayoutManager.HORIZONTAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        values=new String[26];
        int k=0;
        for(char c='A';c<='Z';c++){
            values[k++]=c+"";
        }
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
        Log.e("hello",AppConfig.URL_GET_ALL_DATA);
        dialog.setVisibility(View.VISIBLE);
        StringRequest request=new StringRequest(StringRequest.Method.POST,AppConfig.URL_GET_ALL_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {Log.e("abcd",response);
                    JSONObject object=new JSONObject(response);
                    JSONArray array=object.getJSONArray("products");
                    for(int i=0;i<array.length();i++){
                        JSONObject object1=array.getJSONObject(i);
                        name=object1.getString("name");
                        sku=object1.getString("sku");

                        sp=object1.getInt("cp");
                        imageurl=object1.getString("images");
                        al.add(new SearchListModel(imageurl,name,sku,sp,sp));
                    }
                    adapter.notifyDataSetChanged();

                    loading=true;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                finally {
                    dialog.setVisibility(View.INVISIBLE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.setVisibility(View.INVISIBLE);
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
                if(fromalpha) {
                    params.put("alpha", searchalpha);
                }

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

    public void openProfile(View view){
        Intent intent=new Intent(this,ProfileActivity.class);
        startActivity(intent);
    }
    public void openCart(View view){
        Intent intent=new Intent(this,CartActivity.class);
        startActivity(intent);
    }
    public void refine(View view){
        final Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.customdialog);
        dialog.setTitle("Select");
        ListView listView=(ListView) dialog.findViewById(R.id.listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                searchalpha=values[position];
                fromalpha=true;
                count=0;
                loaddata();
                dialog.dismiss();
                al.clear();
                adapter.notifyDataSetChanged();
            }
        });
        try {
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, values);
            listView.setAdapter(adapter);

            dialog.show();
        }
        catch (Exception e){

        }
    }

}
