package nl.thijswijnen.geojob.UI;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import nl.thijswijnen.geojob.R;
import nl.thijswijnen.geojob.Util.ZoomOutPageTransformer;

public class DetailPoiActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_poi);

        ViewPager viewPager = findViewById(R.id.activity_detailed_viewpages_images_id);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());

        Intent intent = getIntent();
    }
}
