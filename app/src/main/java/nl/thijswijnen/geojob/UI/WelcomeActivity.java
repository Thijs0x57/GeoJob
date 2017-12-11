package nl.thijswijnen.geojob.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import nl.thijswijnen.geojob.R;

public class WelcomeActivity extends AppCompatActivity
{

    private Button routeKiezenBtn;
    private Button taalKeuzeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //button navigation
        routeKiezenBtn = findViewById(R.id.welcome_RouteKiezen_btn);
        routeKiezenBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent routeKiezenIntent = new Intent(getApplicationContext(), ChooseRouteActivity.class);
                startActivity(routeKiezenIntent);
            }
        });

        taalKeuzeBtn = findViewById(R.id.welcome_TaalKiezen_btn);
        taalKeuzeBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent taalKeuzeIntent = new Intent(getApplicationContext(), LanguageActivity.class);
                startActivity(taalKeuzeIntent);
            }
        });
    }
}
