package com.example.shubham11.ecraftindia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EditDetailsActivity extends AppCompatActivity {
String access;
    EditText editText;
    TextView textView;
    char a;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details);
        linearLayout=(LinearLayout)findViewById(R.id.linear);
        access=getIntent().getStringExtra("access");

        for(int i=0;i<access.length();i++)
        {
         a=access.charAt(i);
            if(a=='1')
            {
                editText=new EditText(this);
                editText.setText("hello");
                linearLayout.addView(editText);
            }
            else {
                textView=new TextView(this);
                textView.setText("by");
                linearLayout.addView(textView);
            }

        }
    }
}
