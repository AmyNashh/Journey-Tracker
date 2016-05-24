package com.example.amynashamy.journeytracker;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by amynashAmy on 31/03/16.
 */
public class ViewJourneys extends AppCompatActivity{


    MyDBManager db;
    String username;

    ListView list;

    ArrayList<Double> Start_Lat;

    ArrayList<Double> Start_Lon;

    ArrayList<Double> Start_Event;

    ArrayList<Double> Stop_Distance;

    ArrayList<Double> Stop_Duration;

    ArrayList<Double> Stop_Lat;

    ArrayList<Double> Stop_Lon;

    ArrayList<String> Start_Date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_journeys);
       Bundle bundle = getIntent().getExtras();
       username = (bundle.getString("text1"));

        Start_Lat = new ArrayList<>();
        Start_Lon = new ArrayList<>();
        Start_Event =  new ArrayList<>();

        Stop_Lat = new ArrayList<>();
        Stop_Lon = new ArrayList<>();
        Stop_Distance = new ArrayList<>();
        Stop_Duration = new ArrayList<>();

        Start_Date = new ArrayList<>();

        db = new MyDBManager(this);

        db.open();

        get_Journeys(username);

        db.close();


        list = (ListView) findViewById(R.id.listcandresults);
        list.setAdapter(new MyCustomAdapter(ViewJourneys.this, R.layout.row, Start_Event, Start_Lat, Start_Lon));


        list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                double lat = Start_Lat.get(position);
                double lon = Start_Lon.get(position);

                Intent intent = new Intent(ViewJourneys.this, Map.class);
                intent.putExtra("start_lat", lat);
                intent.putExtra("start_lon", lon);

                intent.putExtra("stop_lat", Stop_Lat.get(position));
                intent.putExtra("stop_lon", Stop_Lon.get(position));
                startActivity(intent);

            }
        });



    }


    public class MyCustomAdapter extends ArrayAdapter<Double> {

        ArrayList<Double> Adapter_Event;
        ArrayList<Double> Adapter_Lat;
        ArrayList<Double> Adapter_Lon;


        public MyCustomAdapter(Context context, int textViewResourceId,ArrayList<Double> a, ArrayList<Double> b, ArrayList<Double> c)
        {
            super(context, textViewResourceId,a);
            // TODO Auto-generated constructor stub
            Adapter_Event=a;
            Adapter_Lat=b;
            Adapter_Lon=c;

        }

        @Override

        public View getView(int position, View convertView, ViewGroup parent)
        {

            // TODO Auto-generated method stub
            //return super.getView(position, convertView, parent);
            LayoutInflater inflater=getLayoutInflater();
            View row=inflater.inflate(R.layout.row, parent, false);

            TextView date=(TextView)row.findViewById(R.id.date);
            TextView mileage=(TextView)row.findViewById(R.id.mileage);
            TextView duration=(TextView)row.findViewById(R.id.duration);


            ImageView icon = (ImageView) row.findViewById(R.id.icon);
            icon.setImageResource(R.drawable.map);

            date.setText("" + Start_Date.get(position));
            duration.setText("\n" + "" + Stop_Duration.get(position)+"sec");

            return row;
        }
    }


    public void get_Journeys(String username) {
        Cursor c = db.getStarts(username);
        Cursor c2 = db.getStops(username);

        if (c.moveToFirst()) {
            do
            {

                ShowJourney(c, c2);

            }
            while (c.moveToNext()&&c2.moveToNext());
        }
    }


    public void ShowJourney(Cursor c, Cursor c2)
    {


        Start_Date.add((c.getString(7)));
        Start_Lat.add((c.getDouble(3)));
        Start_Event.add((c.getDouble(2)));
        Start_Lon.add((c.getDouble(4)));

        Stop_Lat.add((c2.getDouble(3)));
        Stop_Lon.add((c2.getDouble(4)));
        Stop_Duration.add((c2.getDouble(6)));
        Stop_Distance.add((c2.getDouble(5)));


    }

    /*
    //Method is passed a username and password - if the user is found then the user id returned
    // If the user does not exist then -1 is returned
    public int getUser(String username, String password) {

        Cursor c = db.getUsers(username, password);

        if (c.moveToFirst()) {
            do {

                return c.getInt(0);

            }
            while (c.moveToNext());
        }

        return -1;
    }
    */

}
