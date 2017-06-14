package com.example.shubham11.ecraftindia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.EditText;
import android.widget.TextView;

public class EditDetailsActivity extends AppCompatActivity {
String access;
    EditText editText;
    TextView textView;
    LinearLayoutManager manager;
    char a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details);
        access=getIntent().getStringExtra("access");
        manager=new LinearLayoutManager(this);
        for(int i=0;i<access.length();i++)
        {
         a=access.charAt(i);
            if(a=='1')
            {
                editText=new EditText(this);
                manager.addView(editText);
            }
            else {
                textView=new TextView(this);
                manager.addView(textView);
            }

        }
    }
}
