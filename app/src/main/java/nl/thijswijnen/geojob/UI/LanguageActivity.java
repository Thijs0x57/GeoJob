package nl.thijswijnen.geojob.UI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
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
        SharedPreferences preferences = getSharedPreferences(WelcomeActivity.PREFS_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.clear().apply();
        editor.putString("language", lang).apply();
        setLocale(getApplicationContext(),lang);
        finish();
        //TODO: check if this is OK

    }


    public static void setLocale(Context context, String lang){
        WelcomeActivity.myLocale = new Locale(lang);
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = WelcomeActivity.myLocale;
        res.updateConfiguration(conf, dm);

        Intent welcomeIntent = new Intent(context, WelcomeActivity.class);
        welcomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(welcomeIntent);
    }
}
