package nl.thijswijnen.geojob.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import nl.thijswijnen.geojob.R;

public class WelcomeActivity extends AppCompatActivity
{

    //shared preferences
    private final String PREFERENCES_NAME = "SharedPreferences";
    private SharedPreferences sharedPreferences;
    private final String FIRST_STARTUP = "FIRST_STARTUP";

    //buttons
    private Button routeKiezenBtn;
    private Button taalKeuzeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //shared preferences
        sharedPreferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        boolean firstStartUp = sharedPreferences.getBoolean(FIRST_STARTUP, true);

        if (firstStartUp)
        {
            Intent firstStartupIntent = new Intent(getApplicationContext(), LanguageActivity.class);
            startActivity(firstStartupIntent);
        }

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
