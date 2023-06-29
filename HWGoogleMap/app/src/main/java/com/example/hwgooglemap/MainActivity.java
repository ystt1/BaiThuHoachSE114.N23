package com.example.hwgooglemap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private TextView tv;
    private LocationManager locationManager;
    private int PERMISSION_CODE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv=findViewById(R.id.textView);

        locationManager= (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(
                MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                MainActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_CODE);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();
                getCurLocation(longi, lat);
            } else {
                Toast.makeText(this, "Không tìm thấy vị trí.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getCurLocation(double longidude,double latitude)
    {
        String cityName = "";
        Geocoder gcd = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation(latitude,longidude,10);
            if (addresses.size() > 0) {
                System.out.println(addresses.get(0).getLocality());
                cityName = addresses.get(0).getLocality();
                tv.setText("Vị trí hiện tại: "+cityName);
            }
            else
            {
                Toast.makeText(this,"Không Tìm Thấy Vị Trí!",Toast.LENGTH_SHORT).show();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}