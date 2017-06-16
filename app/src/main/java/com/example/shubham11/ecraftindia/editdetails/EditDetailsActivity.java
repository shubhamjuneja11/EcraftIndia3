package com.example.shubham11.ecraftindia.editdetails;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


import com.example.shubham11.ecraftindia.R;

public class EditDetailsActivity extends AppCompatActivity {

    EditText editTexts[];
    String text[];
    int resourceIds[]={R.id.sku,R.id.msku,R.id.name,R.id.primarycategory,R.id.category,R.id.cp,R.id.mrp,R.id.sp,R.id.material,R.id.color,R.id.size,R.id.inventory,R.id.inventorytype,R.id.comment};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details);

        initializeTextFields();
        EditUtility.setContext(this);

    }
    public void initializeTextFields(){
        editTexts=new EditText[14];
        text=new String[14];
        for(int i=0;i<resourceIds.length;i++)
            editTexts[i]=(EditText)findViewById(resourceIds[i]);


    }
    public void senddata(View view){
       for(int i=0;i<resourceIds.length;i++)
           text[i]=editTexts[i].getText().toString();
        if(EditUtility.validatedata(text))
            EditUtility.sendAlldata(text);

    }
}
