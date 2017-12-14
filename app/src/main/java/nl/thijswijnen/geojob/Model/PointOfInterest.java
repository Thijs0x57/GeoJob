package nl.thijswijnen.geojob.Model;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by thijs_000 on 05-Dec-17.
 */

public abstract class PointOfInterest implements Serializable
{
    private String title;
    private String descriptionNL;
    private String descriptionEN;
    private List<String> allImages;
    private List<String> allVideos;
    private double latitude;
    private double longitude;
    private boolean visited;

    public PointOfInterest(String title, String descriptionNL, String descriptionEN, LatLng location) {
        this.title = title;
        this.descriptionNL = descriptionNL;
        this.descriptionEN = descriptionEN;

        latitude = location.latitude;
        longitude = location.longitude;

        visited = false;
        allImages = new ArrayList<>();
        allVideos = new ArrayList<>();
    }

    public String getDescriptionNL() {
        return descriptionNL;
    }

    public String getDescriptionEN() {
        return descriptionEN;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public List<String> getAllImages()
    {
        return allImages;
    }

    public void setAllImages(List<String> allImages)
    {
        this.allImages = allImages;
    }

    public List<String> getAllVideos()
    {
        return allVideos;
    }

    public void setAllVideos(List<String> allVideos)
    {
        this.allVideos = allVideos;
    }

    public LatLng getLocation() {
        return new LatLng(latitude,longitude);
    }

    public boolean isVisited()
    {
        return visited;
    }

    public void setVisited(boolean visited)
    {
        this.visited = visited;
    }
}
