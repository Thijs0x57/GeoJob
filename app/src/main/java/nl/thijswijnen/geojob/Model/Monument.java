package nl.thijswijnen.geojob.Model;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by thijs_000 on 05-Dec-17.
 */

public class Monument extends PointOfInterest implements Serializable
{
    public Monument(String title, String descriptionNL, String descriptionEN, LatLng location) {
        super(title, descriptionNL, descriptionEN, location);
    }
}
