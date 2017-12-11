package nl.thijswijnen.geojob.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.Locale;

import nl.thijswijnen.geojob.Controller.Adapters.PoiListViewAdapter;
import nl.thijswijnen.geojob.Model.Route;
import nl.thijswijnen.geojob.R;

public class DetailRouteActivity extends AppCompatActivity
{

    private Route route;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_route);

        Bundle b = getIntent().getExtras();
        Route r = (Route) b.getSerializable("route");

        TextView title = findViewById(R.id.activity_detail_route_title);
        title.setText(r.getRouteTitle());

        RecyclerView pois = findViewById(R.id.activity_detail_route_pinpoints_recycleview);
        pois.setLayoutManager(new LinearLayoutManager(this));
        PoiListViewAdapter adapter = new PoiListViewAdapter(r.getAllPointsOfInterest());
        pois.setAdapter(adapter);

        TextView description = findViewById(R.id.activity_detail_route_description);
        Locale current = getResources().getConfiguration().locale;
        if(current.equals(new Locale("en"))){
            description.setText(r.getDescriptionEN());
        }else if(current.equals(new Locale("nl"))){
            description.setText(r.getDescriptionNL());
        }
    }
}
