package nl.thijswijnen.geojob.Model;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by thijs_000 on 05-Dec-17.
 */

public class BlindWall extends PointOfInterest
{
    private String artist;
    private String photographer;

    private String materialEn;
    private String materialNl;

    public BlindWall(String title, String descriptionNL, String descriptionEN, LatLng location, String artist, String photographer, String materialEn, String materialNl) {
        super(title, descriptionNL, descriptionEN, location);
        this.artist = artist;
        this.photographer = photographer;
        this.materialEn = materialEn;
        this.materialNl = materialNl;
    }


    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getPhotographer() {
        return photographer;
    }

    public void setPhotographer(String photographer) {
        this.photographer = photographer;
    }

    public String getMaterialEn() {
        return materialEn;
    }

    public void setMaterialEn(String materialEn) {
        this.materialEn = materialEn;
    }

    public String getMaterialNl() {
        return materialNl;
    }

    public void setMaterialNl(String materialNl) {
        this.materialNl = materialNl;
    }
}
