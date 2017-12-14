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

/**
 * Created by thijs_000 on 05-Dec-17.
 */

public class BlindWalls extends Route implements Serializable
{
    @Override
    public void load(Context context)
    {
        setRouteTitle("Blindwalls");
        List<PointOfInterest> pointOfInterests = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(loadJSONFromAsset(context));
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


    private String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("Blindwalls.json");
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
