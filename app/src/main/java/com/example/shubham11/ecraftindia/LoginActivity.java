package com.example.shubham11.ecraftindia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
EditText emailtext,passwordtext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailtext=(EditText) findViewById(R.id.email);
        passwordtext=(EditText) findViewById(R.id.password);
    }
}
