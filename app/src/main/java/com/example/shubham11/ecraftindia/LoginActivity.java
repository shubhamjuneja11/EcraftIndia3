package com.example.shubham11.ecraftindia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.shubham11.ecraftindia.app.AppConfig;
import com.example.shubham11.ecraftindia.app.SessionManager;
import com.example.shubham11.ecraftindia.helper.SQLiteHandler;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
EditText emailtext,passwordtext;
    String email,password;
    Button signin;
    SessionManager sessionManager;
    ProgressDialog progressDialog;
    SQLiteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);


        emailtext=(EditText) findViewById(R.id.email);
        passwordtext=(EditText) findViewById(R.id.password);
        signin=(Button)findViewById(R.id.btnLogin);

        sessionManager=new SessionManager(this);
        db=new SQLiteHandler(this);
        if(sessionManager.isLoggedin()){
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
        }

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=emailtext.getText().toString();
                password=passwordtext.getText().toString();

                if(!email.isEmpty()&&!password.isEmpty()){
                    checkcredentials(email,password);
                }
                else Toast.makeText(LoginActivity.this, "Please entername username and password.", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void checkcredentials(final String email, String password){
                StringRequest request=new StringRequest(Request.Method.POST, AppConfig.URL_LOGIN, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject res=new JSONObject(response);
                            boolean success=res.getBoolean("error");
                            if(success){
                                db.addUser(email);
                                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
    }

}
