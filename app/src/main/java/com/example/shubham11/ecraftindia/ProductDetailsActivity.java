package com.example.shubham11.ecraftindia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ProductDetailsActivity extends AppCompatActivity {
TextView t_sku,t_msku,t_primarycategory,t_category,t_cp,t_mrp,t_sp,t_material,t_color,t_size,t_inventory,t_inventorytype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        initializeTextFields();
    }

    public void initializeTextFields(){
        t_sku=(TextView)findViewById(R.id.sku);
        t_msku=(TextView)findViewById(R.id.msku);
        t_primarycategory=(TextView)findViewById(R.id.primarycategory);
        t_category=(TextView)findViewById(R.id.category);
        t_cp=(TextView)findViewById(R.id.cp);
        t_mrp=(TextView)findViewById(R.id.mrp);
        t_sp=(TextView)findViewById(R.id.sp);
        t_material=(TextView)findViewById(R.id.material);
        t_color=(TextView)findViewById(R.id.color);
        t_size=(TextView)findViewById(R.id.size);
        t_inventory=(TextView)findViewById(R.id.inventory);
        t_inventorytype=(TextView)findViewById(R.id.inventorytype);
    }
}
