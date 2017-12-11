package nl.thijswijnen.geojob.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
    protected static final String PREFS_NAME = "NamePrefsFile";
    protected static SharedPreferences preferences;
    protected static SharedPreferences.Editor editor;
    private final String FIRST_STARTUP = "FIRST_STARTUP";

    public boolean firstStartup;

    static Locale myLocale;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        Map<String, ?> keyValues = preferences.getAll();

        //preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        editor = preferences.edit();

        //check if we need to display languageActivity
        /*
        if (!preferences.getBoolean(FIRST_STARTUP,false))
        {
            startActivity(new Intent(this, LanguageActivity.class));
            editor.putBoolean(FIRST_STARTUP, true);
            editor.apply();
        }
        */
        //LanguageActivity.setLocale(this,preferences.getString("language", null));


        if(!preferences.getAll().isEmpty())
        {
            if(WelcomeActivity.myLocale == null){
                String lang = preferences.getString("language", null);
                LanguageActivity.setLocale(getApplicationContext(),lang);
                finish();
            }
        } else
        {
            Intent taalKeuzeIntent = new Intent(getApplicationContext(), LanguageActivity.class);
            startActivity(taalKeuzeIntent);
        }

        
        //button navigation
        Button routeKiezenBtn = findViewById(R.id.welcome_RouteKiezen_btn);
        routeKiezenBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent routeKiezenIntent = new Intent(getApplicationContext(), ChooseRouteActivity.class);
                startActivity(routeKiezenIntent);
            }
        });

        Button taalKeuzeBtn = findViewById(R.id.welcome_TaalKiezen_btn);
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
