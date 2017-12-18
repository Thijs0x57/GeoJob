package nl.thijswijnen.geojob.Model;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

import nl.thijswijnen.geojob.R;

import static com.android.volley.VolleyLog.TAG;

public class GeoFencingService extends IntentService {


    public GeoFencingService() {
        super("Geofencing");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d("Geofencing","geofencing has been called ");
        GeofencingEvent event = GeofencingEvent.fromIntent(intent);

        if (event.hasError()) {
            Log.e("Geofencing", event.getErrorCode() + "");
            return;
        }

        // Get the transition type.
        int geofenceTransition = event.getGeofenceTransition();

        // Test that the reported transition was of interest.
        if (geofenceTransition == (Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)) {

            Intent i = new Intent("eojob.geofencing");
            i.putExtra("latitude",event.getTriggeringLocation().getLatitude());
            i.putExtra("longitude",event.getTriggeringLocation().getLongitude());
            sendBroadcast(i);
        }
    }
}
