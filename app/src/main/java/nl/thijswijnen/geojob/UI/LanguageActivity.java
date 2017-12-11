package nl.thijswijnen.geojob.UI;

import android.content.Context;
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
    private static final String PREFS_NAME = "NamePrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        ImageButton dutchButton = (ImageButton) findViewById(R.id.language_dutch_imgButt);
        dutchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLanguage(PREFS_NAME,"nl");
            }
        });

        ImageButton englishButton = (ImageButton) findViewById(R.id.language_english_imgButt);
        englishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLanguage(PREFS_NAME,"en");
            }
        });
    }

    public void setLanguage(String prefName, String lang){
        SharedPreferences preferences = getSharedPreferences(prefName,MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
        editor.putString("language",lang);
        editor.apply();
        setLocale(getApplicationContext(),lang);

        //TODO: check if this is OK
        Intent welcomeIntent = new Intent(this, WelcomeActivity.class);
        startActivity(welcomeIntent);
    }


    public static void setLocale(Context context, String lang){
        WelcomeActivity.myLocale = new Locale(lang);
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = WelcomeActivity.myLocale;
        res.updateConfiguration(conf, dm);
    }
}
