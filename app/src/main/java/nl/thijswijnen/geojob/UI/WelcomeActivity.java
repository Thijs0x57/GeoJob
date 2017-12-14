package nl.thijswijnen.geojob.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Locale;
import java.util.Map;

import nl.thijswijnen.geojob.R;
import nl.thijswijnen.geojob.Util.Constants;

public class WelcomeActivity extends AppCompatActivity
{

    //shared preferences

    protected static final String PREFS_NAME = "NamePrefsFile";


    static Locale myLocale;

    //permissions



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        Map<String, ?> keyValues = preferences.getAll();

        //preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

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

        if (ContextCompat.checkSelfPermission(this, Constants.PERMISSION_FINELOCATION_STRING)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Constants.PERMISSION_FINELOCATION_STRING}, Constants.PERMISSION_REQUEST_CODE);
        }
        if (ContextCompat.checkSelfPermission(this, Constants.PERMISSION_COARSELOCATION_STRING)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Constants.PERMISSION_COARSELOCATION_STRING}, Constants.PERMISSION_REQUEST_CODE);
        }

        ImageView HulpButton = findViewById(R.id.welcome_imgHulp);
        HulpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent helpActivity = new Intent(getApplicationContext(), HelpActivity.class);
                startActivity(helpActivity);
            }
        });

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
            finish();
        }

        
        //button navigation
        Button routeKiezenBtn = findViewById(R.id.welcome_chooose_route_btn);
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
                finish();
            }
        });
    }
}
