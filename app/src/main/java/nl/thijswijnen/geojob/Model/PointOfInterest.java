package nl.thijswijnen.geojob.Model;

import android.location.Location;

import java.util.List;

/**
 * Created by thijs_000 on 05-Dec-17.
 */

public class PointOfInterest
{
    private String title;
    private String description;
    private List<String> allImages;
    private List<String> allVideos;
    private Location location; //moet volgens klassendiagram een Point zijn!
    private boolean visited;

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
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

    public Location getLocation()
    {
        return location;
    }

    public void setLocation(Location location)
    {
        this.location = location;
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
