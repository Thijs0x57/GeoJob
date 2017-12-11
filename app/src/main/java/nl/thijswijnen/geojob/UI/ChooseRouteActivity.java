package nl.thijswijnen.geojob.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import nl.thijswijnen.geojob.Controller.Adapters.ChooseRouteActivityItemDecorator;
import nl.thijswijnen.geojob.Controller.Adapters.RouteListViewAdapter;
import nl.thijswijnen.geojob.Model.BlindWall;
import nl.thijswijnen.geojob.Model.BlindWalls;
import nl.thijswijnen.geojob.Model.Route;
import nl.thijswijnen.geojob.R;

public class ChooseRouteActivity extends AppCompatActivity
{
    private List<Route> routes;

    private RouteListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_route);
        ImageButton settings = findViewById(R.id.activity_choose_route_settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),SettingsActivity.class);
                startActivity(i);
            }
        });


        routes = new ArrayList<>();

        Route r = new BlindWalls();
        r.load(this);
        routes.add(r);

        //dummy route
        Route route = new Route();
        route.setRouteTitle("test");
        routes.add(route);

        Route p = new Route();
        p.setRouteTitle("test2");
        routes.add(p);

        EditText search = findViewById(R.id.activity_choose_route_search_edittext);
        search.setHint("\uD83D\uDD0D Search");

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        RecyclerView recyclerView = findViewById(R.id.activity_choose_route_recycleview);
        recyclerView.addItemDecoration(new ChooseRouteActivityItemDecorator(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RouteListViewAdapter(routes);
        recyclerView.setAdapter(adapter);

    }
}
