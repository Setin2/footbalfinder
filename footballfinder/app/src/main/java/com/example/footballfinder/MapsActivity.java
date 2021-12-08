package com.example.footballfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.util.Consumer;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.example.footballfinder.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private static final int MY_REQUEST_INT = 177;
    private ArrayList<Field> fields;
    private int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        com.example.footballfinder.databinding.ActivityMapsBinding binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        map.setOnMarkerClickListener(this);
        // Here We want to check the permission of Location - GPS
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION}, MY_REQUEST_INT);
            }
        } else {
            // Here the code of Grand Permission
            map.setMyLocationEnabled(true);
        }
        map.moveCamera(getCurrentLocation());
        loadFieldsIntoMap(map);
    }

    private CameraUpdate getCurrentLocation(){
        if(ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) == PackageManager.PERMISSION_GRANTED){
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            CameraUpdate point = CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 12.0f);
            return point;
        }
        return CameraUpdateFactory.newLatLngZoom(new LatLng(55.4038, 10.4024), 12.0f);
    }

    private void loadFieldsIntoMap(GoogleMap map){
        Field.getAllFields(this, data -> {
            fields = data;

            for(int i = 0; i < fields.size(); i++){
                Field field = fields.get(i);
                MarkerOptions marker = new MarkerOptions()
                        .position(new LatLng(field.lat, field.lon))
                        .title(String.valueOf(field.id))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                map.addMarker(marker);
            }
        }, error -> {

        });
    }


    /*
     * Open MakerEvents activity. Contains a recyclerview with the events for this field
     */
    @Override
    public boolean onMarkerClick(final Marker marker) {
        Intent intent = new Intent(this, MarkerEvents.class);
        intent.putExtra("fieldID", Integer.parseInt(marker.getTitle()));
        intent.putExtra("userID", userID);
        startActivity(intent);
        return true;
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}