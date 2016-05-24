package com.example.amynashamy.journeytracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by amynashAmy on 31/03/16.
 */
public class Menu extends AppCompatActivity {

    private MyDBManager db;
    private Button view;
    String username;
    String user2;
    Button button2;
    Button button3;
    Button button4;
    String mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Bundle bundle = getIntent().getExtras();
        username = (bundle.getString("text1"));

        String date = new SimpleDateFormat("yyyy.MM.dd").format(Calendar
                .getInstance().getTime());

        view = (Button)findViewById(R.id.view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, ViewJourneys.class);
                intent.putExtra("text1", username);
                startActivity(intent);
            }
        });

        button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, OptionRecording.class);
                intent.putExtra("text1", username);
                startActivity(intent);

            }
        });

        button3 = (Button)findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, ViewAll.class);
                intent.putExtra("text1", username);
                startActivity(intent);

            }
        });

        button4 = (Button)findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, MainActivity.class);
                intent.putExtra("text1", username);
                startActivity(intent);

            }
        });

    }
}
