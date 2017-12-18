package nl.thijswijnen.geojob.Model;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import nl.thijswijnen.geojob.R;
import nl.thijswijnen.geojob.Util.Constants;

/**
 * Created by thijs_000 on 17-Dec-17.
 */

public class RouteHandler
{
    private String TAG = "RouteHandler";
    private Activity context;

    private List<List<LatLng>> lines;
    List<LatLng> poisLatLng = new LinkedList<LatLng>();
    private double distance = 0;

    private double northLat;
    private double northLng;
    private double southLat;
    private double southLng;

    private GoogleMap mMap;

    public static RequestQueue mapQueue;

    private Map<Integer,List<Polyline>> polylinesMap;


    public  RouteHandler(Activity context, LatLng origin, List<PointOfInterest> points, GoogleMap mMap, Route route)
    {
        this.context = context;
        this.mMap = mMap;

        northLat = Integer.MIN_VALUE;
        southLat = Integer.MAX_VALUE;
        northLng = Integer.MIN_VALUE;
        southLng = Integer.MAX_VALUE;

        mapQueue = Volley.newRequestQueue(context);
        for (PointOfInterest p : points)
        {
            if (p.getLocation() != null)
            {
                poisLatLng.add(p.getLocation());
            }
        }
        List<String> urls = getUrls(origin);
        lines = new ArrayList<>();
        polylinesMap = new HashMap<>();

        for (int i = 0; i < urls.size(); i ++) {
            String url = urls.get(i);
            int finalI = i;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
                synchronized (lines){
                    try {
                        addMarker(origin, context.getString(R.string.Common_origin));
                        for (int j = 0; j < poisLatLng.size(); j++)
                        {
                            if(route instanceof HistorischeKilometer){
                                for (PointOfInterest p : route.getHKPointsOfInterests()) {
                                    if(p.getLocation().equals(poisLatLng.get(j))){
                                        addMarker(new LatLng(poisLatLng.get(j).latitude, poisLatLng.get(j).longitude), points.get(j).getTitle());
                                    }
                                }
                            }else addMarker(new LatLng(poisLatLng.get(j).latitude, poisLatLng.get(j).longitude), points.get(j).getTitle());
                        }

                        JSONArray jRoutes = response.getJSONArray("routes");
                        JSONArray jLegs = jRoutes.getJSONObject(0).getJSONArray("legs");
                        JSONArray jSteps = jLegs.getJSONObject(0).getJSONArray("steps");

                        JSONObject northEastObject = jRoutes.getJSONObject(0).getJSONObject("bounds").getJSONObject("northeast");
                        JSONObject southWestObject = jRoutes.getJSONObject(0).getJSONObject("bounds").getJSONObject("southwest");

                        double norhteastOblat = northEastObject.getDouble("lat");
                        if(norhteastOblat > northLat){
                            northLat = norhteastOblat;
                        }

                        double northeastOblng = northEastObject.getDouble("lng");
                        if(northeastOblng > northLng){
                            northLng = northeastOblng;
                        }

                        double southwestOblat = southWestObject.getDouble("lat");
                        if(southwestOblat < southLat){
                            southLat = southwestOblat;
                        }

                        double southwestOblng = southWestObject.getDouble("lng");
                        if(southwestOblng < southLng){
                            southLng = southwestOblng;
                        }

                        mMap.animateCamera(getCameraUpdate());

                        distance = Math.round(jLegs.getJSONObject(0).getJSONObject("distance").getInt("value") / 1000);


                        for (int j = 0; j < jLegs.length(); j++) {
                            JSONArray step = jLegs.getJSONObject(j).getJSONArray("steps");
                            for (int k = 0; k < step.length(); k++) {
                                JSONObject object = step.getJSONObject(k);
                                String polyline = object.getJSONObject("polyline").getString("points");
                                List<LatLng> list = decodePoly(polyline);
                                lines.add(list);
                            }
                        }
                        List<Polyline> polylines = new ArrayList<>();
                        for (PolylineOptions polylineOptions : getPolylineOptions()) {
                            Polyline p = mMap.addPolyline(polylineOptions);
                            polylines.add(p);
                        }
                        synchronized (polylines){
                            polylinesMap.put(finalI,polylines);
                        }
                        lines.clear();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, onError);

            mapQueue.add(jsonObjectRequest);
        }
    }

    

    //normal route https://maps.googleapis.com/maps/api/directions/json?origin=Disneyland&destination=Universal+Studios+Hollywood4&key=AIzaSyDlPMbvEikR40aphGhAQHBirTTPonIR5Ic
    //route with waypoints: https://maps.googleapis.com/maps/api/directions/json?origin=Boston,MA&destination=Concord,MA&waypoints=Charlestown,MA|Lexington,MA&key=YOUR_API_KEY
    //route with waypoints: https://maps.googleapis.com/maps/api/directions/json?origin=Boston,MA&destination=Concord,MA&waypoints=Charlestown,MA|via:Lexington,MA&key=YOUR_API_KEY
    //route with waypoints: https://maps.googleapis.com/maps/api/directions/json? origin=Boston,MA &destination=Concord,MA &waypoints=42.4614275,-71.0552091,MA |Lexington,MA&key=AIzaSyDlPMbvEikR40aphGhAQHBirTTPonIR5Ic

    public List<String> getUrls(LatLng origin)
    {
        List<String> urls = new ArrayList<>();

        String waipointsString = "waypoints=";

        String trafficMode = "mode=walking";
        String output = "json";

        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        for (int i = 0; i < poisLatLng.size(); i++) {
            if(i % 7 == 0 && i != 0|| i == poisLatLng.size() -1){
                LatLng destinationLatLng;
                if(i == poisLatLng.size()-1){
                    waipointsString += "|" + poisLatLng.get(i).latitude + "," + poisLatLng.get(i).longitude;
                    destinationLatLng = origin;
                }else  destinationLatLng = new LatLng(poisLatLng.get(i).latitude, poisLatLng.get(i).longitude);
                String str_dest = "destination=" + destinationLatLng.latitude + "," + destinationLatLng.longitude;
                String parameters = str_origin + "&" + str_dest + "&" + waipointsString + "&" + trafficMode;
                String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&" + Constants.API_KEY;
                urls.add(url);
                waipointsString = "waypoints=";
                str_origin = "origin=" + poisLatLng.get(i).latitude + "," + poisLatLng.get(i).longitude;
            }else {
                if(!waipointsString.equals("waypoints=")){
                    waipointsString += "|";
                }
                waipointsString += poisLatLng.get(i).latitude + "," + poisLatLng.get(i).longitude;
            }
        }

        return urls;
    }

    private void addMarker(LatLng origin, String title)
    {
        mMap.addMarker(new MarkerOptions().position(origin).title(title));
    }


    public CameraUpdate getCameraUpdate(){
        LatLng northEast = new LatLng(northLat, northLng);
        LatLng southWest = new LatLng(southLat, southLng);
        LatLngBounds bounds = new LatLngBounds(southWest, northEast);
        return CameraUpdateFactory.newLatLngBounds(bounds, 80);
    }

    public List<PolylineOptions> getPolylineOptions(){
        List<PolylineOptions> polylineOptions = new ArrayList<>();
        LatLng prevPosition = null;

        for (List<LatLng> line : lines) {
            for (LatLng latLng : line) {
                if(prevPosition == null){
                    prevPosition = latLng;
                }else {
                    polylineOptions.add(new PolylineOptions().width(10).color(Color.GREEN).add(prevPosition).add(latLng));
                    prevPosition = latLng;
                }
            }
        }

        return polylineOptions;
    }

    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }

    public Response.ErrorListener onError = (VolleyError error) -> {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(context.getString(R.string.Common_noConnection))
                .setTitle(context.getString(R.string.Error_title))
                .setNeutralButton(context.getString(R.string.Common_ok), (dialogInterface, i) ->
                {
                    //do something
                });
        AlertDialog dialog = builder.create();
        dialog.show();
        Log.d("Connection failed: ", error.toString());
    };

    public List<Polyline> getPolylinesMap() {
        List<Polyline> lines = new ArrayList<>();
        List<Integer> keys = new ArrayList<>(polylinesMap.keySet());
        Collections.sort(keys);
        for (Integer integer : keys) {
            lines.addAll(polylinesMap.get(integer));
        }
        return lines;
    }
}
