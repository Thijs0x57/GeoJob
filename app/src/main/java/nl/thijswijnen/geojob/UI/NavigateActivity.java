package nl.thijswijnen.geojob.UI;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import nl.thijswijnen.geojob.Model.LocationHandler;
import nl.thijswijnen.geojob.Model.PointOfInterest;
import nl.thijswijnen.geojob.Model.Route;
import nl.thijswijnen.geojob.Model.RouteHandler;
import nl.thijswijnen.geojob.R;
import nl.thijswijnen.geojob.Util.Constants;

public class NavigateActivity extends FragmentActivity implements OnMapReadyCallback
{
    final String LOCATION_PROVIDER = LocationManager.GPS_PROVIDER;
    private LocationManager locationManager;
    private Location lastLocation = null;
    private LocationHandler locationHandler;
    private RouteHandler routeHandler;
    public static GoogleMap mMap;

    private Route route;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigate);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.navigate_map_map);
        mapFragment.getMapAsync(this);

        ImageButton settingsButton = findViewById(R.id.navigate_settings_btn);
        settingsButton.setOnClickListener(view ->
                startActivity(new Intent(this, SettingsActivity.class)));

        locationHandler = LocationHandler.getInstance(this);
        locationHandler.setShouldShareLocation(true);

        //getting Route from intent
        Bundle b = getIntent().getExtras();
        route = (Route) b.getSerializable("route");
        callRouteHandler();
    }

    private void callRouteHandler()
    {
        Location currentLoc = getCurrentLocation();
        List<PointOfInterest> pointOfInterestList = route.getAllPointsOfInterest();
        if (routeHandler == null)
        {
            routeHandler = new RouteHandler(this, new LatLng(currentLoc.getLatitude(), currentLoc.getLongitude()), pointOfInterestList);
        }else
        {
            mMap.addPolyline(routeHandler.getPolylineOptions());
            mMap.animateCamera(routeHandler.getCameraUpdate());
        }
    }

    public Location getCurrentLocation() {
        Location currentLocation = getLastKnownLocation();
        if (currentLocation == null)
        {
            Toast noGPSToast = Toast.makeText(getApplicationContext(),"NO GPS SIGNAL", Toast.LENGTH_LONG);
            noGPSToast.show();
        }
        return currentLocation;
    }

    private Location getLastKnownLocation() {
        locationManager = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, Constants.PERMISSION_REQUEST_CODE);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, Constants.PERMISSION_REQUEST_CODE);
        }
        for (String provider : providers) {
            Location l = locationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
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
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        lastLocation = locationHandler.getLocation();
        if (lastLocation != null)
        {
            LatLng currentLocationLatLng = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
            mMap.addMarker(new MarkerOptions().position(currentLocationLatLng).title("Current location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocationLatLng));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //outState.putAll(Bundle b);
        // ...
        super.onSaveInstanceState(outState);
    }

    private void updateValuesFromBundle(Bundle savedInstanceState) {
        // Update the value of mRequestingLocationUpdates from the Bundle.
        if (savedInstanceState.keySet().contains("LocationHandlerKey")) {
            //locationHandler = savedInstanceState.getSerializable("LocationHandlerKey");
        }

        // ...

        // Update UI to match restored state
        //updateUI();
    }

    @Override
    protected void onResume(){
        super.onResume();
        locationHandler.setShouldShareLocation(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationHandler.setShouldShareLocation(false);
    }
}
