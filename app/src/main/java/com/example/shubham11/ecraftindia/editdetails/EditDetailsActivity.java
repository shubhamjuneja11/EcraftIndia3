package com.example.shubham11.ecraftindia.editdetails;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.example.shubham11.ecraftindia.ProductDetailsActivity;
import com.example.shubham11.ecraftindia.R;
import com.example.shubham11.ecraftindia.models.ProductDetailsModel;

public class EditDetailsActivity extends AppCompatActivity {

    EditText editTexts[];
    String text[];
    TextView textView[];
    int textresourcid[]={R.id.commentby};
    ProductDetailsModel model;
    String originalsku,originalcomment;
    int resourceIds[]={R.id.sku,R.id.msku,R.id.name,R.id.primarycategory,R.id.category,R.id.cp,R.id.mrp,R.id.sp,R.id.material,
            R.id.color,R.id.size,R.id.inventory,R.id.inventorytype,R.id.comment};

    @Override
    protected void onResume() {
        super.onResume();
        setData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details);
        model=(ProductDetailsModel) getIntent().getSerializableExtra("model");
        initializeTextFields();

        EditUtility.setContext(this);

    }
    public void initializeTextFields(){
        text=new String[14];
        editTexts=new EditText[resourceIds.length];
        textView=new TextView[textresourcid.length];
        for(int i=0;i<resourceIds.length;i++) {
            editTexts[i] = (EditText) findViewById(resourceIds[i]);

        }
        for(int i=0;i<textView.length;i++)
        textView[i]=(TextView)findViewById(textresourcid[i]);

    }
    public void senddata(View view){
       for(int i=0;i<resourceIds.length;i++)
           text[i]=editTexts[i].getText().toString();
       // if(EditUtility.validatedata(text))
            EditUtility.sendadmindata(text,originalsku,originalcomment,editTexts[13].getText().toString());

    }
    public void setData(){
        originalsku=model.getSku();
        originalcomment=model.getComment();
        editTexts[0].setText(model.getSku());
        editTexts[1].setText(model.getMsku());
        editTexts[2].setText(model.getName());
        editTexts[3].setText(model.getPrimary_category());
        editTexts[4].setText(model.getCategory());
        editTexts[5].setText(model.getCp());
        editTexts[6].setText(model.getMrp());
        editTexts[7].setText(model.getSp());
        editTexts[8].setText(model.getMaterial());
        editTexts[9].setText(model.getColor());
        editTexts[10].setText(model.getSize());
        editTexts[11].setText(model.getInventory());
        editTexts[12].setText(model.getInventory_type());
        editTexts[13].setText(model.getComment());
        textView[0].setText(model.getCommentBy());
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this, ProductDetailsActivity.class);
        intent.putExtra("sku",editTexts[0].getText().toString());
        startActivity(intent);
        finish();
    }
}
