package nl.thijswijnen.geojob.Model;

/**
 * Created by thijs_000 on 05-Dec-17.
 */

public class BlindWall extends PointOfInterest
{
    private String artist;
    private String photographer;
    private String material;

    public String getArtist()
    {
        return artist;
    }

    public void setArtist(String artist)
    {
        this.artist = artist;
    }

    public String getPhotographer()
    {
        return photographer;
    }

    public void setPhotographer(String photographer)
    {
        this.photographer = photographer;
    }

    public String getMaterial()
    {
        return material;
    }

    public void setMaterial(String material)
    {
        this.material = material;
    }
}
