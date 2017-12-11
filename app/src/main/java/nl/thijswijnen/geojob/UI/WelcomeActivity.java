package nl.thijswijnen.geojob.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import java.util.Locale;
import java.util.Map;

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
    static Locale myLocale;
    private static final String PREFS_NAME = "NamePrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(intent);

        SharedPreferences preferences = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        Map<String, ?> keyValues = preferences.getAll();

        if(!keyValues.isEmpty()){
            String lang = (String)keyValues.get("language");
            LanguageActivity.setLocale(getApplicationContext(),"en");
        } else{
            Intent taalKeuzeIntent = new Intent(getApplicationContext(), LanguageActivity.class);
            startActivity(taalKeuzeIntent);
        }

        //button navigation
//        routeKiezenBtn = findViewById(R.id.welcome_RouteKiezen_btn);
//        routeKiezenBtn.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                Intent routeKiezenIntent = new Intent(getApplicationContext(), ChooseRouteActivity.class);
//                startActivity(routeKiezenIntent);
//            }
//        });
//
//        taalKeuzeBtn = findViewById(R.id.welcome_TaalKiezen_btn);
//        taalKeuzeBtn.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                Intent taalKeuzeIntent = new Intent(getApplicationContext(), LanguageActivity.class);
//                startActivity(taalKeuzeIntent);
//            }
//        });
    }
}
