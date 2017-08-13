package com.example.shubham11.ecraftindia;


import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.shubham11.ecraftindia.app.AppConfig;
import com.example.shubham11.ecraftindia.app.AppController;
import com.example.shubham11.ecraftindia.app.SessionManager;
import com.example.shubham11.ecraftindia.helper.SQLiteHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
EditText emailtext,passwordtext;
    String email,password;
    Button signin;
    SessionManager sessionManager;

    SQLiteHandler db;
    String imei;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressBar=(ProgressBar)findViewById(R.id.progressbar);


        emailtext=(EditText) findViewById(R.id.email);
        passwordtext=(EditText) findViewById(R.id.password);
        signin=(Button)findViewById(R.id.btnLogin);

        sessionManager=new SessionManager(this);
        db=new SQLiteHandler(this);
        if(sessionManager.isLoggedin()){
            checkcredentials(db.getUserDetails().get("username"),db.getUserDetails().get("password"));
        }
        imei = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

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
    public void checkcredentials(final String email, final String password){
        showDialog();
        String my_req="login_req";
                StringRequest request=new StringRequest(Request.Method.POST, AppConfig.URL_LOGIN, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject res=new JSONObject(response);

                           int success=res.getInt("success");
                            if(success==1){
                                String accesS_role=res.getString("access_role");
                                db.addUser(email,accesS_role,password);
                                sessionManager=new SessionManager(LoginActivity.this);
                                sessionManager.setLogin(true);
                                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else if(success==-1)Toast.makeText(LoginActivity.this, "Your device is not registered.", Toast.LENGTH_LONG).show();
                            else Toast.makeText(LoginActivity.this, "Username/Password is not correct", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        finally {
                            hideDialog();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       hideDialog();

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() {
                        // Posting parameters to login url
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("username", email);
                        params.put("password", password);
                        params.put("uniqueid",imei);

                        return params;
                    }
                };
               // if(request!=null)
        AppController.getInstance().addToRequestQueue(request,my_req);
       // else Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();


    }
    public void showDialog(){
        progressBar.setVisibility(View.VISIBLE);

    }
    public void hideDialog(){
        progressBar.setVisibility(View.INVISIBLE);
    }

}
