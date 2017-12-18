package nl.thijswijnen.geojob.UI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Locale;

import nl.thijswijnen.geojob.Model.BlindWall;
import nl.thijswijnen.geojob.Model.PointOfInterest;
import nl.thijswijnen.geojob.R;
import nl.thijswijnen.geojob.Util.ZoomOutPageTransformer;

public class DetailPoiActivity extends AppCompatActivity
{

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_poi);

        ViewPager viewPager = findViewById(R.id.activity_detailed_viewpages_images_id);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());

        Bundle b = getIntent().getExtras();
        if (b.getSerializable("POI") != null)
        {
            PointOfInterest poi = (PointOfInterest) b.getSerializable("POI");
            TextView title = findViewById(R.id.detailPoi_title_txtvw);
            title.setText(poi.getTitle());

            TextView info = findViewById(R.id.detailPoi_info_txtvw);
            TextView description = findViewById(R.id.detailPoi_description_txtvw);
            Locale currentLocale = getResources().getConfiguration().locale;
            if (currentLocale.equals(new Locale("en"))){
                description.setText(poi.getDescriptionEN());
            } else if (currentLocale.equals(new Locale("nl"))){
                description.setText(poi.getDescriptionNL());
            }
        } else if (b.getSerializable("wall") != null){
            BlindWall wall = (BlindWall) b.getSerializable("wall");
            TextView title = findViewById(R.id.detailPoi_title_txtvw);
            title.setText(wall.getTitle());

            TextView info = findViewById(R.id.detailPoi_info_txtvw);
            TextView description = findViewById(R.id.detailPoi_description_txtvw);
            Locale currentLocale = getResources().getConfiguration().locale;
            if (currentLocale.equals(new Locale("en"))){
                info.setText(getString(R.string.Common_artist) + wall.getArtist()+ "\n" +
                        getString(R.string.Common_photographer) + wall.getPhotographer() + "\n" +
                getString(R.string.Common_material) + wall.getMaterialEn());
                description.setText(wall.getDescriptionEN());
            } else if (currentLocale.equals(new Locale("nl"))){
                info.setText(getString(R.string.Common_artist) + wall.getArtist()+ "\n" +
                        getString(R.string.Common_photographer) + wall.getPhotographer() + "\n" +
                        getString(R.string.Common_material) + wall.getDescriptionNL());
                description.setText(wall.getDescriptionNL());
            }
        }




    }
}
