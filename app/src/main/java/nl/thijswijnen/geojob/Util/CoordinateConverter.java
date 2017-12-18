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
        String latSecondSplit []  = latFirstSplit[1].split("\\.");
        System.out.println(latSecondSplit);
        String latCleanSecondSplit = latSecondSplit[1].replace("’", "");

        double latDegree = Integer.parseInt(latFirstSplit[0]);
        double latDecimal = Integer.parseInt(latSecondSplit[0]);
        double latMinute = Integer.parseInt(latCleanSecondSplit);

        double latitudeDouble = latDegree + ((latDecimal+(latMinute/10000))/60);

        String lngFirstSplit [] = longitude.split("°");
        String lngSecondSplit []  = lngFirstSplit[1].split("\\.");
        String lngCleanSecondSplit = lngSecondSplit[1].replace("’", "");

        double lngDegree = Integer.parseInt(lngFirstSplit[0]);
        double lngDecimal = Integer.parseInt(lngSecondSplit[0]);
        double lngMinute = Integer.parseInt(lngCleanSecondSplit);

        double longitudeDouble = lngDegree  + ((lngDecimal+(lngMinute/10000))/60);

        return new LatLng(latitudeDouble, longitudeDouble);
    }
}


//16.673913043478260869565217391304)