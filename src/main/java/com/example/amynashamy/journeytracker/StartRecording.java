package com.example.amynashamy.journeytracker;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by amynashAmy on 31/03/16.
 */
public class StartRecording extends AppCompatActivity implements LocationListener {

    private TextView journeydetails;
    private TextView modee;
    private TextView duration;
    private TextView locationText;
    private LocationManager locationManager;
    Button button1;

    boolean GPSenabled = false;

    boolean first_reading=true;

    MyDBManager db;
    double distance=0;
    double previous_lat,previous_lon;
    double lon,lat;
    int seconds;
    int minutes;
    int hours;
    Timer timer;
    int time;
    double times;
    String username;
    String mode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_recording);
        db = new MyDBManager(this);
        Bundle bundle = getIntent().getExtras();
        mode = (bundle.getString("text2"));
        username = (bundle.getString("text1"));

        button1 = (Button) findViewById(R.id.button1);
        journeydetails = (TextView) findViewById(R.id.journeydetails);
        modee = (TextView) findViewById(R.id.mode);
        duration = (TextView) findViewById(R.id.duration);
        locationText = (TextView) findViewById(R.id.distance);

        journeydetails.setText("Journey Details");
        modee.setText(mode);
        duration.setText("Journey Duration" + time);
        locationText.setText("Distance to be calculated");

        setUpLocation();

        checkIsGpsEnabled();


        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                times++;
            }
        }, 0, 1000);



        Timer timers = new Timer();
        timers.schedule(new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView txtClicks = (TextView) findViewById(R.id.duration);
                        seconds = seconds + 1;

                        if (seconds == 60) {
                            minutes++;
                            seconds = 0;
                            seconds++;
                        }
                        if (minutes == 60) {
                            hours++;
                            minutes = 0;
                            minutes++;
                        }

                        txtClicks.setText(String.valueOf(hours + " hours " + minutes + " minutes " + seconds + " seconds"));
                        time = hours + minutes + seconds;
                    }
                });
            }

        }, 0, 1000);

    }

    public void onLocationChanged(Location location) {
        String latestLocation = "";

        if (location != null) {
            if(first_reading)
            {
                if(location.getLatitude()!=0 && location.getLongitude()!=0) {

                    previous_lat = location.getLatitude();

                    previous_lon = location.getLongitude();

                    first_reading = false;

                    distance=0;

                    String date = new SimpleDateFormat("yyyy.MM.dd").format(Calendar
                            .getInstance().getTime());
                    db.open();
                    db.insertTask(username, 1, previous_lat, previous_lon, distance, 0, date, mode);
                    db.close();
                }
            }

            else
            {


                lon = location.getLongitude();
                lat=location.getLatitude();

                // Calculates the distance

                double dlong = toRad(lon - previous_lon);
                double dlat = toRad(lat - previous_lat);
                double a =
                        Math.pow(Math.sin(dlat / 2.0), 2)
                                + Math.cos(toRad(previous_lat))
                                * Math.cos(toRad(location.getLatitude()))
                                * Math.pow(Math.sin(dlong / 2.0), 2);
                double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
                double d = 6367 * c;


                distance = distance + d;



                previous_lat=lat;

                previous_lon=lon;

                button1.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(StartRecording.this, Menu.class);
                        intent.putExtra("text1", username);
                        startActivity(intent);

                        String date = new SimpleDateFormat("yyyy.MM.dd").format(Calendar
                                .getInstance().getTime());
                        db.open();
                        db.insertTask(username, 3, lat, lon, distance, times /60, date, mode);
                        db.close();

                    }
                });

            }
        }
        Toast.makeText(StartRecording.this, previous_lat+" "+previous_lon,
                Toast.LENGTH_LONG).show();

        //Displays the distance and time calculations

        locationText.setText(distance+"kms");

    }

    private Double toRad(Double value) {
        return value * Math.PI / 180;
    }

    public void onProviderDisabled(String provider) {
        // Code to do something if location provider is disabled e.g. display error
    }

    public void onProviderEnabled(String provider) {
        // Code to do something if location provider becomes available e.g check if itís a  more useful provider
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
        // Code to do something if location provider status changes..
    }


    // custom method
    private void setUpLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);




        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                5001,
                5,
                this);
    }

    /**
     * Register for the updates when Activity is in foreground i.e. only use up the battery power if you have to..
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                60000,
                5,
                this);
    }


    /**
     * Stop the updates when Activity is paused i.e. save your battery
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.removeUpdates(this);
    }


    private void showAlertDialogNoGPS()
    {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(	"Your handset's GPS location function is disabled and without it the application cannot run")
                .setCancelable(false)
                .setPositiveButton("Enable GPS",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog,final int id) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent, 3); 		// will come back to onActivityResult()
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int id) {
                        // Finishes all acivities currently open
                        finish();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void checkIsGpsEnabled()
    {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            GPSenabled = false;
            showAlertDialogNoGPS();
        }
        else
            GPSenabled = true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 3 && resultCode == 0) {
            String provider = Settings.Secure.getString(getContentResolver(),Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            if (provider != null) {
                if ((provider.contains("gps")) || (provider.contains("GPS"))) {
                    GPSenabled = true;
                    return;	// gps enabled - go ahead
                }
                else{
                    GPSenabled = false;


                }
            }else{
                GPSenabled = false;
            }
            Toast.makeText(this,"GPS satellites location still not enabled",	Toast.LENGTH_LONG).show();
            finish();
        }

    }

}