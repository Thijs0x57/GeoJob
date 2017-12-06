package nl.thijswijnen.geojob.Controller;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by thijs_000 on 05-Dec-17.
 */

public class LocationHandler
{

    private LocationManager locationManager;
    public static final String PERMISSION_STRING
            = android.Manifest.permission.ACCESS_FINE_LOCATION;


    public Location getCurrentLocation()
    {
        return null;
    }
}
