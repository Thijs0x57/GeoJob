package nl.thijswijnen.geojob.Util;

import android.Manifest;
import android.content.SharedPreferences;

/**
 * Created by thijs_000 on 11-Dec-17.
 */

public class Constants
{
    public static final String API_KEY = "AIzaSyDlPMbvEikR40aphGhAQHBirTTPonIR5Ic";
    public static final int GEOFENCE_RADIUS_IN_METERS = 5;
    public static final int GEOFENCE_EXPIRATION_IN_MILLISECONDS = 10000;

    public static final int PERMISSION_REQUEST_CODE = 698;

    public static final String PERMISSION_FINELOCATION_STRING = android.Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String PERMISSION_COARSELOCATION_STRING = Manifest.permission.ACCESS_COARSE_LOCATION;
}
