package com.example.sanoyan.googlemaps;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.location.LocationListener;
//import com.google.android.gms.location.LocationListener;
import com.example.sanoyan.googlemaps.bdd.DBAdapter;
import com.example.sanoyan.googlemaps.bdd.Position;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import android.content.ContextWrapper;
import android.util.Log;

import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback {

    private GoogleMap mMap;
    private static final int REQUEST_CODE = 1;

    private LocationManager locationManager;
    private LocationListener locationListener;
    private TextView textView;

    private DBAdapter db;
    private ArrayList<Position> listPosition;
    private Position dernierePosition;


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Log.d("Tag", "Test");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        textView = (TextView) findViewById(R.id.textView);



    }

    @Override
    public void onMapReady(GoogleMap map) {


        mMap = map;

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                // INSERER DANS LA BASE DE DE DONNEES location.getLatitude et location.getLongitude
                Position nouvellePosition = new Position(location.getLatitude(), location.getLongitude());
                db = new DBAdapter(getApplicationContext());
                try {
                    db.open();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                db.creerUnePosition(nouvellePosition);
                db.close();

                //ACCES à la bdd
                try {
                    db.open();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                listPosition = db.recupererTouteLesPositions();
                dernierePosition = db.recupererDernierePosition();
                db.close();
                /*
                PolylineOptions polyoption = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
                for(int i=0; i<listPosition.size(); i++){
                    LatLng latLng = new LatLng(listPosition.get(i).getLatitude(), listPosition.get(i).getLongitude());
                    mMap.addPolyline(polyoption.add(latLng));
                }
*/
                textView.append("\n " + dernierePosition.getLatitude() +
                        " " + dernierePosition.getLongitude());
                LatLng myPosition = new LatLng(dernierePosition.getLatitude(), dernierePosition.getLongitude());
                mMap.addMarker(new MarkerOptions().position(myPosition).title("My Position"));
/*
                //Je met ta partie en commentaire pour essayer de mettre les marqueurs à partir de la bdd
                textView.append("\n " + location.getLatitude() +
                        " " + location.getLongitude());
                LatLng myPosition = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(myPosition).title("My Position"));
*/
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.INTERNET,
                                Manifest.permission.ACCESS_FINE_LOCATION},
                        10);
                return;
            } else {
                locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
            }

        } else{
            locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
        }



        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));

        //LatLng barcelona = new LatLng(2.1699, 41.38);
        //mMap.addMarker(new MarkerOptions().position(barcelona).title("Marker in Barcelone"));

        //LatLng chennai= new LatLng(13.0826744, 80.27066070000001);
        //mMap.addMarker(new MarkerOptions().position(chennai).title("Marker in Barcelone"));


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(MapsActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
        }
        map.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                LatLng loc = new LatLng(mMap.getMyLocation().getLatitude(),mMap.getMyLocation().getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
                return true;
            }
        });


    }

    @Override

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("Tag", "Permission Ok");
                } else {
                    finish();
                }
                break;
            }
        }
    }

}
