package nl.thijswijnen.geojob.Model;

import android.content.Context;

import java.io.Serializable;
import java.util.List;

/**
 * Created by thijs_000 on 05-Dec-17.
 */

public class Route implements Serializable
{
    private String routeTitle;
    private List<PointOfInterest> allPointOfInterests;

    public List<PointOfInterest> getAllPointsOfInterest()
    {
        return allPointOfInterests;
    }

    public void load(Context context)
    {
        //fill method
    }

    public void setAllPointOfInterests(List<PointOfInterest> allPointOfInterests)
    {
        this.allPointOfInterests = allPointOfInterests;
    }

    public void setRouteTitle(String routeTitle)
    {
        this.routeTitle = routeTitle;
    }

    public String getRouteTitle()
    {
        return routeTitle;
    }
}
