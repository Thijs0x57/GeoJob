package nl.thijswijnen.geojob.UI;

import android.annotation.SuppressLint;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Guideline;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Locale;

import nl.thijswijnen.geojob.Model.BlindWall;
import nl.thijswijnen.geojob.Model.Monument;
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
        if (b.getSerializable("POI") != null) {
            PointOfInterest poi = (PointOfInterest) b.getSerializable("POI");
            Locale currentLocale = getResources().getConfiguration().locale;

            TextView title = findViewById(R.id.detailPoi_title_txtvw);
            title.setText(poi.getTitle());


            TextView locationTxt = findViewById(R.id.detailedPoi_Location_txtview);
            TextView description = findViewById(R.id.detailPoi_description_txtvw);
            TextView materialTxt = findViewById(R.id.detailedPoi_Material_txtview);
            TextView photographerTxt = findViewById(R.id.detailedPoi_photographer_txtview);
            TextView yearTxt = findViewById(R.id.detailedPoi_year_txtview);

            if (poi.getClass() == BlindWall.class) {
                BlindWall BPoi = (BlindWall) poi;
                locationTxt.setText(BPoi.getLocationS());

                if (currentLocale.equals(new Locale("en"))) {
                    materialTxt.setText(BPoi.getMaterialEn());
                    description.setText(poi.getDescriptionEN());
                } else if (currentLocale.equals(new Locale("nl"))) {
                    materialTxt.setText(BPoi.getMaterialNl());
                    description.setText(poi.getDescriptionNL());
                }

                photographerTxt.setText(BPoi.getPhotographer());
                yearTxt.setText(BPoi.getYear());

            } else if (poi.getClass()==Monument.class) {

                //turns off All BlindWall textviews

                TextView locationFill = findViewById(R.id.detailPoi_locationFill_txtvw);
                TextView materialFill = findViewById(R.id.textdetailPoi_materialFill_txtvwView4);
                TextView photographerFill = findViewById(R.id.detailPoi_photographerFill_txtvw);
                TextView yearFill = findViewById(R.id.detailPoi_yearFill_txtvw);

                locationFill.setText("");
                materialFill.setText("");
                photographerFill.setText("");
                yearFill.setText("");
                locationTxt.setText("");
                materialTxt.setText("");
                photographerTxt.setText("");
                yearTxt.setText("");

                if (currentLocale.equals(new Locale("en"))) {
                    description.setText(poi.getDescriptionEN());
                } else if (currentLocale.equals(new Locale("nl"))) {
                    description.setText(poi.getDescriptionNL());
                }

                Guideline highLvl = findViewById(R.id.guideline6);
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) highLvl.getLayoutParams();
                params.guidePercent = 0.50f; // 45% // range: 0 <-> 1
                highLvl.setLayoutParams(params);


            }
        }




    }
}
