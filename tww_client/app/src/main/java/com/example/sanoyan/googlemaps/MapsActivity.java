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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import android.content.ContextWrapper;
import android.util.Log;

import android.view.View;
import android.widget.ImageButton;
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

    private DBAdapter db;
    private ArrayList<Position> listPosition;
    private Position dernierePosition;

    ImageButton dangerButton;
    private Position dangerPosition;


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Log.d("Tag", "Test");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        dangerButton = (ImageButton) findViewById(R.id.dangerButton);

        dangerButton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                try {
                    db.open();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                dangerPosition = db.recupererDernierePosition();
                db.close();

                Toast.makeText(getApplicationContext(),"Danger signalé", Toast.LENGTH_SHORT).show();
                // dangerPosition à ajouter dans la BDD
                Log.d("Tag", "Danger " + dangerPosition.getLatitude() + " " + dangerPosition.getLongitude());
                LatLng positionDanger = new LatLng(dangerPosition.getLatitude(), dangerPosition.getLongitude());
                mMap.addMarker(new MarkerOptions()
                        .position(positionDanger)
                        .title("Danger")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

            }

        });
/*
        imageButton = (ImageButton) findViewById(R.id.dangerButton);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Tag", "Clic Ok");
                imageButton.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(),"You download is resumed",Toast.LENGTH_LONG).show();
            }
        });
        */
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
                Log.d("Tag", location.getLatitude() + " " + location.getLongitude());
                LatLng myPosition = new LatLng(dernierePosition.getLatitude(), dernierePosition.getLongitude());
                mMap.addMarker(new MarkerOptions()
                        .position(myPosition)
                        .title("My Position")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
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
