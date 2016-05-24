package com.example.amynashamy.journeytracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by amynashAmy on 19/04/16.
 */
public class OptionRecording extends AppCompatActivity {

    private Spinner spinner2;
    private String mode;
    Button button1;
    String user;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_recording);
        Bundle bundle = getIntent().getExtras();
        user = (bundle.getString("text1"));

        spinner2 = (Spinner) findViewById(R.id.modespinner);

        List<String> list1 = new ArrayList<String>();

        list1.add("Walking");
        list1.add("Driving");
        list1.add("Cylcing");
        list1.add("Rollerblading");


        ArrayAdapter<String> modespinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list1);
        modespinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner2.setAdapter(modespinner);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mode = spinner2.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(OptionRecording.this, StartRecording.class);
                intent.putExtra("text2", mode);
                intent.putExtra("text1", user);
                startActivity(intent);

            }
        });


    }
}
