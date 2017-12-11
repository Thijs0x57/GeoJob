package nl.thijswijnen.geojob.UI;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import java.util.Locale;
import android.content.res.Resources;

import nl.thijswijnen.geojob.R;

public class LanguageActivity extends AppCompatActivity
{
    Locale myLocale;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        ImageButton dutchButton = (ImageButton) findViewById(R.id.language_dutch_imgButt);
        dutchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLocale("nl");
            }
        });

        ImageButton englishButton = (ImageButton) findViewById(R.id.language_english_imgButt);
        englishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLocale("en");
            }
        });
    }

    public void setLanguage(String lang){
        
    }


    public void setLocale(String lang){
        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, LanguageActivity.class);
        startActivity(refresh);
    }
}
