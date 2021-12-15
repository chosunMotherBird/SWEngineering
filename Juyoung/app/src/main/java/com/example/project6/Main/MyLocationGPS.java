package com.example.project6.Main;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;


import java.util.List;

public class MyLocationGPS extends Service implements LocationListener {
    private final Context context;
    private LocationManager locationManager;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;
    Location myLocation;
    double lat;
    double lng;

    public MyLocationGPS(Context context) {
        this.context = context;
    }

    public Location getMyLatLng() {
        locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!isGPSEnabled && !isNetworkEnabled) {

        } else {
            int hasFineLocationPermission = ContextCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_FINE_LOCATION);
            int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_COARSE_LOCATION);
            if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                    hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {
            } else
                return null;
            if (isNetworkEnabled) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES,
                        this);
                if (locationManager != null) {
                    myLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (myLocation != null) {
                        lat = myLocation.getLatitude();
                        lng = myLocation.getLongitude();
                    }
                }
            }
            if (isGPSEnabled) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES,
                        this);
                if (locationManager != null) {
                    myLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (myLocation != null) {
                        lat = myLocation.getLatitude();
                        lng = myLocation.getLongitude();
                    }
                }
            }
        }
        return myLocation;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }

    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {

    }

    @Override
    public void onFlushComplete(int requestCode) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        if(locationManager!=null)
            locationManager.removeUpdates(MyLocationGPS.this);
    }
}
