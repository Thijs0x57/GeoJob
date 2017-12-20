package nl.thijswijnen.geojob.UI;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.List;

import nl.thijswijnen.geojob.Model.GeoFenceHandler;
import nl.thijswijnen.geojob.Model.HistorischeKilometer;
import nl.thijswijnen.geojob.Model.LocationHandler;
import nl.thijswijnen.geojob.Model.PointOfInterest;
import nl.thijswijnen.geojob.Model.Route;
import nl.thijswijnen.geojob.Model.RouteHandler;
import nl.thijswijnen.geojob.R;
import nl.thijswijnen.geojob.Util.Constants;

public class NavigateActivity extends FragmentActivity implements OnMapReadyCallback {


    private LocationHandler locationHandler;
    private RouteHandler routeHandler;

    private GoogleMap mMap;

    private Route route;


    private PointOfInterest nextPointOfInterest;
    private GeoFenceHandler geoFenceHandler;

    private boolean isRunningInBackground;
    private boolean isShowingOffRouteMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigate);
        //getting Route from intent
        Bundle b = getIntent().getExtras();
        route = (Route) b.getSerializable("route");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.navigate_map_map);
        mapFragment.getMapAsync(this);

        ImageButton settingsButton = findViewById(R.id.navigate_settings_btn);
        settingsButton.setOnClickListener(view ->
                startActivity(new Intent(this, SettingsActivity.class)));

        locationHandler = LocationHandler.getInstance(this);
        locationHandler.setShouldShareLocation(true);

        geoFenceHandler = new GeoFenceHandler(this);


        for (PointOfInterest pointOfInterest : route.getHKPointsOfInterests()) {
            geoFenceHandler.createGeoFence(pointOfInterest.getLocation());
        }
        geoFenceHandler.addGeofenceToClient();

        Button backbutton = findViewById(R.id.navigate_pauzeplay_btn);
        backbutton.setOnClickListener(view -> {
            showExitAlertdiaglog();
        });

        //init for loop
        nextPointOfInterest = route.getHKPointsOfInterests().get(0);
    }

    private AlertDialog showExitAlertdiaglog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.exit_navigate_activity_tile));
        builder.setPositiveButton(getResources().getString(R.string.exit_navigate_activity_positive), (dialogInterface, i) -> {
            finish();
        });
        builder.setNegativeButton(getResources().getString(R.string.exit_navigate_cancel), (dialogInterface, i) -> {

        });
        return builder.show();
    }

    @Override
    public void onBackPressed() {
        showExitAlertdiaglog();
    }

    @Override
    protected void onStart() {
        super.onStart();
        isRunningInBackground = true;
        callRouteHandler();
    }

    @Override
    protected void onStop() {
        super.onStop();
        isRunningInBackground = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("NavigateActivity","geofencingReceiver has been unregisterd");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("NavigateActivity","geofencingReceiver has been registerd");
    }

    //TODO: ROEP DEZE AAN ALS DE GEOLOCATIE GETRIGGERD WORDT
    private void openPOI(PointOfInterest p) {
        if (!p.isVisited()) {
            Intent i = new Intent(getApplicationContext(), DetailPoiActivity.class);
            p.setVisited(true);
            synchronized (routeHandler.getMarkers()){
                for (Marker marker : routeHandler.getMarkers()) {
                    runOnUiThread(() -> {
                        if(marker.getPosition().equals(p.getLocation())){
                            marker.remove();
                        }
                    });
                }
                routeHandler.updateMarker(p.getLocation(), p.getTitle());
                i.putExtra("POI", p);
                startActivity(i);
                Log.d("NavigateActiviy","open poi");
            }
        }
    }

    private void callRouteHandler()
    {
        new Thread(() ->{
            LocationHandler handler = LocationHandler.getInstance(this);
            while (handler.getLocation() == null || mMap == null){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            final Location[] currentLoc = {handler.getLocation()};
            List<PointOfInterest> pointOfInterestList = route.getHKPointsOfInterests();
            if (routeHandler == null)
            {
                routeHandler = new RouteHandler(this, new LatLng(currentLoc[0].getLatitude(), currentLoc[0].getLongitude()), pointOfInterestList,mMap,route);
            }else
            {
                runOnUiThread(() -> {
                    mMap.animateCamera(routeHandler.getCameraUpdate());
                });
            }

            new Thread(() ->{
                boolean init = false;

                synchronized (routeHandler.getMarkers()) {
                    for (Marker marker : routeHandler.getMarkers()) {
                        runOnUiThread(() -> {
                            if (marker.getPosition().equals(nextPointOfInterest.getLocation())) {
                                marker.remove();
                            }
                        });
                    }
                }


                routeHandler.updateMarkerDarkBlue(nextPointOfInterest.getLocation(),nextPointOfInterest.getTitle());

                while (isRunningInBackground){

                    if(!routeHandler.getPolylinesMap().isEmpty()){
                        runOnUiThread(()->{
                            boolean onRoute = false;
                            for (Polyline polyline : routeHandler.getPolylinesMap()) {
                                if(PolyUtil.isLocationOnPath(new LatLng(currentLoc[0].getLatitude(),currentLoc[0].getLongitude()),polyline.getPoints(),false,30)){
                                    onRoute = true;
                                }
                            }
                            if(!onRoute){
                                if(!isShowingOffRouteMessage){
                                    isShowingOffRouteMessage = true;
                                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                                    builder.setCancelable(false);
                                    builder.setTitle(getResources().getString(R.string.navigate_exit_route));
                                    builder.setPositiveButton(getResources().getString(R.string.exit_navigate_activity_positive), (dialogInterface, i) -> {
                                        isShowingOffRouteMessage = false;
                                    });
                                    builder.show();
                                }
                            }
                        });
                    }



                    currentLoc[0] = handler.getLocation();
                    for (PointOfInterest pointOfInterest : route.getHKPointsOfInterests()) {
                        if(!pointOfInterest.isVisited() || pointOfInterest.equals(nextPointOfInterest)){
                            float distance = distance(pointOfInterest.getLocation().latitude,pointOfInterest.getLocation().longitude,
                                    currentLoc[0].getLatitude(), currentLoc[0].getLongitude());
                            if(distance < 20){
                                runOnUiThread(()->{
                                    //only next marker should be blue
                                    if(pointOfInterest.equals(nextPointOfInterest)){
                                        synchronized (routeHandler.getMarkers()) {
                                            for (Marker marker : routeHandler.getMarkers()) {
                                                if (marker.getPosition().equals(nextPointOfInterest.getLocation())) {
                                                    marker.remove();

                                                }
                                            }

                                            if(!pointOfInterest.isVisited()){
                                                openPOI(pointOfInterest);
                                            }else routeHandler.updateMarker(nextPointOfInterest.getLocation(),nextPointOfInterest.getTitle());

                                            if(pointOfInterestList.indexOf(nextPointOfInterest) + 1 < pointOfInterestList.size())
                                                nextPointOfInterest = pointOfInterestList.get(pointOfInterestList.indexOf(nextPointOfInterest) + 1);

                                            routeHandler.updateMarkerDarkBlue(nextPointOfInterest.getLocation(), nextPointOfInterest.getTitle());


                                        }

                                    }else openPOI(pointOfInterest);
                                });
                            }
                        }
                    }


                    if(routeHandler.getPolylinesMap() != null && !routeHandler.getPolylinesMap().isEmpty()){

                        if(handler.getLocation() != null){
                            runOnUiThread(() -> {
                                for (Polyline line : routeHandler.getPolylinesMap()) {
                                    List<LatLng> prevPoints = line.getPoints();

                                    float distance = distance(prevPoints.get(1).latitude, prevPoints.get(1).longitude, handler.getLocation().getLatitude(), handler.getLocation().getLongitude());
                                    if(distance < 15 ){
                                        line.setColor(getResources().getColor(R.color.colorWalkedRouteAndPinPoint));
                                    }
                                }

                            });
                        }
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }).start();
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
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMyLocationEnabled(true);

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent i = new Intent(getApplicationContext(), DetailPoiActivity.class);
                PointOfInterest poi=null;
                for(PointOfInterest p:route.getAllPointsOfInterest()) {
                    if (p.getLocation().equals(marker.getPosition())) {
                        poi = p;
                    }
                }
                i.putExtra("POI",poi);
                marker.hideInfoWindow();
                startActivity(i);
            }
        });

    }


    public float distance (double lat_a, double lng_a, double lat_b, double lng_b )
    {
        double earthRadius = 3958.75;
        double latDiff = Math.toRadians(lat_b-lat_a);
        double lngDiff = Math.toRadians(lng_b-lng_a);
        double a = Math.sin(latDiff /2) * Math.sin(latDiff /2) +
                Math.cos(Math.toRadians(lat_a)) * Math.cos(Math.toRadians(lat_b)) *
                        Math.sin(lngDiff /2) * Math.sin(lngDiff /2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double distance = earthRadius * c;

        int meterConversion = 1609;

        return new Float(distance * meterConversion).floatValue();
    }
}
