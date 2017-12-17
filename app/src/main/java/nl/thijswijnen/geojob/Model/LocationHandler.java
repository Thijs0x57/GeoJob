package nl.thijswijnen.geojob.Model;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.concurrent.Executor;

import nl.thijswijnen.geojob.R;

/**
 * Created by thijs_000 on 11-Dec-17.
 */

public class LocationHandler
{
    private FusedLocationProviderClient fusedLocationProviderClient; //no settter or getter

    private boolean shouldShareLocation; //get and set
    private int updateFreq;     //get and set
    private Location location;  //only get

    private Context context;

    private static LocationHandler locationHandler;

    public static LocationHandler getInstance(Context context) {
        if(locationHandler == null){
            locationHandler = new LocationHandler(context);
        }
        return locationHandler;
    }


    private LocationHandler(Context context) {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        this.context = context;
        shouldShareLocation = true;
        updateFreq = 2000;
    }

    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            if(locationResult.getLocations().size() > 0)
                location = locationResult.getLocations().get(0);
        }
    };


    private LocationRequest generateRequest(){
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(updateFreq);
        locationRequest.setFastestInterval(updateFreq);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }

    public void setShouldShareLocation(boolean shouldShareLocation) {
        if(shouldShareLocation){
            startLocationIfEnabled();
        }else stopLocationIfDisabled();
        this.shouldShareLocation = shouldShareLocation;
    }

    private void startLocationIfEnabled() {
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context,"Permissions are not set",Toast.LENGTH_LONG);
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(generateRequest(), locationCallback, null);
    }
    private void stopLocationIfDisabled(){
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    
    public int getUpdateFreq() {
        return updateFreq;
    }

    public void setUpdateFreq(int updateFreq) {
        this.updateFreq = updateFreq;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location loc){
        location = loc;
    }

//    private Location currentLocation;
//
//    @SuppressLint("MissingPermission")
//    public LocationHandler(Context context, LocationManager locationManager, String LOCATION_PROVIDER)
//    {
//        currentLocation = locationManager.getLastKnownLocation(LOCATION_PROVIDER);
//        if (currentLocation == null)
//        {
//            Toast noGPSToast = Toast.makeText(context,"NO GPS SIGNAL", Toast.LENGTH_LONG);
//            noGPSToast.show();
//        }
//    }
//
//    public Location getCurrentLocation()
//    {
//        return currentLocation;
//    }
//
//    public void setCurrentLocation(Location currentLocation)
//    {
//        this.currentLocation = currentLocation;
//    }
}
