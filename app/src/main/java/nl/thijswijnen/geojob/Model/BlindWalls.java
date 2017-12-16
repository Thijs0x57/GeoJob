package nl.thijswijnen.geojob.Model;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import nl.thijswijnen.geojob.R;

/**
 * Created by thijs_000 on 05-Dec-17.
 */

public class BlindWalls extends Route implements Serializable
{

    @Override
    public void load(Context context)
    {
        setRouteTitle("Blindwalls");
        setDescriptionEN("The blindwalls route is a route ");
        setDescriptionNL("The blindwalls route is een route");
        List<PointOfInterest> pointOfInterests = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(loadJSONFromAsset(context,"Blindwalls.json"));
            for (int i = 0; i < array.length(); i++) {
                JSONObject wall = array.getJSONObject(i);

                String title = wall.getJSONObject("title").getString("en");

                double latitude = wall.getDouble("latitude");
                double longitude = wall.getDouble("longitude");
                LatLng location = new LatLng(latitude,longitude);

                JSONObject description = wall.getJSONObject("description");
                String descriptionEN = description.getString("en");
                String descriptionNL = description.getString("nl");

                String artist = wall.getString("author");
                String photographer = wall.getString("photographer");

                JSONObject material = wall.getJSONObject("material");
                String materialEN = material.getString("en");
                String materialNL = material.getString("nl");

                BlindWall blindWall = new BlindWall(title,descriptionNL,descriptionEN,location,artist,photographer,materialEN,materialNL);

                JSONArray images = wall.getJSONArray("images");
                for (int j = 0; j < images.length(); j++) {
                    JSONObject image = images.getJSONObject(j);
                    String url = image.getString("url");
                    blindWall.getAllImages().add(url);
                }

                blindWall.getAllVideos().add(wall.getString("videoUrl"));
                pointOfInterests.add(blindWall);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        setAllPointOfInterests(pointOfInterests);


    }

    public static List<Route> getBlindWallsRoutes(Context context){
        BlindWalls b = new BlindWalls();
        b.load(context);

        List<Route> blindwalls = new ArrayList<>();

        try {
            JSONArray routes = new JSONArray(loadJSONFromAsset(context,"BlindwallRoutes.json"));
            for (int i = 0; i < routes.length(); i++) {
                JSONObject route = routes.getJSONObject(i);
                BlindWalls bw = new BlindWalls();
                bw.setRouteTitle("Blindwall route " + route.getInt("id"));
                String type = "";
                if (route.getString("type").equals("walk"))
                {
                    type = context.getString(R.string.BlindWalls_walkType);
                }else if (route.getString("type").equals("bike")){
                    type = context.getString(R.string.BlindWalls_bikeType);
                }
                bw.setDescriptionEN("This route is " + route.getString("distance") + " long. When you " + type + " this route its " +
                route.getString("time") + " long.");
                bw.setDescriptionNL("Deze route is " + route.getString("distance") + " lang. Als je " + type + " is deze route " +
                        route.getString("time") + " lang.");

                JSONArray points = route.getJSONArray("points");
                for (int j = 0; j < points.length(); j++) {
                    JSONObject point = points.getJSONObject(j);

                    //checks for each point in the route the mural id and if thats is equal to another id it wil add it to this route.
                    int id = point.getInt("muralId");
                    for (int i1 = 0; i1 < b.getAllPointsOfInterest().size(); i1++) {
                        if(i1 == id){
                            List<PointOfInterest> bwpois = bw.getAllPointsOfInterest();
                            bwpois.add(b.getAllPointsOfInterest().get(i1));
                            bw.setAllPointOfInterests(bwpois);
                        }
                    }
                }
                blindwalls.add(bw);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return blindwalls;
    }



    private static String loadJSONFromAsset(Context context,String jsonName) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(jsonName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
