package nl.thijswijnen.geojob.Util;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by thijs_000 on 14-Dec-17.
 */

public class CoordinateConverter
{
    public static String latitude;
    public static String longitude;

    public static LatLng degreeToDecimal(String latitude, String longitude)
    {
        //latitude String to latitude decimal
        String latFirstSplit [] = latitude.split("°");
        String latSecondSplit []  = latFirstSplit[1].split(".");
        String latCleanSecondSplit = latSecondSplit[1].replace("’", "");

        int latDegree = Integer.parseInt(latFirstSplit[0]);
        int latDecimal = Integer.parseInt(latSecondSplit[0]);
        int latMinute = Integer.parseInt(latCleanSecondSplit);

        double latitudeDouble = latDegree + ((latMinute/16.73913)/60);

        String lngFirstSplit [] = longitude.split("°");
        String lngSecondSplit []  = lngFirstSplit[1].split(".");
        String lngCleanSecondSplit = lngSecondSplit[1].replace("’", "");

        int lngDegree = Integer.parseInt(lngFirstSplit[0]);
        int lngDecimal = Integer.parseInt(lngSecondSplit[0]);
        int lngMinute = Integer.parseInt(lngCleanSecondSplit);

        double longitudeDouble = lngDegree + ((lngMinute/16.73913)/60);

        return new LatLng(latitudeDouble, longitudeDouble);
    }
}
