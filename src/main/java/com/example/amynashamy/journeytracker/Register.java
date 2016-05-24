package com.example.amynashamy.journeytracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.database.SQLException;

/**
 * Created by amynashAmy on 31/03/16.
 */
public class Register extends AppCompatActivity {

    Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText txtUserName = (EditText)findViewById(R.id.txtUsername);
        final EditText txtPassword = (EditText)findViewById(R.id.txtPassword);

        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String username = txtUserName.getText().toString();
                String password = txtPassword.getText().toString();


                try {
                    if (username.length() > 0 && password.length() > 0) {
                        MyDBManager db = new MyDBManager(Register.this);
                        db.open();
                        db.insertUser(username, password);
                        Intent intent1 = new Intent(Register.this, Login.class);
                        startActivity(intent1);
                        db.close();


                    } else {
                       Toast.makeText(Register.this, "Invalid Username/Password", Toast.LENGTH_LONG).show();

                     }

                } catch (SQLException e) {
                    e.printStackTrace();
                    Toast.makeText(Register.this, "Error Registering", Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}