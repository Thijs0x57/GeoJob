package nl.thijswijnen.geojob.UI;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import nl.thijswijnen.geojob.Controller.Adapters.PoiListViewAdapter;
import nl.thijswijnen.geojob.Model.BlindWalls;
import nl.thijswijnen.geojob.Model.PointOfInterest;
import nl.thijswijnen.geojob.Model.Route;
import nl.thijswijnen.geojob.R;

public class DetailRouteActivity extends AppCompatActivity {

    private Route route;
    RecyclerView pois;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_route);

        Bundle b = getIntent().getExtras();
        route = (Route) b.getSerializable("route");
        List<PointOfInterest> poisList = null;

        if (route.getClass()==BlindWalls.class){
            poisList = route.getAllPointsOfInterest();
        } else {
            poisList = route.getHKPointsOfInterests();
        }

        TextView title = findViewById(R.id.activity_detail_route_title);
        title.setText(route.getRouteTitle());

        TextView descriptionTitle = findViewById(R.id.PointsDescriptionBoxTitle);
        descriptionTitle.setTextColor(Color.BLACK);

        pois = findViewById(R.id.activity_detail_route_pinpoints_recycleview);
        pois.setLayoutManager(new LinearLayoutManager(this));
        PoiListViewAdapter adapter = new PoiListViewAdapter(poisList);
        pois.setAdapter(adapter);

        TextView description = findViewById(R.id.activity_detail_route_description);
        Locale current = getResources().getConfiguration().locale;
        if (current.equals(new Locale("en"))) {
            description.setText(route.getDescriptionEN());
        } else if (current.equals(new Locale("nl"))) {
            description.setText(route.getDescriptionNL());
        }


//        final int speedScroll = 500;
//        final Handler handler = new Handler();
//        final Runnable runnable = new Runnable() {
//            int count = 0;
//
//            @Override
//            public void run() {
//                if (count < poisList.size()) {
//                    pois.scrollToPosition(++count);
//                    handler.postDelayed(this, speedScroll);
//                }
//
//
//            }
//        };
//
//        handler.postDelayed(runnable, speedScroll);

        Button startRoute = findViewById(R.id.activity_detail_route_start_button);
        startRoute.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), NavigateActivity.class);
            intent.putExtra("route", route);
            startActivity(intent);
            finish();
        });
    }
}
