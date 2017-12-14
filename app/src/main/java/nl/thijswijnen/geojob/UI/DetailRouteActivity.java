package nl.thijswijnen.geojob.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import nl.thijswijnen.geojob.R;

public class DetailRouteActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_route);

        Button startRouteButton = findViewById(R.id.detailRoute_StartRoute_btn);
        startRouteButton.setOnClickListener(view ->
        {
            startActivity(new Intent(getApplicationContext(), NavigateActivity.class));
        });
    }
}
