package nl.thijswijnen.geojob.UI;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import nl.thijswijnen.geojob.Model.CurrentLocation;
import nl.thijswijnen.geojob.R;

public class NavigateActivity extends FragmentActivity implements OnMapReadyCallback
{

    private GoogleMap mMap;

    final String LOCATION_PROVIDER = LocationManager.GPS_PROVIDER;
    private LocationManager locationManager;
    private Location lastLocation = null;

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

        lastLocation = new CurrentLocation(this, locationManager, LOCATION_PROVIDER).getCurrentLocation();
        double latitude = lastLocation.getLatitude();
        double longitude = lastLocation.getLongitude();
        LatLng currentLocationLatLng = new LatLng(latitude, longitude);

        mMap.addMarker(new MarkerOptions().position(currentLocationLatLng).title("Current location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocationLatLng));
    }
}
