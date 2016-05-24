package com.example.amynashamy.journeytracker;

import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by amynashAmy on 21/04/16.
 */
public class ViewAll extends AppCompatActivity{
    TextView results;
    MyDBManager db;
    LinearLayout container;
    String user;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        Bundle bundle = getIntent().getExtras();
        user = (bundle.getString("text1"));

        container = (LinearLayout) findViewById(R.id.database);
        results = (TextView) findViewById(R.id.results);
        db = new MyDBManager(this);

        //// Do Database Lookup

        db.open();
        getRows();
        db.close();
    }


    //---get all tasks---
    public void getRows() {
        Cursor c = db.getAll(user);
        if (c.moveToFirst()) {
            do {
                ShowTask(c);

            }
            while (c.moveToNext());
        }
    }

    public void ShowTask(Cursor c) {

        TextView text = new TextView(this);

        results.append("\n" +
                        "user: " + c.getString(1) + "\n" +
                        "lat: " + c.getString(2) + "\n" +
                        "lon: " + c.getString(3) + "\n" +
                        "event: " + c.getString(4) + "\n" +
                        "duration: " + c.getString(5) + "\n" +
                        "distance: " + c.getString(6) + "\n" +
                        "date: " + c.getString(7) + "\n" +
                        "mode: " + c.getString(8) + "\n"


        );

        container.addView(text);

    }
}
