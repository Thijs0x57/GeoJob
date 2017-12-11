package nl.thijswijnen.geojob.Model;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import nl.thijswijnen.geojob.Util.Constants;

/**
 * Created by thijs_000 on 11-Dec-17.
 */

public class CurrentLocation
{
    private Location currentLocation;

    @SuppressLint("MissingPermission")
    public CurrentLocation(Context context, LocationManager locationManager, String LOCATION_PROVIDER)
    {
        currentLocation = locationManager.getLastKnownLocation(LOCATION_PROVIDER);
        if (currentLocation == null)
        {
            Toast noGPSToast = Toast.makeText(context,"NO GPS SIGNAL", Toast.LENGTH_LONG);
            noGPSToast.show();
        }
    }

    public Location getCurrentLocation()
    {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation)
    {
        this.currentLocation = currentLocation;
    }
}
