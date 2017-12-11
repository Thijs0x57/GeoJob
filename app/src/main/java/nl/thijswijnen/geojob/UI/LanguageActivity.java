package nl.thijswijnen.geojob.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import java.util.Locale;
import java.util.Map;

import android.content.res.Resources;

import nl.thijswijnen.geojob.R;

public class LanguageActivity extends AppCompatActivity
{
    Locale myLocale;
    private static final String PREFS_NAME = "NamePrefsFile";
    SharedPreferences.Editor editor;
    Boolean firstTime = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        SharedPreferences preferences = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        Map<String, ?> keyValues = preferences.getAll();
        editor = preferences.edit();

        if(getIntent().getExtras() == null) {
            firstTime = true;
        } else {
            firstTime = false;
        }

        if(!keyValues.isEmpty()&&firstTime){       //checkt of er al een taal aanwezig ig TODO: als je later wilt aanpassen doet hij het ook niet
            String lang = (String)keyValues.get("language");
            setLocale(lang);
        }

        ImageButton dutchButton = (ImageButton) findViewById(R.id.language_dutch_imgButt);
        dutchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLanguage("nl");
            }
        });

        ImageButton englishButton = (ImageButton) findViewById(R.id.language_english_imgButt);
        englishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLanguage("en");
            }
        });
    }

    public void setLanguage(String lang){
        editor.clear();
        editor.apply();
        editor.putString("language",lang);
        editor.apply();
        setLocale(lang);
    }


    public void setLocale(String lang){
        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent welcomeIntent = new Intent(this, WelcomeActivity.class);
        startActivity(welcomeIntent);
    }
}
