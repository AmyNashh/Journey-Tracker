package com.example.amynashamy.journeytracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by amynashAmy on 31/03/16.
 */
public class Login extends AppCompatActivity {

    Button button1;
    MyDBManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText txtUserName = (EditText)findViewById(R.id.txtUsername);
        final EditText txtPassword = (EditText)findViewById(R.id.txtPassword);
        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String username = txtUserName.getText().toString();
                String password = txtPassword.getText().toString();

                try{
                    if(username.length() > 0 && password.length() >0)
                    {
                        MyDBManager db = new MyDBManager(Login.this);
                        db.open();

                        if(db.Login(username, password))
                        {
                            Toast.makeText(Login.this, "Successfully Logged In", Toast.LENGTH_LONG).show();
                            Intent intent1 = new Intent(Login.this, Menu.class);
                            intent1.putExtra("text1", username);
                            startActivity(intent1);
                        }else{
                            Toast.makeText(Login.this,"Invalid Username/Password", Toast.LENGTH_LONG).show();
                        }
                        db.close();
                    }

                }catch(Exception e)
                {
                    Toast.makeText(Login.this,e.getMessage(), Toast.LENGTH_LONG).show();
                }


            }

        });
    }
}