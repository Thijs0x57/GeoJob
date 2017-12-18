package nl.thijswijnen.geojob.Model;

import android.content.Context;

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
import nl.thijswijnen.geojob.Util.CoordinateConverter;

/**
 * Created by thijs_000 on 05-Dec-17.
 */

public class HistorischeKilometer extends Route implements Serializable
{


    public void load(Context context)
    {
        setRouteTitle(context.getString(R.string.historischeKilometer));
        setDescriptionEN("The Historical Kilometer is a route that goes through the historical city of Breda ");
        setDescriptionNL("De Historische Kilometer is een route die door de historische binnenstad van Breda loopt");
        List<PointOfInterest> pointOfInterests = new ArrayList<>();
        List<PointOfInterest> HKpointOfInterests = new ArrayList<>();
        try{
            JSONArray array = new JSONArray(loadJSONFromAsset(context));
            for (int i = 0; i < array.length(); i++){
                JSONObject monument = array.getJSONObject(i);

                String latitude = monument.getString("latitude");
                String longitude = monument.getString("longitude");
                LatLng location = CoordinateConverter.degreeToDecimal(latitude, longitude);

                String title = monument.getString("VVV");

                JSONObject description = monument.getJSONObject("Verhaal");
                String descriptionNL = description.getString("NL");
                String descriptionEN = description.getString("ENG");

                //images
                ArrayList<String> listDataImages = new ArrayList<String>();
                JSONArray images = monument.getJSONArray("Image");
                for (int j = 0; j < images.length(); j++){
                    String image = images.getString(j);
                    listDataImages.add(image);
                }
                List<String> imagesList = listDataImages;

                //videos
                ArrayList<String> listDataVideos = new ArrayList<String>();
                JSONArray videos = monument.getJSONArray("Video");
                for (int v = 0; v < videos.length(); v++){
                    String video = videos.getString(v);
                    listDataVideos.add(video);
                }
                List<String> videosList = listDataVideos;

                PointOfInterest poi = new Monument(title, descriptionNL, descriptionEN, location);
                poi.setAllImages(imagesList);
                poi.setAllVideos(videosList);

                if(!poi.getTitle().equals("")){
                    HKpointOfInterests.add(poi);
                    System.out.println(poi.getTitle());
                }
                pointOfInterests.add(poi);
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
        if(!HKpointOfInterests.isEmpty()){
            setHKPointsOfInterests(HKpointOfInterests);
        }
        setAllPointOfInterests(pointOfInterests);
    }

    private String loadJSONFromAsset(Context context)
    {
        String json = null;
        try{
            InputStream is = context.getAssets().open("HistorischeKilometer.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex){
            ex.printStackTrace();
            return null;
        }
        return json;

    }
}

/**
 * [
        {
            "1": "2",
            "latitude": "51°35.5967’",
            "longitude": "4°46.7633’",
            "VVV": "Liefdeszuster",
            "Beginpunt": "",
            "Verhaal":
            {
                "Default": "Symbolisch beeld voor het religieus verleden van Breda. De Liefdezuster geeft de dienstverlening weer, zoals de Gasthuiszusters die eeuwenlang in de praktijk brachten.",
                "NL": "Symbolisch beeld voor het religieus verleden van Breda. De Liefdezuster geeft de dienstverlening weer, zoals de Gasthuiszusters die eeuwenlang in de praktijk brachten.",
                "ENG": "Symbolic image for the religious past of Breda. De Liefdezuster shows the service, as the Gasthuiszusters put into practice for centuries"
            },
            "Image":
            [
                {
                    "0": "liefdeszuster.jpg"
                }
            ],
            "Audio":
            [
                {
                    "0": "liefdeszustergeluid.mp3"
                }
            ],
            "Video":
            [
                {
                    "0": "liefdeszusterfilm.mp4"
                }
            ]
        }
    ]
 */
