package nl.thijswijnen.geojob.Model;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Parcel;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import nl.thijswijnen.geojob.R;
import nl.thijswijnen.geojob.UI.NavigateActivity;
import nl.thijswijnen.geojob.Util.Constants;

/**
 * Created by thijs_000 on 17-Dec-17.
 */

public class RouteHandler
{
    private String TAG = "RouteHandler";
    private Context context;

    private List<List<LatLng>> lines;
    private double distance = 0;

    private double northLat;
    private double northLng;
    private double southLat;
    private double southLng;

    public static RequestQueue mapQueue;


    public RouteHandler(Context context, LatLng origin, List<PointOfInterest> points)
    {
        this.context = context;
        List<LatLng> poisLatLng = null;
        mapQueue = Volley.newRequestQueue(context);
        for (PointOfInterest p : points)
        {
            if (p.getLatLng() != null)
            {
                poisLatLng.add(p.getLatLng());
            }
        }
        String url = getUrl(origin, poisLatLng);
        lines = new ArrayList<>();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                addMarker(origin, context.getString(R.string.Common_origin));

                JSONArray jRoutes = response.getJSONArray("routes");
                JSONArray jLegs = jRoutes.getJSONObject(0).getJSONArray("legs");
                JSONArray jSteps = jLegs.getJSONObject(0).getJSONArray("steps");

                JSONObject northEastObject = jRoutes.getJSONObject(0).getJSONObject("bounds").getJSONObject("northeast");
                JSONObject southWestObject = jRoutes.getJSONObject(0).getJSONObject("bounds").getJSONObject("southwest");

                northLat = northEastObject.getDouble("lat");
                northLng = northEastObject.getDouble("lng");
                southLat = southWestObject.getDouble("lat");
                southLng = southWestObject.getDouble("lng");

                NavigateActivity.mMap.animateCamera(getCameraUpdate());

                distance = Math.round(jLegs.getJSONObject(0).getJSONObject("distance").getInt("value") / 1000);

                for (int i = 0; i < jRoutes.length(); i++) {
                    for (int j = 0; j < jLegs.length(); j++) {
                        for (int k = 0; k < jSteps.length(); k++) {
                            JSONObject object = jSteps.getJSONObject(k);
                            String polyline = object.getJSONObject("polyline").getString("points");
                            List<LatLng> list = decodePoly(polyline);
                            lines.add(list);
                        }
                    }
                }

                NavigateActivity.mMap.addPolyline(getPolylineOptions());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, onError);

        mapQueue.add(jsonObjectRequest);

    }


    //normal route https://maps.googleapis.com/maps/api/directions/json?origin=Disneyland&destination=Universal+Studios+Hollywood4&key=AIzaSyDlPMbvEikR40aphGhAQHBirTTPonIR5Ic
    //route with waypoints: https://maps.googleapis.com/maps/api/directions/json?origin=Boston,MA&destination=Concord,MA&waypoints=Charlestown,MA|Lexington,MA&key=YOUR_API_KEY
    //route with waypoints: https://maps.googleapis.com/maps/api/directions/json?origin=Boston,MA&destination=Concord,MA&waypoints=Charlestown,MA|via:Lexington,MA&key=YOUR_API_KEY
    //route with waypoints: https://maps.googleapis.com/maps/api/directions/json?origin=Boston,MA&destination=Concord,MA&waypoints=42.4614275,-71.0552091,MA|Lexington,MA&key=AIzaSyDlPMbvEikR40aphGhAQHBirTTPonIR5Ic

    public String getUrl(LatLng origin, List<LatLng> points)
    {
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude + ",MA";

        LatLng destinationLatLng = new LatLng(points.get(points.size()).latitude, points.get(points.size()).longitude);
        String str_dest = "MA&destination=" + destinationLatLng.latitude + "," + destinationLatLng.longitude;

        String trafficMode = "mode=walking";

        String str_waypoints = "";

        String parameters = str_origin + "," + str_dest + "," + str_waypoints + "&" + trafficMode;

        String output = "json";

        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + Constants.API_KEY;

        return url;
    }

    private void addMarker(LatLng origin, String title)
    {
        NavigateActivity.mMap.addMarker(new MarkerOptions().position(origin).title(title));
    }


    public CameraUpdate getCameraUpdate(){
        LatLng northEast = new LatLng(northLat, northLng);
        LatLng southWest = new LatLng(southLat, southLng);
        LatLngBounds bounds = new LatLngBounds(southWest, northEast);
        return CameraUpdateFactory.newLatLngBounds(bounds, 80);
    }

    public PolylineOptions getPolylineOptions(){
        PolylineOptions polylineOptions = new PolylineOptions().width(3).color(Color.RED);
        for (List<LatLng> leg : lines) {
            polylineOptions.addAll(leg);
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
}
