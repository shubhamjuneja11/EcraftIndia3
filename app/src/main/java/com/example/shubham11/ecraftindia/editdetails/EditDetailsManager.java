package com.example.shubham11.ecraftindia.editdetails;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.shubham11.ecraftindia.R;
import com.example.shubham11.ecraftindia.models.ProductDetailsModel;

public class EditDetailsManager extends AppCompatActivity {

    EditText editTexts[];
    String text[];
    TextView textView[];
    int textresourcid[]={R.id.sku,R.id.msku,R.id.name,
            R.id.primarycategory,R.id.category,R.id.cp,R.id.mrp,R.id.sp,R.id.material,
            R.id.color,R.id.commentby};
    ProductDetailsModel model;
    int resourceIds[]={R.id.size,R.id.inventory,R.id.inventorytype,R.id.comment};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details_manager);
        initializeTextFields();
        setTextFields();
        setData();
        EditUtility.setContext(this);

    }
    public void initializeTextFields(){
        text=new String[11];
        for(int i=0;i<resourceIds.length;i++)
            editTexts[i]=(EditText)findViewById(resourceIds[i]);
        for(int i=0;i<textresourcid.length;i++)
            textView[i]=(TextView)findViewById(textresourcid[i]);

    }
    public void senddata(View view){
        for(int i=0;i<resourceIds.length;i++)
            text[i]=editTexts[i].getText().toString();
        if(EditUtility.validatedata(text))
            EditUtility.sendmanagerdata(text);

    }
    public void setTextFields(){

    }
    public void setData(){
        textView[0].setText(model.getSku());
        textView[1].setText(model.getMsku());
        textView[2].setText(model.getName());
        textView[3].setText(model.getPrimary_category());
        textView[4].setText(model.getCategory());
        textView[5].setText(model.getCp());
        textView[6].setText(model.getMrp());
        textView[7].setText(model.getSp());
        textView[8].setText(model.getMaterial());
        textView[9].setText(model.getColor());
        textView[10].setText(model.getCommentBy());


        editTexts[0].setText(model.getSize());
        editTexts[1].setText(model.getInventory());
        editTexts[2].setText(model.getInventory_type());
        editTexts[3].setText(model.getComment());
    }
}
