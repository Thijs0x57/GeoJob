package nl.thijswijnen.geojob.UI;

import android.content.Intent;
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
    Switch notificationswtch;
    Button chooselanguagebtn;

    private Button taalKeuzeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        notificationswtch = findViewById(R.id.settings_notifications_swtch);
        chooselanguagebtn = findViewById(R.id.settings_chooselanguage_btn);
        notificationswtch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.d("test" , "" + "test" );
                toggleNotification(compoundButton.isChecked());
            }
        });
        chooselanguagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("test" , "" + "button clicked" );
                Intent intent = new Intent(getApplicationContext(), LanguageActivity.class);
                startActivity(intent);
            }
        });
    }

    public void toggleNotification(boolean showNotifcation) {

        Log.d("test" , "" + showNotifcation );
        if (showNotifcation) {
            Toast.makeText(getApplicationContext(), "Notification Enabled", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Notification Disabled", Toast.LENGTH_SHORT).show();
        }
       // getPrefernceHelperInstace().setBoolean(getBaseContext(), ENABLE_NOTIFICATION, showNotifcation);
    }
}
