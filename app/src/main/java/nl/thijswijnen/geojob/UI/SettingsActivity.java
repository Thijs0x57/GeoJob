package nl.thijswijnen.geojob.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import android.view.View;
import android.widget.Button;

import nl.thijswijnen.geojob.R;

public class SettingsActivity extends AppCompatActivity
{
    private Switch notificationswtch;
    private Button chooselanguagebtn;
    protected static SharedPreferences preferences;
    protected static SharedPreferences.Editor editor;
    private final String SHOW_NOTIFICATIONS = "showNotifications";
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        preferences = getSharedPreferences(WelcomeActivity.PREFS_NAME, MODE_PRIVATE);
        editor = preferences.edit();


        notificationswtch = findViewById(R.id.settings_notifications_swtch);
        notificationswtch.setChecked(preferences.getBoolean(SHOW_NOTIFICATIONS,true));

        chooselanguagebtn = findViewById(R.id.settings_chooselanguage_btn);
        notificationswtch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                toggleNotification(compoundButton.isChecked());
            }
        });
        chooselanguagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), LanguageActivity.class);
                startActivity(intent);
            }
        });
    }

    public void toggleNotification(boolean wantToShow) {


        if (wantToShow) {
            Toast.makeText(getApplicationContext(), "Notification Enabled", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Notification Disabled", Toast.LENGTH_SHORT).show();
        }
        editor.putBoolean(SHOW_NOTIFICATIONS,wantToShow);
        editor.commit();

    }
}
